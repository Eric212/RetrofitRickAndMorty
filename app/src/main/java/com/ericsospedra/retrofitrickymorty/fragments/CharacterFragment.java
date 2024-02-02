package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.CharacterAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.ericsospedra.retrofitrickymorty.models.CharacterResult;
import com.ericsospedra.retrofitrickymorty.models.Result;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharacterFragment extends Fragment {

    private List<Character> characters;

    private IOnClickListener listener;
    private IApiService api;
    private CharacterAdapter adapter;
    private SearchView svCharacter;

    public CharacterFragment() {
        super(R.layout.character_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api.getAllCharacter().enqueue(new Callback<CharacterResult>() {
            @Override
            public void onResponse(Call<CharacterResult> call, Response<CharacterResult> response) {
                if (response.isSuccessful()) {
                    characters =new ArrayList<>();
                    Result<Character> result = response.body();
                    for(int i = 0; i<=result.getInfo().getPages();i++){
                        int finalI = i;
                        api.getCharacterByPages(i).enqueue(new Callback<CharacterResult>() {
                            @Override
                            public void onResponse(Call<CharacterResult> call, Response<CharacterResult> response) {
                                characters.addAll(response.body().getResults());
                                if(finalI ==result.getInfo().getPages()){
                                    RecyclerView rvAuthor = view.findViewById(R.id.rvCharacter);
                                    adapter = new CharacterAdapter(characters, listener);
                                    rvAuthor.setAdapter(adapter);
                                    rvAuthor.setLayoutManager(new GridLayoutManager(view.getContext(),3,GridLayoutManager.VERTICAL,false));
                                    svCharacter = view.findViewById(R.id.svCharacter);
                                    svCharacter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                            public void onFailure(Call<CharacterResult> call, Throwable t) {
                                Log.e(CharacterFragment.class.getSimpleName(),t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<CharacterResult> call, Throwable t) {
                Log.e(CharacterFragment.class.getSimpleName(), t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        api = ApiRickAndMorty.getInstance();
        listener = (IOnClickListener) context;
    }
}
