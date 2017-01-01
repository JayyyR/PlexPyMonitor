package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.events.AuthResponseEvent;
import com.joeracosta.joe.plexpymonitor.model.AuthenticationModel;
import com.joeracosta.joe.plexpymonitor.view.adapters.TextWatcherAdapter;
import com.joeracosta.joe.plexpymonitor.view.UserDetailsScreen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jacosta on 12/31/16.
 * View Model for the user details screen
 */

public class UserDetailsViewModel extends BaseObservable {

    private String mAuthKey;
    private String mIPAddress;
    private String mHTTPRoot;
    private String mPort;
    private UserDetailsScreen mView;
    private boolean mIsLoading;

    public UserDetailsViewModel(UserDetailsScreen screen) {
        mView = screen;
        EventBus.getDefault().register(this);
    }

    public void authenticate() {
        if (mAuthKey == null || mIPAddress == null || mPort == null) {
            mView.notifyUser(R.string.fill_user_details);
            return;
        }

        if (mHTTPRoot == null){
            mHTTPRoot = "";
        }

        mIsLoading = true;
        notifyChange();

        AuthenticationModel.testAuthentication(mIPAddress, mPort, mAuthKey, mHTTPRoot);
    }

    public TextWatcher ipWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mIPAddress = s.toString();
        }
    };

    public TextWatcher portWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mPort = s.toString();
        }
    };

    public TextWatcher authKeyWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mAuthKey = s.toString();
        }
    };

    public TextWatcher httpRootWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mHTTPRoot = s.toString();
        }
    };

    @Bindable
    public int getLoadingVisibility() {
        return mIsLoading ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getContentVisibility() {
        return !mIsLoading ? View.VISIBLE : View.GONE;
    }

    public String getIP() {
        return mIPAddress;
    }

    public String getAuth() {
        return mAuthKey;
    }

    public String getPort() {
        return mPort;
    }

    public String getHttpRoot() {
        return mHTTPRoot;
    }

    public void setData(String ip, String port, String auth, String httpRoot) {
        mIPAddress = ip;
        mPort = port;
        mAuthKey = auth;
        mHTTPRoot = httpRoot;
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onAuthEvent(AuthResponseEvent event){
        if (!event.authSuccess){
            mView.notifyUser(R.string.authenication_failed);
            mIsLoading = false;
            notifyChange();
        }
    }

}
