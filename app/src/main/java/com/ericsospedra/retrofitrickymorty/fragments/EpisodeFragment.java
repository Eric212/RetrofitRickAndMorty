package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.EpisodeAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.Episode;
import com.ericsospedra.retrofitrickymorty.models.EpisodeResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeFragment extends Fragment {
    private IOnClickListener listener;
    private IApiService api;
    private List<Episode> episodes;
    private EpisodeAdapter adapter;
    private SearchView svEpisode;

    public EpisodeFragment() {
        super(R.layout.episode_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = ApiRickAndMorty.getInstance();
        api.getAllEpisodes().enqueue(new Callback<EpisodeResult>() {
            @Override
            public void onResponse(Call<EpisodeResult> call, Response<EpisodeResult> response) {
                episodes = new ArrayList<>();
                EpisodeResult result = response.body();
                for(int i = 1; i<= result.getInfo().getPages();i++){
                    int finalI = i;
                    api.getEpisodesByPages(i).enqueue(new Callback<EpisodeResult>() {
                        @Override
                        public void onResponse(Call<EpisodeResult> call, Response<EpisodeResult> response) {
                            episodes.addAll(response.body().getResults());
                            if(finalI == result.getInfo().getPages()){
                                Collections.sort(episodes, Comparator.comparing(Episode::getId));
                                RecyclerView rvEpisodes = view.findViewById(R.id.rvEpisodes);
                                adapter = new EpisodeAdapter(episodes,listener);
                                rvEpisodes.setAdapter(adapter);
                                rvEpisodes.setLayoutManager(new GridLayoutManager(view.getContext(),3,GridLayoutManager.VERTICAL,false));
                                svEpisode = view.findViewById(R.id.svEpisode);
                                svEpisode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                        public void onFailure(Call<EpisodeResult> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<EpisodeResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (IOnClickListener) context;
    }
}
