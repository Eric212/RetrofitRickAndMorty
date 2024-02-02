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

import java.util.List;

public class EpisodeLiteAdapter extends RecyclerView.Adapter<EpisodeLiteAdapter.EpisodeViewHolder> {

    private List<String> episodes;
    private IOnClickListener listener;

    public EpisodeLiteAdapter(List<String> episodes, IOnClickListener listener) {
        this.listener = listener;
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.onBindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCharacterName;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCharacterName = itemView.findViewById(R.id.tvEpisdoeName);
            itemView.setOnClickListener(this);
        }

        public void onBindEpisode(String episode) {
            tvCharacterName.setText(episode);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(String.valueOf(getAdapterPosition()+1));
        }
    }
}
