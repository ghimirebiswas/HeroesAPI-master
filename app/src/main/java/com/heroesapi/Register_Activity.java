package com.heroesapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import heroesapi.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class Register_Activity extends AppCompatActivity {
    private EditText etName,etDesc;
    private Button btnSave,btnFieldMap,btnField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        etName = findViewById(R.id.etname);
        etDesc = findViewById(R.id.etDesc);

        btnSave = findViewById(R.id.btnSave);
        btnField = findViewById(R.id.btnField);
        btnFieldMap = findViewById(R.id.btnFieldMap);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        btnField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveField();
            }
        });

        btnFieldMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveFieldMap();
            }
        });
    }

    private void Save()
    {
        String name= etName.getText().toString();
        String desc = etDesc.getText().toString();

        Heroes heroes = new Heroes(name,desc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(heroes);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful())
                {
                   Toast.makeText(Register_Activity.this,"Code" +response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this,"Successfully Saved",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this,"Error" +t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    //Using @Field
    private void SaveField()
    {
        String name= etName.getText().toString();
        String desc = etDesc.getText().toString();

        Heroes heroes = new Heroes(name,desc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(name,desc);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(Register_Activity.this,"Code" +response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this,"Successfully Saved",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this,"Error" +t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    //Using @FieldMap(Key and Value)
    private void SaveFieldMap()
    {
        String name= etName.getText().toString();
        String desc = etDesc.getText().toString();

        Map<String,String> map =  new HashMap<>();
        map.put("name",name);
        map.put("desc",desc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(map);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(Register_Activity.this,"Code" +response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this,"Successfully Saved",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this,"Error" +t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}

