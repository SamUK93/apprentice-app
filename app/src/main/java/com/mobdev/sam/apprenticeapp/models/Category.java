package com.mobdev.sam.apprenticeapp.models;

import java.io.Serializable;

/**
 * Model for a skill / interest category
 */

public class Category implements Serializable {

    private Long id;
    private String name;

    // Constructor
    public Category(Long id, String name) {
        setId(id);
        setName(name);
    }


    // Get
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }


    // Set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
