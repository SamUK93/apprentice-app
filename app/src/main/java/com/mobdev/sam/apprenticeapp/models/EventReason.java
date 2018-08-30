package com.mobdev.sam.apprenticeapp.models;

/**
 * Model for an EventReason. An event reason is an Event with an additional 'reason' string.
 * Used for suggested events, where the 'reason' string is used to describe why the event is being
 * suggested to the user.
 */

public class EventReason {

    public Event event;
    public String reason;

    public EventReason(Event event, String reason) {
        this.event = event;
        this.reason = reason;
    }
}
