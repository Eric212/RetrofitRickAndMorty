package com.ericsospedra.retrofitrickymorty.interfaces;

import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.CharacterResult;
import com.ericsospedra.retrofitrickymorty.models.Episode;
import com.ericsospedra.retrofitrickymorty.models.EpisodeResult;
import com.ericsospedra.retrofitrickymorty.models.Location;
import com.ericsospedra.retrofitrickymorty.models.LocationResult;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiService {

    @GET("character")
    Call<CharacterResult> getAllCharacter();

    @GET("character/{id}")
    Call<Character> getCharacter(@Path("id") int id);

    @GET("character/")
    Call<CharacterResult> getCharactersByPages(@Query("page") int page);

    @GET("episode")
    Call<EpisodeResult> getAllEpisodes();

    @GET("episode/{id}")
    Call<Episode> getEpisodeById(@Path("id") int id);

    @GET("episode/")
    Call<EpisodeResult> getEpisodesByPages(@Query("page") int page);

    @GET("location")
    Call<LocationResult> getAllLocations();

    @GET("location/")
    Call<LocationResult> getLocationsByPages(@Query("page") int page);

    @GET("location/{id}")
    Call<Location> getLocationById(@Path("id") int id);
}
