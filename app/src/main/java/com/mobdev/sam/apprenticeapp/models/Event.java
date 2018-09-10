package com.mobdev.sam.apprenticeapp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Model for an event
 */

public class Event implements Serializable {

    private Long eventId;
    private String name;
    private String description;
    private String location;
    private String date;
    private String goodFor;
    private String prerequisites;
    private List<Skill> relatedSkills;
    private Long creatorId;


    // Constructor
    public Event(String name, String description, String location, String date, String goodFor, String prerequisites, List<Skill> relatedSkills, Long creatorId) {
        setName(name);
        setDescription(description);
        setLocation(location);
        setDate(date);
        setGoodFor(goodFor);
        setPrerequisites(prerequisites);
        setAllRelatedSkills(relatedSkills);
        setCreatorId(creatorId);
    }


    // Get
    public Long getEventId() {
        return this.eventId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDate() {
        return this.date;
    }

    public String getGoodFor() {
        return this.goodFor;
    }

    public String getPrerequisites() {
        return this.prerequisites;
    }

    public List<Skill> getRelatedSkills() {
        return this.relatedSkills;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }


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

    public Event setLocation(String location) {
        this.location = location;
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

    public Event setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    /**
     * Overwrites the current skills with a new set of skills
     *
     * @param skills the skills to be set
     */
    public Event setAllRelatedSkills(List<Skill> skills) {
        this.relatedSkills = skills;
        return this;
    }

    /**
     * Adds a list of skills, ignoring those that are already in the skills list for this profile
     *
     * @param skills the list of skills to be added
     */
    public Event addRelatedSkills(List<Skill> skills) {
        for (Skill skill : skills) {
            addRelatedSkill(skill);
        }
        return this;
    }

    /**
     * Adds a skill if it is not already in the skills list for this profile
     *
     * @param skill the skill to be added
     */
    public Event addRelatedSkill(Skill skill) {
        if (!this.relatedSkills.contains(skill)) {
            this.relatedSkills.add(skill);
        }
        return this;
    }

    /**
     * Removes a skill specified by the skill name
     *
     * @param skillName a string value representing the skill to be removed
     */
    public Event removeRelatedSkillByName(String skillName) {
        Skill skillToRemove = null;
        for (Skill skill : this.relatedSkills) {
            if (skill.getName().equals(skillName)) {
                skillToRemove = skill;
                break;
            }
        }
        this.relatedSkills.remove(skillToRemove);
        return this;
    }
}