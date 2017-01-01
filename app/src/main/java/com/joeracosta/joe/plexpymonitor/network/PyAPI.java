package com.joeracosta.joe.plexpymonitor.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jacosta on 12/31/16.
 */

public class PyAPI {

    private static String sBaseUrl;
    private static Retrofit sRetroFit;
    private static String sAuthKey;
    private static String sIPAddress;
    private static String sPort;
    private static PlexPyAPI sPlexPyApi;

    public static void initialize(String apikey, String ip, String port){
        sAuthKey = apikey;
        sIPAddress = ip;
        sPort = port;
    }

    public static void clear(){
        sAuthKey = null;
        sPort = null;
        sIPAddress = null;
        sRetroFit = null;
    }

    public static PlexPyAPI getPlexPyApi(){

        if (sPlexPyApi == null){
            sPlexPyApi = getRetroFit().create(PlexPyAPI.class);
        }

        return sPlexPyApi;
    }

    private static Retrofit getRetroFit(){
        if (sRetroFit == null){

            if (sAuthKey == null || sPort == null || sIPAddress == null){
                throw new RuntimeException("Must set ip, auth, and port");
            }

            sBaseUrl = "http://" + sIPAddress + ":" + sPort +"/api/v2/";

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                            chain -> {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder().addQueryParameter("sAuthKey", sAuthKey).build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }).build();

            sRetroFit = new Retrofit.Builder()
                    .baseUrl(sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return sRetroFit;
    }
}
