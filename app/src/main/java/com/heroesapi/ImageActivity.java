package com.heroesapi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import heroesapi.HeroesAPI;
import model.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

public class ImageActivity extends AppCompatActivity {
    private EditText etName, etDesc;
    private Button btnSave;
    private ImageView imgPhoto;
    String imagepath,imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        btnSave = findViewById(R.id.btnSave);
        imgPhoto = findViewById(R.id.imgPhoto);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });
    }

    private void browseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(ImageActivity.this,"please select an image",Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        imagepath = getRealPathFromUri(uri);
        previewImage(imagepath);
    }

    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void previewImage(String imgaepath){
        File imgFile = new File(imagepath);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgPhoto.setImageBitmap(myBitmap);
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    private void SaveImageOnly(){
        File file = new File(imagepath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);
        Call<ImageResponse> responseBodyCall = heroesAPI.uploadImage(body);

        StrictMode();

        try{
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();

        }catch (IOException e){
            Toast.makeText(ImageActivity.this,"error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void save(){
        SaveImageOnly();
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("desc",desc);
        map.put("image",imageName);

        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(map);

        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ImageActivity.this,"code " +response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(ImageActivity.this,"Added successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ImageActivity.this,"error " +t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        
    }
}
