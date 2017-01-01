package com.joeracosta.joe.plexpymonitor.model;

import com.google.gson.annotations.SerializedName;
import com.joeracosta.joe.plexpymonitor.events.CurrentPlexActivityEvent;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jacosta on 12/31/16.
 * POJO for plex sessions
 */

public class CurrentPlexActivity implements Serializable {

    @SerializedName("response")
    Response response;

    public boolean isSuccess(){
        return response.result.equals("success");
    }

    public ArrayList<Response.Data.Session> getSessions(){
        return response.data.sessions;
    }

    public class Response implements Serializable {

        @SerializedName("message")
        String message;

        @SerializedName("data")
        Data data;

        @SerializedName("result")
        String result;

        public class Data implements Serializable {

            @SerializedName("stream_count")
            int streamCount;

            @SerializedName("sessions")
            ArrayList<Session> sessions;

            public class Session implements Serializable {

                @SerializedName("title")
                String title;

                @SerializedName("user")
                String user;

                @SerializedName("art")
                String artUrl;

                @SerializedName("thumb")
                String thumbUrl;

                @SerializedName("user_thumb")
                String userThumbUrl;

                @SerializedName("grandparent_title")
                String grandParentTitle;

                public String getGrandParentTitle() {
                    return grandParentTitle;
                }

                public String getTitle() {
                    return title;
                }

                public String getUser() {
                    return user;
                }

                public String getArtUrl() {
                    return artUrl;
                }

                public String getThumbUrl() {
                    return thumbUrl;
                }

                public String getUserThumbUrl() {
                    return userThumbUrl;
                }
            }

        }
    }


    public static void getCurrentActivity(){
        PyAPI.getPlexPyApi().getCurrentActivity().enqueue(new Callback<CurrentPlexActivity>() {
            @Override
            public void onResponse(Call<CurrentPlexActivity> call, retrofit2.Response<CurrentPlexActivity> response) {
                EventBus.getDefault().post(new CurrentPlexActivityEvent(response.body()));
            }

            @Override
            public void onFailure(Call<CurrentPlexActivity> call, Throwable t) {
                EventBus.getDefault().post(new CurrentPlexActivityEvent(false));
            }
        });
    }
}
