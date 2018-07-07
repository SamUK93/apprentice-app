package com.mobdev.sam.apprenticeapp.models;

/**
 * Model for a task
 */

public class Task {

    private Long taskId;
    private String name;
    private String date;
    private Long moduleId;

    // TODO: Should tasks have a more detailed view with content, or is name enough?

    // Constructor
    public Task(String name, String date, Long moduleId) {
        setName(name);
        setDate(date);
        setModuleId(moduleId);
    }


    // Get
    public Long getTaskId() { return this.taskId; }

    public String getName() { return this.name; }

    public String getDate() { return this.date; }

    public Long getModuleId() { return this.moduleId; }


    // Set
    public Task setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public Task setDate(String date) {
        this.date = date;
        return this;
    }

    public Task setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }
}
