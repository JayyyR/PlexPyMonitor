package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.text.Editable;
import android.text.TextWatcher;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.view.TextWatcherAdapter;
import com.joeracosta.joe.plexpymonitor.view.UserDetailsScreen;

/**
 * Created by jacosta on 12/31/16.
 * View Model for the user details screen
 */

public class UserDetailsViewModel extends BaseObservable {

    private String mAuthKey;
    private String mIPAddress;
    private String mPort;
    private UserDetailsScreen mView;

    public UserDetailsViewModel(UserDetailsScreen screen){
        mView = screen;
    }

    public void authenticate(){

        if (mAuthKey == null || mIPAddress == null || mPort == null){
            mView.notifyUser(R.string.fill_user_details);
        }

    }

    public TextWatcher ipWatcher = new TextWatcherAdapter(){
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mIPAddress = s.toString();
        }
    };

    public TextWatcher portWatcher = new TextWatcherAdapter(){
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mPort = s.toString();
        }
    };

    public TextWatcher authKeyWatcher = new TextWatcherAdapter(){
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            mAuthKey = s.toString();
        }
    };


}
