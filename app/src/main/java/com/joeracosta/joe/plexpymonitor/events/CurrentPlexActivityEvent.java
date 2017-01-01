package com.joeracosta.joe.plexpymonitor.events;

import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentPlexActivityEvent {

    public CurrentPlexActivity response;

    public CurrentPlexActivityEvent(CurrentPlexActivity response) {
        this.response = response;
    }
}
