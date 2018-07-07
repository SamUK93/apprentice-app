package com.mobdev.sam.apprenticeapp.models;

/**
 * Model for an event
 */

public class Event {

    private Long eventId;
    private String name;
    private String description;
    //TODO: Convert to date format
    private String date;
    private String goodFor;
    private String prerequisites;


    // Constructor
    public Event(String name, String description, String date, String goodFor, String prerequisites) {
        setName(name);
        setDescription(description);
        setDate(date);
        setGoodFor(goodFor);
        setPrerequisites(prerequisites);
    }


    // Get
    public Long getEventId() { return this.eventId; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public String getDate() { return this.date; }

    public String getGoodFor() { return this.goodFor; }

    public String getPrerequisites() { return this.prerequisites; }


    // Set
    public Event setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public Event setDate(String date) {
        this.date = date;
        return this;
    }

    public Event setGoodFor(String goodFor) {
        this.goodFor = goodFor;
        return this;
    }

    public Event setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
        return this;
    }
}