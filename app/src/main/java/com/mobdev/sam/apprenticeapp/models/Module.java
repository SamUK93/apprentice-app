package com.mobdev.sam.apprenticeapp.models;

import java.util.List;

/**
 * Model for a module
 */

public class Module {

    private Long moduleId;
    private String name;
    // TODO: convert date type
    private List<String> deadlines;

    //TODO: Does this class need extending? Recives note and tasks from other models, so perhaps not?

    // Constructor
    public Module(String name, List<String> deadlines) {
        setName(name);
        setDeadlines(deadlines);
    }


    // Get
    public Long getModuleId() { return this.moduleId; }

    public String getName() { return this.name; }

    public List<String> getDeadlines() { return this.deadlines; }


    // Set
    public Module setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public Module setName(String name) {
        this.name = name;
        return this;
    }

    public Module setDeadlines(List<String> deadlines) {
        this.deadlines = deadlines;
        return this;
    }

    public Module addDeadLines(List<String> deadlines) {
        for (String deadline: deadlines) {
            addDeadline(deadline);
        }
        return this;
    }

    public Module addDeadline(String deadline) {
        if (!this.deadlines.contains(deadline))
            this.deadlines.add(deadline);
        return this;
    }
}
