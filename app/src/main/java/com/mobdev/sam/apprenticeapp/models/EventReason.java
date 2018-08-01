package com.mobdev.sam.apprenticeapp.models;

/**
 * Created by Sam on 30/07/2018.
 */

public class EventReason {

    public Event event;
    public String reason;

    public EventReason(Event event, String reason) {
        this.event = event;
        this.reason = reason;
    }
}
