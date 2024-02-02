package com.ericsospedra.retrofitrickymorty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ericsospedra.retrofitrickymorty.R;
import com.ericsospedra.retrofitrickymorty.adapters.StartMenuAdapter;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.StartMenuItem;

import java.util.List;

public class StartMenuFragment extends Fragment {

    private List<StartMenuItem> startMenuItems;
    private IOnClickListener listener;

    public interface IOnAttach{
        List<StartMenuItem> getList();
    }
    public StartMenuFragment() {
        super(R.layout.start_menu_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvStartMenu = view.findViewById(R.id.rvStartMenu);
        StartMenuAdapter adapter = new StartMenuAdapter(startMenuItems, listener);
        rvStartMenu.setAdapter(adapter);
        rvStartMenu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttach iOnAttach = (IOnAttach) context;
        startMenuItems = iOnAttach.getList();
        listener = (IOnClickListener) context;
    }
}
