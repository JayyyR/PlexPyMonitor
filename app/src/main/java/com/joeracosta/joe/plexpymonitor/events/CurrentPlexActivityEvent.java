package com.joeracosta.joe.plexpymonitor.events;

import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityEvent {

    public CurrentPlexActivity response;

    public boolean success;

    public CurrentPlexActivityEvent(CurrentPlexActivity response) {
        this.response = response;
        if (response == null){
            success = false;
        } else {
            success = response.isSuccess();
        }
    }

    public CurrentPlexActivityEvent(boolean success) {
        this.success = success;
    }
}
