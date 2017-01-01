package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;

/**
 * Created by jacosta on 12/31/16.
 */

public class SessionCardViewModel extends BaseObservable {

    private CurrentPlexActivity.Response.Data.Session mSession;

    public void setSession(CurrentPlexActivity.Response.Data.Session session){
        mSession = session;
        notifyChange();
    }

    @Bindable
    public String getTitle(){
        return mSession.getFullTitle();
    }

    @Bindable
    public String getUser(){
        return mSession.getUser();
    }
}
