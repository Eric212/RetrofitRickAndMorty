package com.ericsospedra.retrofitrickymorty.interfaces;

import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.CharacterResult;
import com.ericsospedra.retrofitrickymorty.models.Episode;
import com.ericsospedra.retrofitrickymorty.models.EpisodeResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiService {

    @GET("character")
    public Call<CharacterResult> getAllCharacter();

    @GET("character/{id}")
    public Call<Character> getCharacter(@Path("id") int id);

    @GET("character/")
    public Call<CharacterResult> getCharacterByPages(@Query("page") int page);

    @GET("episode")
    public Call<EpisodeResult> getEpisodes();

    @GET("episode/{id}")
    public Call<Episode> getEpisodeById(@Path("id") int id);
}
