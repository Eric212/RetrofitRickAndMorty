package com.ericsospedra.retrofitrickymorty.interfaces;

import com.ericsospedra.retrofitrickymorty.models.Character;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiService {

    @GET("character/{id}")
    public Call<Character> getCharacter(@Path("id") int id);
}
