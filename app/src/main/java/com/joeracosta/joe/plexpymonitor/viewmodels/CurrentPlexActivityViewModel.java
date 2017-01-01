package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.joeracosta.joe.plexpymonitor.BR;
import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.databinding.ScreenCurrentActivityBinding;
import com.joeracosta.joe.plexpymonitor.events.CurrentPlexActivityEvent;
import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;
import com.joeracosta.joe.plexpymonitor.view.CurrentPlexActivityScreen;
import com.joeracosta.joe.plexpymonitor.view.adapters.CurrentSessionsAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityViewModel extends BaseObservable {

    private boolean mIsLoading;
    private ScreenCurrentActivityBinding mBinding;
    private CurrentPlexActivityScreen mView;
    private boolean mNoSessions;
    private boolean mErrorLoading;


    /**
     * Constructor when you need to load the sessions
     */
    public CurrentPlexActivityViewModel(ScreenCurrentActivityBinding binding, CurrentPlexActivityScreen screen) {
        initialize(binding, screen);
        mIsLoading = true;
        CurrentPlexActivity.getCurrentActivity();
    }

    /**
     * Constructor for when sessions are coming from a save state
     */
    public CurrentPlexActivityViewModel(ScreenCurrentActivityBinding binding, CurrentPlexActivityScreen screen,
                                        ArrayList<CurrentPlexActivity.Response.Data.Session> sessions){
        initialize(binding, screen);
        setAdapter(sessions);
    }

    private void initialize(ScreenCurrentActivityBinding binding, CurrentPlexActivityScreen screen){
        EventBus.getDefault().register(this);
        mBinding = binding;
        mView = screen;


        mBinding.refreshLayout.setOnRefreshListener(() -> {
            CurrentPlexActivity.getCurrentActivity();
            mBinding.refreshLayout.setRefreshing(true);
            notifyChange();
        });
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCurrentActivityLoaded(CurrentPlexActivityEvent event){

        mBinding.refreshLayout.setRefreshing(false);

        if (event.success){
            mView.setSessions(event.response.getSessions());
            setAdapter(event.response.getSessions());
            mErrorLoading = false;
        } else {
            mErrorLoading = true;
        }

        mIsLoading = false;
        notifyChange();
    }


    private void setAdapter(ArrayList<CurrentPlexActivity.Response.Data.Session> sessions){
        if (sessions == null || sessions.isEmpty()){
            mNoSessions = true;
            notifyPropertyChanged(BR.noSessions);
            return;
        }
        mBinding.sessionList.setAdapter(new CurrentSessionsAdapter(sessions));
        mNoSessions = false;
        notifyPropertyChanged(BR.noSessions);
    }

    @Bindable
    public int getLoadingVisibility(){
        return mIsLoading ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getContentVisibility(){
        return !mIsLoading ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getNoSessions() {
        return mNoSessions ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getErrorLoading() {
        return mErrorLoading ? View.VISIBLE : View.GONE;
    }

}
