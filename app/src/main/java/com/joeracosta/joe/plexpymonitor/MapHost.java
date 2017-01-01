package com.joeracosta.joe.plexpymonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.joeracosta.joe.plexpymonitor.view.Host;
import com.joeracosta.joe.plexpymonitor.view.UserDetailsScreen;
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

            //shared pref check
            if (!authenticated()){
                map.show(R.id.userdetails_screen, new UserDetailsScreen.Factory());
            } else {
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        map.saveToBundle(outState, VIEW_MAP_KEY);
        super.onSaveInstanceState(outState);
    }

    private boolean authenticated() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.contains(getString(R.string.ip_address_key)) &&
                sharedPref.contains(getString(R.string.auth_key_key)) &&
                sharedPref.contains(getString(R.string.port_key));
    }

    @Override
    public void setAppBarTitle(String title) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void storeIp(String ip) {
        storeStringInPref(getString(R.string.ip_address_key), ip);
    }

    @Override
    public void storePort(String port) {
        storeStringInPref(getString(R.string.port_key), port);
    }

    @Override
    public void storeAuth(String auth) {
        storeStringInPref(getString(R.string.auth_key_key), auth);
    }

    private void storeStringInPref(String key, String valToStore){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, valToStore);
        editor.apply();
    }
}
