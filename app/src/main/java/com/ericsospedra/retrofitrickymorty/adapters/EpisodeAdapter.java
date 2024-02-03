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
import com.ericsospedra.retrofitrickymorty.models.Episode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {
    private IOnClickListener listener;
    private List<Episode> episodes;
    private List<Episode> episodesOriginal;

    public EpisodeAdapter(List<Episode> episodes, IOnClickListener listener) {
        this.listener = listener;
        this.episodes = episodes;
        episodesOriginal = new ArrayList<>();
        episodesOriginal.addAll(episodes);
    }

    @NonNull
    @Override
    public EpisodeAdapter.EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.EpisodeViewHolder holder, int position) {
        holder.onBindEpisode(episodes.get(position));
    }
    public void search(String search) {
        int searchLength = search.length();
        if(searchLength == 0){
            episodes.clear();
            episodes.addAll(episodesOriginal);
        }else {
            List<Episode> temporalyList = episodesOriginal.stream().filter(c ->c.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            episodes.clear();
            episodes.addAll(temporalyList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivEpisode;
        private TextView tvSeason;
        private TextView tvEpisode;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEpisode = itemView.findViewById(R.id.ivEpisode);
            tvSeason = itemView.findViewById(R.id.tvSeason);
            tvEpisode = itemView.findViewById(R.id.tvEpisode);
            itemView.setOnClickListener(this);
        }

        public void onBindEpisode(Episode episode) {
            ivEpisode.setImageResource(itemView.getContext().getResources().getIdentifier("episode","drawable",itemView.getContext().getPackageName()));
            ivEpisode.getLayoutParams().width=300;
            ivEpisode.getLayoutParams().height=300;
            tvSeason.setText(episode.getEpisode());
            tvEpisode.setText(episode.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(String.valueOf(episodes.get(getAdapterPosition()).getId()));
        }
    }
}
