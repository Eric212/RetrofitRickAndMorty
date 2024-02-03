package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.CharacterLiteAdapter;
import com.ericsospedra.retrofitrickymorty.adapters.LocationAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.Episode;
import com.ericsospedra.retrofitrickymorty.models.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDetailFragment extends Fragment {
    public interface IOnAttach{
        int getLocationId();
    }
    private int locationId;
    private IOnClickListener listener;
    private List<String> charactersString;
    private ImageView ivLocationDetail;
    private TextView tvLocationName;
    private TextView tvLocationType;
    private TextView tvDimension;
    private RecyclerView rvLocationCharacters;
    private IApiService api;

    public LocationDetailFragment() {
        super(R.layout.location_detail_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = ApiRickAndMorty.getInstance();
        ivLocationDetail = view.findViewById(R.id.ivLocationDetail);
        tvLocationName = view.findViewById(R.id.tvLocationName);
        tvLocationType = view.findViewById(R.id.tvLocationType);
        tvDimension = view.findViewById(R.id.tvDimension);
        rvLocationCharacters = view.findViewById(R.id.rvLocationCharacters);
        api.getLocationById(locationId).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                ivLocationDetail.setImageResource(view.getContext().getResources().getIdentifier("r_m_defaullt","drawable",view.getContext().getPackageName()));
                tvLocationName.setText(location.getName());
                tvLocationType.setText(location.getType());
                tvDimension.setText(location.getDimension());
                charactersString = location.getResidents();
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
                                Collections.sort(characters, Comparator.comparing(Character::getId));
                                CharacterLiteAdapter adapter = new CharacterLiteAdapter(characters,listener);
                                rvLocationCharacters.setAdapter(adapter);
                                rvLocationCharacters.setLayoutManager(new GridLayoutManager(view.getContext(),3,GridLayoutManager.VERTICAL,false));
                            }
                        }

                        @Override
                        public void onFailure(Call<Character> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttach iOnAttach = (IOnAttach) context;
        listener = (IOnClickListener) context;
        locationId = iOnAttach.getLocationId();
    }
}
