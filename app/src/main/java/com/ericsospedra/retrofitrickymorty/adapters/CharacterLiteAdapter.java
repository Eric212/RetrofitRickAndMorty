package com.ericsospedra.retrofitrickymorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterLiteAdapter extends RecyclerView.Adapter<CharacterLiteAdapter.EpisodeViewHolder> {
    private List<Character> characters;
    private IOnClickListener listener;

    public CharacterLiteAdapter(List<Character> characters, IOnClickListener listener) {
        this.characters = characters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterLiteAdapter.EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterLiteAdapter.EpisodeViewHolder holder, int position) {
        holder.onBindEpisode(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivCharacter;
        private TextView tvCharacter;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.ivCharacter);
            tvCharacter = itemView.findViewById(R.id.tvCharacter);
            itemView.setOnClickListener(this);
        }

        public void onBindEpisode(Character character) {
            Picasso.get().load(character.getImage()).into(ivCharacter);
            tvCharacter.setText(character.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(String.valueOf(characters.get(getAdapterPosition()).getId()));
        }
    }
}
