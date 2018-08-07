package com.mobdev.sam.apprenticeapp.models;

import java.util.List;

/**
 * Model for a module
 */

public class Module {

    private Long moduleId;
    private String name;
    private String description;
    // TODO: convert date type
    private List<Deadline> deadlines;
    private List<Long> moduleParticipants;

    //TODO: Does this class need extending? Recives note and tasks from other models, so perhaps not?

    // Constructor
    public Module(String name, String description, List<Deadline> deadlines, List<Long> moduleParticipants) {
        setName(name);
        setDescription(description);
        setDeadlines(deadlines);
        setModuleParticipants(moduleParticipants);
    }


    // Get
    public Long getModuleId() {
        return this.moduleId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Deadline> getDeadlines() {
        return this.deadlines;
    }

    public List<Long> getModuleParticipants() {
        return this.moduleParticipants;
    }


    // Set
    public Module setModuleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public Module setName(String name) {
        this.name = name;
        return this;
    }

    public Module setDescription(String description) {
        this.description = description;
        return this;
    }

    public Module setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
        return this;
    }

    public Module addDeadLines(List<Deadline> deadlines) {
        for (Deadline deadline : deadlines) {
            addDeadline(deadline);
        }
        return this;
    }

    public Module addDeadline(Deadline deadline) {
        this.deadlines.add(deadline);
        return this;
    }

    public Module setModuleParticipants(List<Long> moduleParticipants) {
        this.moduleParticipants = moduleParticipants;
        return this;
    }

    public Module addModuleParticipants(List<Long> moduleParticipants) {
        for (Long participant : moduleParticipants) {
            addModuleParticipant(participant);
        }
        return this;
    }

    public Module addModuleParticipant(Long participant) {
        if (!this.moduleParticipants.contains(participant)) {
            this.moduleParticipants.add(participant);
        }
        return this;
    }
}
