package com.joeracosta.joe.plexpymonitor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.library.Screen;
import com.joeracosta.library.ViewFactory;

/**
 * Created by jacosta on 12/31/16.
 */

public class UserDetailsScreen extends Screen {

    private Host mHost;

    public class Factory extends ViewFactory {

        @Override
        public int getLayoutResource() {
            return R.layout.screen_userdetails;
        }
    }

    public UserDetailsScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        mHost = (Host) getParent();

        mHost.setAppBarTitle(getContext().getString(R.string.userdetails_title));
    }

    @Override
    public int getViewId() {
        return R.id.userdetails_screen;
    }
}
