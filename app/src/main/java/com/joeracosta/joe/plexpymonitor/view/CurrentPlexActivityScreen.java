package com.joeracosta.joe.plexpymonitor.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.databinding.ScreenCurrentActivityBinding;
import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;
import com.joeracosta.joe.plexpymonitor.viewmodels.CurrentPlexActivityViewModel;
import com.joeracosta.library.Screen;
import com.joeracosta.library.ViewFactory;

import java.util.ArrayList;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityScreen extends Screen {

    private static final String SESSION_KEY = "com.joeracosta.session_key";

    private ScreenCurrentActivityBinding mBinding;
    private CurrentPlexActivityViewModel mViewModel;
    private Host mHost;
    private ArrayList<CurrentPlexActivity.Response.Data.Session> mSessions;
    private Bundle mSavedBundle;

    public static class Factory extends ViewFactory {

        @Override
        public int getLayoutResource() {
            return R.layout.screen_current_activity;
        }
    }

    public CurrentPlexActivityScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScreenAttached() {
        super.onScreenAttached();
        mHost = (Host) getContext();
        mHost.setAppBarTitle(getContext().getString(R.string.current_sessions));
        mBinding = DataBindingUtil.bind(this);
        mBinding.sessionList.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mSavedBundle != null){
            mSessions = (ArrayList<CurrentPlexActivity.Response.Data.Session>) mSavedBundle.getSerializable(SESSION_KEY);
            mViewModel = new CurrentPlexActivityViewModel(mBinding, this, mSessions);
            mSavedBundle = null;
        } else {
            mViewModel = new CurrentPlexActivityViewModel(mBinding, this);
        }

        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onScreenDetached() {
        super.onScreenDetached();
        mViewModel.destroy();
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {
        bundle.putSerializable(SESSION_KEY, mSessions);
        return super.onSaveState(bundle);
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        mSavedBundle = bundle;
        super.onRestoreState(bundle);
    }

    public void setSessions(ArrayList<CurrentPlexActivity.Response.Data.Session> sessions) {
        mSessions = sessions;
    }

    @Override
    public int getViewId() {
        return R.id.current_activity_screen;
    }
}
