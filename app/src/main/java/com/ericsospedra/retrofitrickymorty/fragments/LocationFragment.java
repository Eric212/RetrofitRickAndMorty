package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.CharacterAdapter;
import com.ericsospedra.retrofitrickymorty.adapters.LocationAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.Episode;
import com.ericsospedra.retrofitrickymorty.models.Location;
import com.ericsospedra.retrofitrickymorty.models.LocationResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    private IOnClickListener listener;
    private List<Location> locations;
    private IApiService api;
    private LocationAdapter adapter;
    private SearchView svLocation;

    public LocationFragment() {
        super(R.layout.location_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = ApiRickAndMorty.getInstance();
        api.getAllLocations().enqueue(new Callback<LocationResult>() {
            @Override
            public void onResponse(Call<LocationResult> call, Response<LocationResult> response) {
                locations = new ArrayList<>();
                LocationResult result = response.body();
                for (int i = 1; i <= result.getInfo().getPages(); i++) {
                    int finalI = i;
                    api.getLocationsByPages(i).enqueue(new Callback<LocationResult>() {
                        @Override
                        public void onResponse(Call<LocationResult> call, Response<LocationResult> response) {
                            for(Location location : response.body().getResults()){
                                locations.add(location);
                            }
                            if (finalI == result.getInfo().getPages()) {
                                Collections.sort(locations, Comparator.comparing(Location::getId));
                                RecyclerView rvLocation = view.findViewById(R.id.rvLocation);
                                adapter = new LocationAdapter(locations, listener);
                                rvLocation.setAdapter(adapter);
                                rvLocation.setLayoutManager(new GridLayoutManager(view.getContext(),3,GridLayoutManager.VERTICAL,false));
                                svLocation = view.findViewById(R.id.svLocation);
                                svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        adapter.search(newText);
                                        return false;
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<LocationResult> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<LocationResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (IOnClickListener) context;
    }
}
