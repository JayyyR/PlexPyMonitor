package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.joeracosta.joe.plexpymonitor.events.CurrentPlexActivityEvent;
import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityViewModel extends BaseObservable {

    private boolean mIsLoading;

    public CurrentPlexActivityViewModel() {
        EventBus.getDefault().register(this);
        mIsLoading = true;
        CurrentPlexActivity.getCurrentActivity();
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCurrentActivityLoaded(CurrentPlexActivityEvent event){
        if (event.response.isSuccess()){


        } else {

        }
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
