package com.joeracosta.joe.plexpymonitor.network;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jacosta on 12/31/16.
 */

public interface PlexPyAPI {


    @GET("?cmd=arnold")
    Call<ArnoldResponse> testAPI( );
}
