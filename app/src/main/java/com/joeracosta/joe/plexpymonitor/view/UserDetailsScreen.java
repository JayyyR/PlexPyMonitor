package com.joeracosta.joe.plexpymonitor.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

    private Host mHost;
    private UserDetailsViewModel mViewModel;
    private ScreenUserdetailsBinding mBinding;


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

        mBinding.setViewModel(mViewModel);
    }


    public void notifyUser(int s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getViewId() {
        return R.id.userdetails_screen;
    }
}
