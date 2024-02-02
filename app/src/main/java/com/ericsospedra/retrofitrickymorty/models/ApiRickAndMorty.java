package com.ericsospedra.retrofitrickymorty.models;

import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRickAndMorty{
    private static IApiService instance;
    private static final String BASE_URL ="https://rickandmortyapi.com/api/";

    private ApiRickAndMorty() {
    }
    public synchronized static IApiService getInstance(){
        if(instance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(IApiService.class);
        }
        return instance;
    }
}
