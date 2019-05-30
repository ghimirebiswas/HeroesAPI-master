package com.heroesapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.HeroesAdapter;
import heroesapi.HeroesAPI;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Heroes> heroesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<List<Heroes>> listCall = heroesAPI.getAllHeroes(Url.Cookie);

        listCall.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {

                if (response.isSuccessful()){
                    heroesList = response.body();
                       HeroesAdapter adapter = new HeroesAdapter(MainActivity.this,heroesList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                }

            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
