package com.joeracosta.joe.plexpymonitor.view;

/**
 * Created by jacosta on 12/31/16.
 */

public interface Host {

    void setAppBarTitle(String title);
    void storeIp(String ip);
    void storePort(String port);
    void storeAuth(String auth);
}
