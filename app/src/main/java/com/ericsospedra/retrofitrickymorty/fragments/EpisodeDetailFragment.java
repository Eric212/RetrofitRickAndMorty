package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.CharacterLiteAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.Episode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeDetailFragment extends Fragment {
    public interface IOnAttach{
        int getEpisodeId();
    }
    private Episode episode;
    private List<String> charactersString;
    private IOnClickListener listener;
    private TextView tvEpisodeName;
    private TextView tvAirDate;
    private TextView tvEpisode;
    private RecyclerView rvCharacter;
    private IApiService api;
    private int episodeId;

    public EpisodeDetailFragment() {
        super(R.layout.episode_detail_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = ApiRickAndMorty.getInstance();
        charactersString = new ArrayList<>();
        tvEpisode = view.findViewById(R.id.tvEpisode);
        tvEpisodeName = view.findViewById(R.id.tvEpisodeName);
        tvAirDate = view.findViewById(R.id.tvAirDate);
        rvCharacter = view.findViewById(R.id.rvEpisodeCharacters);
        api.getEpisodeById(episodeId).enqueue(new Callback<Episode>() {
            @Override
            public void onResponse(Call<Episode> call, Response<Episode> response) {
                episode = response.body();
                tvEpisode.setText(episode.getEpisode());
                tvEpisodeName.setText(episode.getName());
                tvAirDate.setText(episode.getAir_date());
                charactersString = episode.getCharacters();
                List<String> charactersId = new ArrayList<>();
                List<Character> characters = new ArrayList<>();
                for(String c : charactersString){
                    String[] temporalyArray = c.split("/");
                    charactersId.add(temporalyArray[temporalyArray.length-1]);
                }
                for(String s : charactersId){
                    api.getCharacter(Integer.parseInt(s)).enqueue(new Callback<Character>() {
                        @Override
                        public void onResponse(Call<Character> call, Response<Character> response) {
                            Character character = response.body();
                            characters.add(character);
                            if(s.equals(charactersId.get(charactersId.size()-1))){
                                CharacterLiteAdapter adapter = new CharacterLiteAdapter(characters,listener);
                                rvCharacter.setAdapter(adapter);
                                rvCharacter.setLayoutManager(new GridLayoutManager(view.getContext(),3,GridLayoutManager.VERTICAL,false));
                            }
                        }

                        @Override
                        public void onFailure(Call<Character> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Episode> call, Throwable t) {

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttach iOnAttach = (IOnAttach)context;
        listener = (IOnClickListener) context;
        episodeId = iOnAttach.getEpisodeId();
    }
}
