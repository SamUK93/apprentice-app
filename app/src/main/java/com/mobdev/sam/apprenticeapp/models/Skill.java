package com.mobdev.sam.apprenticeapp.models;

import java.io.Serializable;

/**
 * Model for a skill / interest
 */

public class Skill implements Serializable {

    private String name;
    private Long categoryId;
    private Long profileId;
    private Long eventId;

    // Constructor
    public Skill(String name, Long categoryId, Long profileId, Long eventId) {
        setName(name);
        setCategoryId(categoryId);
        setProfileId(profileId);
        setEventId(eventId);
    }


    // Get

    public String getName() {
        return this.name;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public Long getProfileId() {
        return this.profileId;
    }

    public Long getEventId() {
        return this.eventId;
    }


    // Set

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
