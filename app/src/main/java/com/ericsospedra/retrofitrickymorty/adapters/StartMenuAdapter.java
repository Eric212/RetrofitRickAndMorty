package com.ericsospedra.retrofitrickymorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.StartMenuItem;

import java.util.List;

public class StartMenuAdapter extends RecyclerView.Adapter<StartMenuAdapter.StartMenuViewHolder> {
    private final List<StartMenuItem> startMenuItems;
    private final IOnClickListener listener;

    public StartMenuAdapter(List<StartMenuItem> startMenuItems, IOnClickListener listener) {
        this.startMenuItems = startMenuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StartMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StartMenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_start_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StartMenuViewHolder holder, int position) {
        holder.onBindStartMenu(startMenuItems.get(position));
    }


    @Override
    public int getItemCount() {
        return startMenuItems.size();
    }

    public class StartMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivStartMenu;
        private final TextView tvStartMenu;
        public StartMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStartMenu = itemView.findViewById(R.id.ivStartMenu);
            tvStartMenu = itemView.findViewById(R.id.tvStartMenu);
            itemView.setOnClickListener(this);
        }

        public void onBindStartMenu(StartMenuItem startMenuItem) {
            ivStartMenu.setImageResource(itemView.getContext().getResources().getIdentifier(startMenuItem.getImage(),"drawable",itemView.getContext().getPackageName()));
            ivStartMenu.getLayoutParams().width = 256;
            ivStartMenu.getLayoutParams().height = 256;
            tvStartMenu.setText(startMenuItem.getName());
        }

        @Override
        public void onClick(View v) {
            ConstraintLayout layout = (ConstraintLayout) v;
            TextView textView = layout.findViewById(R.id.tvStartMenu);
            listener.onClick(textView.getText().toString());
        }
    }
}
