package com.mobdev.sam.apprenticeapp.models;

/**
 * Created by Sam on 07/08/2018.
 */

public class Deadline {

    private Long deadlineId;
    private String name;
    private String date;
    private Long moduleId;

    // TODO: Should tasks have a more detailed view with content, or is name enough?

    // Constructor
    public Deadline(String name, String date, Long moduleId) {
        setName(name);
        setDate(date);
        setModuleId(moduleId);
    }


    // Get
    public Long getDeadlineId() { return this.deadlineId; }

    public String getName() { return this.name; }

    public String getDate() { return this.date; }

    public Long getModuleId() { return this.moduleId; }


    // Set
    public Deadline setDeadlineId(Long deadlineId) {
        this.deadlineId = deadlineId;
        return this;
    }

    public Deadline setName(String name) {
        this.name = name;
        return this;
    }

    public Deadline setDate(String date) {
        this.date = date;
        return this;
    }

    public Deadline setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }
}
