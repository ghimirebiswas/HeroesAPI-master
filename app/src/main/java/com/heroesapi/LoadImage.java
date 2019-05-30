package com.heroesapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import url.Url;

public class LoadImage extends AppCompatActivity {
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        imgPhoto = findViewById(R.id.impPhoto);
        loadFromUrl();
    }

    private void StrictMode(){
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    private void loadFromUrl(){
        StrictMode();


        try {
            String imgUrl = "http://bigulmedia.com/wp-content/uploads/2016/03/Dayahang-Rai-Movie-Wife-and-Salary.jpg";
            URL url = new URL(imgUrl);
            imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            Toast.makeText(this,"error ",Toast.LENGTH_LONG).show();
        }

    }


}
