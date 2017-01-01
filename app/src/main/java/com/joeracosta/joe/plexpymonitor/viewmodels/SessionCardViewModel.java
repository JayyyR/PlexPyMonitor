package com.joeracosta.joe.plexpymonitor.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jacosta on 12/31/16.
 */

public class SessionCardViewModel extends BaseObservable {

    private CurrentPlexActivity.Response.Data.Session mSession;

    public void setSession(CurrentPlexActivity.Response.Data.Session session){
        mSession = session;
        notifyChange();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        Picasso.with(view.getContext())
                .load(PyAPI.getImagePrefix() + imageUrl)
                .into(view);

        //for some reason the api fails unless I clear the image cache
        PyAPI.getPlexPyApi().deleteImageCache().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {}
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {}
        });
    }

    @Bindable
    public String getThumbUrl() {
        return mSession.getArtUrl();
    }

    @Bindable
    public int getSubtitleVisibility() {
        return (mSession.getGrandParentTitle() != null
                && !mSession.getGrandParentTitle().isEmpty())
                ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public String getGrandParentTitle(){
        return mSession.getGrandParentTitle();
    }

    @Bindable
    public String getTitle(){
        return mSession.getTitle();
    }

    @Bindable
    public String getUser(){
        return mSession.getUser();
    }
}
