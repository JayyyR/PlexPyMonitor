package com.joeracosta.joe.plexpymonitor.model;

import com.google.gson.annotations.SerializedName;
import com.joeracosta.joe.plexpymonitor.events.AuthResponseEvent;
import com.joeracosta.joe.plexpymonitor.network.PyAPI;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
/**
 * Created by jacosta on 12/31/16.
 * POJO to test auth key and details
 */

public class AuthenticationModel {

    @SerializedName("response")
    private Response response;


    public Response getResponse(){
        return response;
    }

    public boolean isSuccess() {
        return response.result.equals("success");
    }

    private class Response{

        @SerializedName("message")
        public String message;

        @SerializedName("result")
        public String result;

    }

    public static void testAuthentication(String ipAddress, String port, String authKey, String httpRoot){
        PyAPI.initialize(authKey, ipAddress, port, httpRoot);

        try {
            PyAPI.getPlexPyApi().testAPI().enqueue(new Callback<AuthenticationModel>() {
                @Override
                public void onResponse(Call<AuthenticationModel> call, retrofit2.Response<AuthenticationModel> response) {

                    if (response.body() != null && response.body().isSuccess()) {
                        EventBus.getDefault().post(new AuthResponseEvent(true, authKey, ipAddress, port, httpRoot));
                    } else {
                        EventBus.getDefault().post(new AuthResponseEvent(false));
                        PyAPI.clear();
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationModel> call, Throwable t) {
                    EventBus.getDefault().post(new AuthResponseEvent(false));
                    PyAPI.clear();
                }
            });


        } catch (IllegalArgumentException exception) {

            EventBus.getDefault().post(new AuthResponseEvent(false));
            PyAPI.clear();
        }
    }
}
