package com.joeracosta.joe.plexpymonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.joeracosta.joe.plexpymonitor.view.Host;
import com.joeracosta.library.Map.ViewMap;

public class MapHost extends AppCompatActivity implements Host {

    private static final String VIEW_MAP_KEY = "com.joeracosta.VIEW_MAP_KEY";

    private ViewMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_host);

        map = ViewMap.create((ViewGroup) findViewById(R.id.app_content));

        if (savedInstanceState != null){
            map.rebuildFromBundle(savedInstanceState, VIEW_MAP_KEY);
        } else {

            //shared pref

        }
    }

    @Override
    public void setAppBarTitle(String title) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
