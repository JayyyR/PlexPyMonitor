package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.model.ArnoldResponse;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;
import com.joeracosta.joe.plexpymonitor.view.TextWatcherAdapter;
import com.joeracosta.joe.plexpymonitor.view.UserDetailsScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jacosta on 12/31/16.
 * View Model for the user details screen
 */

public class UserDetailsViewModel extends BaseObservable {

    private String mAuthKey;
    private String mIPAddress;
    private String mPort;
    private UserDetailsScreen mView;
    private boolean mIsLoading;

    public UserDetailsViewModel(UserDetailsScreen screen){
        mView = screen;
    }

    public void authenticate(){

        if (mAuthKey == null || mIPAddress == null || mPort == null){
            mView.notifyUser(R.string.fill_user_details);
            return;
        }

        mIsLoading = true;
        notifyChange();

        PyAPI.initialize(mAuthKey, mIPAddress, mPort);

        PyAPI.getPlexPyApi().testAPI().enqueue(new Callback<ArnoldResponse>() {
            @Override
            public void onResponse(Call<ArnoldResponse> call, Response<ArnoldResponse> response) {

                if (response.body().isSuccess()){
                    mView.saveDetails(mIPAddress, mPort, mAuthKey);
                } else {

                    mIsLoading = false;
                    notifyChange();

                    mView.notifyUser(R.string.authenication_failed);
                    PyAPI.clear();
                }
            }

            @Override
            public void onFailure(Call<ArnoldResponse> call, Throwable t) {

                mIsLoading = false;
                notifyChange();

                mView.notifyUser(R.string.authenication_failed);
                PyAPI.clear();
            }
        });

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

    @Bindable
    public int getLoadingVisibility(){
        return mIsLoading ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getContentVisibility(){
        return !mIsLoading ? View.VISIBLE : View.GONE;
    }


}
