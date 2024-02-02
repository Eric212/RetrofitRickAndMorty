package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.EpisodeArrayAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailFragment extends Fragment {
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
    private ListView lvEpisodes;

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
        lvEpisodes = view.findViewById(R.id.lvEpisodes);
        api = ApiRickAndMorty.getInstance();
        api.getCharacter(characterId).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character c = response.body();
                Picasso.get().load(c.getImage()).into(ivDetailCharacter);
                ivDetailCharacter.getLayoutParams().width=512;
                ivDetailCharacter.getLayoutParams().height =512;
                tvName.setText(c.getName());
                tvGender.setText(c.getGender());
                tvType.setText(c.getType());
                tvSpiece.setText(c.getSpecies());
                tvStatus.setText(c.getStatus());
                tvOrigin.setText(c.getOrigin().getName());
                tvLocation.setText(c.getLocation().getName());
                //EpisodeArrayAdapter adapter = new EpisodeArrayAdapter(getContext(),c.);
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
    }
}
