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

    public boolean isSuccess() {
        return response.result.equals("success");
    }

    private class Response{

        @SerializedName("message")
        public String message;

        @SerializedName("data")
        public String data;

        @SerializedName("result")
        public String result;

    }
}
