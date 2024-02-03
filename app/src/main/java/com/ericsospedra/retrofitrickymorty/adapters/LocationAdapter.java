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
import com.ericsospedra.retrofitrickymorty.models.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private IOnClickListener listener;
    private List<Location> locations;
    private List<Location> locationsOriginal;
    public LocationAdapter(List<Location> locations, IOnClickListener listener) {
        this.locations = locations;
        locationsOriginal = new ArrayList<>();
        locationsOriginal.addAll(locations);
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        holder.onBindLocation(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
    public void search(String search) {
        int searchLength = search.length();
        if(searchLength == 0){
            locations.clear();
            locations.addAll(locationsOriginal);
        }else {
            List<Location> temporalyList = locationsOriginal.stream().filter(c ->c.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            locations.clear();
            locations.addAll(temporalyList);
        }
        notifyDataSetChanged();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivLocation;
        private TextView tvLocation;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLocation = itemView.findViewById(R.id.ivLocation);
            tvLocation = itemView.findViewById(R.id.tvLocationName);
            itemView.setOnClickListener(this);
        }

        public void onBindLocation(Location location) {
            ivLocation.setImageResource(itemView.getContext().getResources().getIdentifier("r_m_defaullt","drawable",itemView.getContext().getPackageName()));
            ivLocation.getLayoutParams().width=300;
            ivLocation.getLayoutParams().height=300;
            tvLocation.setText(location.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(String.valueOf(locations.get(getAdapterPosition()).getId()));
        }
    }
}
