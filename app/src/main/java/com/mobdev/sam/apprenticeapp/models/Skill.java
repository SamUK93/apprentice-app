package com.mobdev.sam.apprenticeapp.models;

import java.io.Serializable;

/**
 * Created by Sam on 24/07/2018.
 */

public class Skill implements Serializable {

    private String name;
    private Long categoryId;
    private Long profileId;

    // Constructor
    public Skill(String name, Long categoryId, Long profileId) {
        setName(name);
        setCategoryId(categoryId);
        setProfileId(profileId);
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
}
