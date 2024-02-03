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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.AuthorViewHolder> {

    private List<Character> charactersOriginal;
    private List<Character> characters;
    private final IOnClickListener listener;

    public CharacterAdapter(List<Character> characters, IOnClickListener listener) {
        this.characters = characters;
        charactersOriginal = new ArrayList<>();
        charactersOriginal.addAll(characters);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterAdapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AuthorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.AuthorViewHolder holder, int position) {
        holder.onBindAuthor(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void search(String search) {
        int searchLength = search.length();
        if(searchLength == 0){
            characters.clear();
            characters.addAll(charactersOriginal);
        }else {
            List<Character> temporalyList = charactersOriginal.stream().filter(c ->c.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            characters.clear();
            characters.addAll(temporalyList);
        }
        notifyDataSetChanged();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivCharacter;
        private final TextView tvCharacter;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.ivCharacter);
            tvCharacter = itemView.findViewById(R.id.tvCharacter);
            itemView.setOnClickListener(this);
        }

        public void onBindAuthor(Character character) {
            Picasso.get().load(character.getImage()).into(ivCharacter);
            tvCharacter.setText(character.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(String.valueOf(characters.get(getAdapterPosition()).getId()));
        }
    }
}
