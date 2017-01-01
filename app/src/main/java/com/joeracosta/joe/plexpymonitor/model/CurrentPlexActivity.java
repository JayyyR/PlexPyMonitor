package com.joeracosta.joe.plexpymonitor.model;

import com.google.gson.annotations.SerializedName;
import com.joeracosta.joe.plexpymonitor.events.CurrentPlexActivityEvent;
import com.joeracosta.joe.plexpymonitor.network.PlexPyAPI;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivity {

    @SerializedName("response")
    Response response;

    public boolean isSuccess(){
        return response.result.equals("success");
    }

    public ArrayList<Response.Data.Session> getSessions(){
        return response.data.sessions;
    }

    public class Response {

        @SerializedName("message")
        String message;

        @SerializedName("data")
        Data data;

        @SerializedName("result")
        String result;

        public class Data {

            @SerializedName("stream_count")
            int streamCount;

            @SerializedName("sessions")
            ArrayList<Session> sessions;

            public class Session {

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
