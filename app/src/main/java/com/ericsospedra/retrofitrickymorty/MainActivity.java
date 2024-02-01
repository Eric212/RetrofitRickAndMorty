package com.ericsospedra.retrofitrickymorty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private int currentId = 1;
    private IApiService api;
    private ImageView ivCharacter;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvStatus;
    private TextView tvSpecies;
    private TextView tvType;
    private Button bPrev;
    private Button bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        api = ApiRickAndMorty.getInstance();
        ivCharacter = findViewById(R.id.ivCharacter);
        tvName = findViewById(R.id.tvName);
        tvGender = findViewById(R.id.tvGender);
        tvStatus = findViewById(R.id.tvStatus);
        tvSpecies = findViewById(R.id.tvSpecies);
        tvType = findViewById(R.id.tvType);
        bPrev = findViewById(R.id.bPrev);
        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    currentId--;
                if (currentId < 1) {
                    currentId =1;
                }
                    showCharacter();

            }
        });
        Button bNext = findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentId++;
                showCharacter();
            }
        });
        showCharacter();
    }

    public void showCharacter() {
        api.getCharacter(currentId).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful()) {
                    Character character = response.body();
                    if (character != null) {
                        tvName.setText(character.getName());
                        tvGender.setText(character.getGender());
                        tvStatus.setText(character.getStatus());
                        tvSpecies.setText(character.getSpecies());
                        tvType.setText(character.getType());
                        Picasso.get().load(character.getImage()).into(ivCharacter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

            }
        });
    }
}