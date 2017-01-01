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

    private static String mBaseUrl;
    private static Retrofit mRetroFit;
    private static String mApiKey;

    public static void initialize(String apikey){
        mApiKey = apikey;
    }

    private static Retrofit getRetroFit(){
        if (mRetroFit == null){

            if (mApiKey == null){
                throw new RuntimeException("Must set apikey");
            }

            OkHttpClient client = new OkHttpClient();

            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("mApiKey", mApiKey).build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            });

            mRetroFit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return mRetroFit;
    }
}
