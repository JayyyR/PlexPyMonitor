package com.joeracosta.joe.plexpymonitor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacosta on 12/31/16.
 */

public class ArnoldResponse {

    @SerializedName("response")
    private Response response;


    public Response getResponse(){
        return response;
    }

    private class Response{

        @SerializedName("message")
        String message;

        @SerializedName("data")
        String data;

        @SerializedName("result")
        String result;

    }
}
