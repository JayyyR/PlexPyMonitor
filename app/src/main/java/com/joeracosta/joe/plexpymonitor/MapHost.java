package com.joeracosta.joe.plexpymonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.joeracosta.joe.plexpymonitor.events.AuthResponseEvent;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;
import com.joeracosta.joe.plexpymonitor.view.CurrentPlexActivityScreen;
import com.joeracosta.joe.plexpymonitor.view.Host;
import com.joeracosta.joe.plexpymonitor.view.UserDetailsScreen;
import com.joeracosta.library.Map.ViewMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MapHost extends AppCompatActivity implements Host {

    private static final String VIEW_MAP_KEY = "com.joeracosta.VIEW_MAP_KEY";

    private ViewMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_map_host);


        //temp
        /*
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        */
        //

        mMap = ViewMap.create((ViewGroup) findViewById(R.id.app_content));

        if (savedInstanceState != null){
            mMap.rebuildFromBundle(savedInstanceState, VIEW_MAP_KEY);
        } else {

            //shared pref check
            if (!authenticated()){
                mMap.show(R.id.userdetails_screen, new UserDetailsScreen.Factory());
            } else {

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                PyAPI.initialize(sharedPref.getString(getString(R.string.auth_key_key), ""),
                        sharedPref.getString(getString(R.string.ip_address_key), ""),
                        sharedPref.getString(getString(R.string.port_key), ""),
                        sharedPref.getString(getString(R.string.http_root_key), ""));

                mMap.show(R.id.current_activity_screen, new CurrentPlexActivityScreen.Factory());
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMap.saveToBundle(outState, VIEW_MAP_KEY);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Subscribe
    public void onAuthEvent(AuthResponseEvent event){
        if (event.authSuccess){
            storeStringInPref(getString(R.string.ip_address_key), event.ipAddress);
            storeStringInPref(getString(R.string.port_key), event.port);
            storeStringInPref(getString(R.string.auth_key_key), event.apiKey);
            storeStringInPref(getString(R.string.http_root_key), event.httpRoot);

            mMap.clear();
            mMap.show(R.id.current_activity_screen, new CurrentPlexActivityScreen.Factory());
        }
    }

    private void storeStringInPref(String key, String valToStore){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, valToStore);
        editor.apply();
    }
}
