package com.joeracosta.joe.plexpymonitor.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private static String sHttpRoot;
    private static PlexPyAPI sPlexPyApi;


    public static void initialize(String apikey, String ip, String port, String httpRoot) {
        sAuthKey = apikey;
        sIPAddress = ip;
        sHttpRoot = httpRoot;
        sPort = port;
    }

    public static void clear() {
        sAuthKey = null;
        sPort = null;
        sIPAddress = null;
        sPlexPyApi = null;
        sRetroFit = null;
        sHttpRoot = null;
    }

    public static PlexPyAPI getPlexPyApi() {

        if (sPlexPyApi == null) {
            sPlexPyApi = getRetroFit().create(PlexPyAPI.class);
        }

        return sPlexPyApi;
    }

    private static Retrofit getRetroFit() {
        if (sRetroFit == null) {

            if (sAuthKey == null || sPort == null || sIPAddress == null) {
                throw new RuntimeException("Must set ip, auth, and port");
            }

            sBaseUrl = "http://" + sIPAddress + ":" + sPort;

            if (sHttpRoot != null && !sHttpRoot.isEmpty()) {
                sBaseUrl += "/" + sHttpRoot;
            }

            sBaseUrl += "/api/v2/";


            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(
                            chain -> {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder().addQueryParameter("apikey", sAuthKey).build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            })
                    .build();

            sRetroFit = new Retrofit.Builder()
                    .baseUrl(sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return sRetroFit;
    }


    public static String getImagePrefix() {
        return sBaseUrl +
                "?apikey=" +
                sAuthKey +
                "&cmd=pms_image_proxy" +
                "&width=500&height=280" +
                "&fallback=art" +
                "&img=";
    }
}
