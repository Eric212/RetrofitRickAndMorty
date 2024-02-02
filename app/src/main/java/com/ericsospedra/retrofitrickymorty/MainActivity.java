package com.ericsospedra.retrofitrickymorty;

import android.app.FragmentContainer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ericsospedra.retrofitrickymorty.fragments.CharacterDetailFragment;
import com.ericsospedra.retrofitrickymorty.fragments.CharacterFragment;
import com.ericsospedra.retrofitrickymorty.fragments.EpisodeFragment;
import com.ericsospedra.retrofitrickymorty.fragments.LocationFragment;
import com.ericsospedra.retrofitrickymorty.fragments.StartMenuFragment;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.StartMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StartMenuFragment.IOnAttach, IOnClickListener, CharacterDetailFragment.IOnAttach {
    private FragmentManager manager;
    private Toolbar toolbar;
    private enum START_MENU_ITEM {Characters, Locations, Episodes}
    private IApiService api;
    private ImageView ivFrontCover;
    private int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = ApiRickAndMorty.getInstance();
        manager = getSupportFragmentManager();
        toolbar = findViewById(R.id.tbMenu);
        toolbar.setTitle("Rick and Morty");
        ivFrontCover = findViewById(R.id.ivFrontCover);
    }

    public List<StartMenuItem> getList() {
        List<StartMenuItem> startMenuItemList = new ArrayList<>();
        for (START_MENU_ITEM item : START_MENU_ITEM.values()) {
            StartMenuItem startMenuItem = new StartMenuItem(item.toString().toLowerCase(), item.toString());
            startMenuItemList.add(startMenuItem);
        }
        return startMenuItemList;
    }

    @Override
    public void onClick(String s) {
        if (manager != null) {
            Fragment f = manager.findFragmentById(R.id.fcvMain);
            if (f instanceof StartMenuFragment) {
                if (s.equals(START_MENU_ITEM.Characters.toString())) {
                    manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, CharacterFragment.class, null).commit();
                    ivFrontCover.setVisibility(View.GONE);
                } else if (s.equals(START_MENU_ITEM.Locations.toString())) {
                    manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, LocationFragment.class, null).commit();
                    ivFrontCover.setVisibility(View.GONE);
                } else {
                    manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, EpisodeFragment.class, null).commit();
                    ivFrontCover.setVisibility(View.GONE);
                }
            }else if(f instanceof CharacterFragment){
                characterId = Integer.parseInt(s);
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, CharacterDetailFragment.class,null).commit();
            }
        }
    }
    @Override
    public int getCharacterId() {
        return characterId;
    }
}