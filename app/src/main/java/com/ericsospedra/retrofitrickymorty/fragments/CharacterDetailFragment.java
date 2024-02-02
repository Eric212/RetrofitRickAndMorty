package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.EpisodeLiteAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailFragment extends Fragment {
    private IOnClickListener listener;

    public interface IOnAttach{
        int getCharacterId();
    }
    private IApiService api;
    private int characterId;
    private ImageView ivDetailCharacter;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvType;
    private TextView tvSpiece;
    private TextView tvStatus;
    private TextView tvOrigin;
    private TextView tvLocation;
    private RecyclerView rvEpisode;
    private List<String> episodesURL;

    public CharacterDetailFragment() {
        super(R.layout.character_detail_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivDetailCharacter = view.findViewById(R.id.ivDetailCharacter);
        tvName = view.findViewById(R.id.tvName);
        tvGender = view.findViewById(R.id.tvGender);
        tvType = view.findViewById(R.id.tvType);
        tvSpiece = view.findViewById(R.id.tvSpecies);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvOrigin = view.findViewById(R.id.tvOrigin);
        tvLocation = view.findViewById(R.id.tvLocation);
        rvEpisode = view.findViewById(R.id.rvEpisode);
        api = ApiRickAndMorty.getInstance();
        api.getCharacter(characterId).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character c = response.body();
                Picasso.get().load(c.getImage()).into(ivDetailCharacter);
                ivDetailCharacter.getLayoutParams().width= 512;
                ivDetailCharacter.getLayoutParams().height =512;
                tvName.setText(c.getName());
                tvGender.setText(c.getGender());
                tvType.setText(c.getType());
                tvSpiece.setText(c.getSpecies());
                tvStatus.setText(c.getStatus());
                tvOrigin.setText(c.getOrigin().getName());
                tvLocation.setText(c.getLocation().getName());
                List<String>
                for(String episode : episodesURL){

                }
                EpisodeLiteAdapter adapter = new EpisodeLiteAdapter(,listener);
                rvEpisode.setAdapter(adapter);
                rvEpisode.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
                rvEpisode.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttach iOnAttach = (IOnAttach) context;
        characterId = iOnAttach.getCharacterId();
        listener = (IOnClickListener) context;
    }
}
