package com.ericsospedra.retrofitrickymorty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ericsospedra.retrofitrickymorty.R;

import java.util.ArrayList;

public class EpisodeArrayAdapter extends ArrayAdapter<String> {
   public EpisodeArrayAdapter(Context context, ArrayList<String> episodes){
       super(context,0,episodes);
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String episode = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_episode,parent,false);
        }
        return null;
    }
}
