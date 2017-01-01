package com.joeracosta.joe.plexpymonitor.network;

import com.joeracosta.joe.plexpymonitor.model.ArnoldResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jacosta on 12/31/16.
 */

public interface PlexPyAPI {

    @GET("?cmd=arnold")
    Call<ArnoldResponse> testAPI( );
}
