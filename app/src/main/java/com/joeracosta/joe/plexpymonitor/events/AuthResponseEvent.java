package com.joeracosta.joe.plexpymonitor.events;

/**
 * Created by jacosta on 12/31/16.
 */

public class AuthResponseEvent {

    public boolean authSuccess;
    public String apiKey;
    public String ipAddress;
    public String port;
    public String httpRoot;

    public AuthResponseEvent(boolean authSuccess){
        this.authSuccess = authSuccess;
    }

    public AuthResponseEvent(boolean authSuccess, String apiKey, String ipAddress, String port, String httpRoot){
        this.authSuccess = authSuccess;
        this.ipAddress = ipAddress;
        this.port = port;
        this.apiKey = apiKey;
        this.httpRoot = httpRoot;
    }
}
