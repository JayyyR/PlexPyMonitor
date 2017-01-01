package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.joeracosta.joe.plexpymonitor.databinding.ScreenCurrentActivityBinding;
import com.joeracosta.joe.plexpymonitor.events.CurrentPlexActivityEvent;
import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;
import com.joeracosta.joe.plexpymonitor.view.adapters.CurrentSessionsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityViewModel extends BaseObservable {

    private boolean mIsLoading;
    private ArrayList<CurrentPlexActivity.Response.Data.Session> mSessions;
    private ScreenCurrentActivityBinding mBinding;

    public CurrentPlexActivityViewModel(ScreenCurrentActivityBinding binding) {
        EventBus.getDefault().register(this);
        mIsLoading = true;
        mBinding = binding;
        CurrentPlexActivity.getCurrentActivity();
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCurrentActivityLoaded(CurrentPlexActivityEvent event){

        if (event.success){
            setSessions(event.response.getSessions());
        } else {
            //todo failure
        }

        mIsLoading = false;
        notifyChange();

    }


    private void setSessions(ArrayList<CurrentPlexActivity.Response.Data.Session> sessions){

        if (sessions == null || sessions.isEmpty()){
            //todo no sessions
            return;
        }
        mSessions = sessions;
        mBinding.sessionList.setAdapter(new CurrentSessionsAdapter(mSessions));
    }

    @Bindable
    public int getLoadingVisibility(){
        return mIsLoading ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getContentVisibility(){
        return !mIsLoading ? View.VISIBLE : View.GONE;
    }

}
