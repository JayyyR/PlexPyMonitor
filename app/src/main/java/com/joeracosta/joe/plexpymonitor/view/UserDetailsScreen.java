package com.joeracosta.joe.plexpymonitor.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Toast;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.databinding.ScreenUserdetailsBinding;
import com.joeracosta.joe.plexpymonitor.viewmodels.UserDetailsViewModel;
import com.joeracosta.library.Screen;
import com.joeracosta.library.ViewFactory;

/**
 * Created by jacosta on 12/31/16.
 */

public class UserDetailsScreen extends Screen {

    private static final String BUNDLE_IP_KEY = "com.joeracosta.bundle.ip";
    private static final String BUNDLE_PORT_KEY = "com.joeracosta.bundle.port";
    private static final String BUNDLE_AUTH_KEY = "com.joeracosta.bundle.auth";
    private static final String BUNDLE_HTTP_ROOT_KEY = "com.joeracosta.bundle.httproot";

    private Host mHost;
    private UserDetailsViewModel mViewModel;
    private ScreenUserdetailsBinding mBinding;
    private Bundle mSavedBundle;

    public static class Factory extends ViewFactory {

        @Override
        public int getLayoutResource() {
            return R.layout.screen_userdetails;
        }
    }

    public UserDetailsScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScreenAttached() {
        super.onScreenAttached();
        mBinding = DataBindingUtil.bind(this);
        mHost = (Host) getContext();
        mHost.setAppBarTitle(getContext().getString(R.string.userdetails_title));
        mViewModel = new UserDetailsViewModel(this);

        if (mSavedBundle != null){
            mViewModel.setData(mSavedBundle.getString(BUNDLE_IP_KEY),
                    mSavedBundle.getString(BUNDLE_PORT_KEY),
                    mSavedBundle.getString(BUNDLE_AUTH_KEY),
                    mSavedBundle.getString(BUNDLE_HTTP_ROOT_KEY));
            mSavedBundle = null;
        }

        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onScreenDetached() {
        super.onScreenDetached();
        mViewModel.destroy();
    }

    @Override
    protected void onRestoreState(Bundle bundle) {
        mSavedBundle = bundle;
        super.onRestoreState(bundle);
    }

    @Override
    protected Bundle onSaveState(Bundle bundle) {

        bundle.putString(BUNDLE_IP_KEY, mViewModel.getIP());
        bundle.putString(BUNDLE_PORT_KEY, mViewModel.getPort());
        bundle.putString(BUNDLE_AUTH_KEY, mViewModel.getAuth());
        bundle.putString(BUNDLE_HTTP_ROOT_KEY, mViewModel.getHttpRoot());
        return super.onSaveState(bundle);
    }

    public void notifyUser(int s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getViewId() {
        return R.id.userdetails_screen;
    }
}
