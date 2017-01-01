package com.joeracosta.joe.plexpymonitor.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.databinding.ScreenCurrentActivityBinding;
import com.joeracosta.joe.plexpymonitor.viewmodels.CurrentPlexActivityViewModel;
import com.joeracosta.library.Screen;
import com.joeracosta.library.ViewFactory;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityScreen extends Screen {

    private ScreenCurrentActivityBinding mBinding;
    private CurrentPlexActivityViewModel mViewModel;

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
        
        mBinding = DataBindingUtil.bind(this);
        mViewModel = new CurrentPlexActivityViewModel();

        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onScreenDetached() {
        super.onScreenDetached();
        mViewModel.destroy();
    }

    @Override
    public int getViewId() {
        return R.id.current_activity_screen;
    }
}