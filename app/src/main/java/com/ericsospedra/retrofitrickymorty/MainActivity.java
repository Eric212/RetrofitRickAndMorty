package com.ericsospedra.retrofitrickymorty;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ericsospedra.retrofitrickymorty.fragments.CharacterDetailFragment;
import com.ericsospedra.retrofitrickymorty.fragments.CharacterFragment;
import com.ericsospedra.retrofitrickymorty.fragments.EpisodeDetailFragment;
import com.ericsospedra.retrofitrickymorty.fragments.EpisodeFragment;
import com.ericsospedra.retrofitrickymorty.fragments.LocationDetailFragment;
import com.ericsospedra.retrofitrickymorty.fragments.LocationFragment;
import com.ericsospedra.retrofitrickymorty.fragments.StartMenuFragment;
import com.ericsospedra.retrofitrickymorty.interfaces.IApiService;
import com.ericsospedra.retrofitrickymorty.interfaces.IOnClickListener;
import com.ericsospedra.retrofitrickymorty.models.ApiRickAndMorty;
import com.ericsospedra.retrofitrickymorty.models.StartMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements StartMenuFragment.IOnAttach,
        IOnClickListener,
        CharacterDetailFragment.IOnAttach,
        EpisodeDetailFragment.IOnAttach,
        LocationDetailFragment.IOnAttach {
    private Stack<Integer> idsStack = new Stack<>();
    private FragmentManager manager;
    private Toolbar toolbar;
    private enum START_MENU_ITEM {Characters, Locations, Episodes}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        toolbar = findViewById(R.id.tbMenu);
        toolbar.setTitle("Rick and Morty");
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
                } else if (s.equals(START_MENU_ITEM.Locations.toString())) {
                    manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, LocationFragment.class, null).commit();
                } else {
                    manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, EpisodeFragment.class, null).commit();
                }
            }else if(f instanceof CharacterFragment){
                idsStack.push(Integer.parseInt(s));
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, CharacterDetailFragment.class,null).commit();
            }else if(f instanceof CharacterDetailFragment){
                idsStack.push(Integer.parseInt(s));
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain, EpisodeDetailFragment.class,null).commit();
            } else if (f instanceof EpisodeDetailFragment) {
                idsStack.push(Integer.parseInt(s));
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain,CharacterDetailFragment.class,null).commit();
            } else if (f instanceof EpisodeFragment) {
                idsStack.push(Integer.parseInt(s));
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain,EpisodeDetailFragment.class,null).commit();
            }else if (f instanceof LocationFragment){
                idsStack.push(Integer.parseInt(s));
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMain,LocationDetailFragment.class,null).commit();
            }
        }
    }
    @Override
    public int getCharacterId() {
        return idsStack.pop();
    }
    @Override
    public int getEpisodeId(){
        return idsStack.pop();
    }
    @Override
    public int getLocationId() {
        return idsStack.pop();
    }
}