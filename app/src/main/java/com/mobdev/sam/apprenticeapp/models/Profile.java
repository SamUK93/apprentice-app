package com.mobdev.sam.apprenticeapp.models;

import java.util.List;

/**
 * Model for a profile of a specific person
 */

public class Profile {

    // Fields
    private Long id;
    private String name;
    private String description;
    private List<String> skills;
    private List<String> interests;

    // TODO: Add more fields? Split into separate class?
    // Capgemini info fields
    private String email;
    //TODO: Make some sort of location type instead of string?
    private String baseLocation;
    private String grade;
    private String jobTitle;
    //TODO: Make date instead of string?
    private String joinDate;


    // Get
    public Long getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public List<String> getSkills() { return this.skills; }

    public List<String> getInterests() { return this.interests; }

    public String getEmail() { return this.email; }

    public String getBaseLocation() { return this.baseLocation; }

    public String getGrade() { return this.grade; }

    public String getJobTitle() { return this.jobTitle; }

    public String getJoinDate() { return this.joinDate; }


    // Set
    public Profile setId(Long id) {
        this.id = id;
        return this;
    }

    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    public Profile setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Overwrites the current skills with a new set of skills
     * @param skills the skills to be set
     */
    public Profile setAllSkills(List<String> skills) {
        this.skills = skills;
        return this;
    }

    /**
     * Adds a list of skills, ignoring those that are already in the skills list for this profile
     * @param skills the list of skills to be added
     */
    public Profile addSkills(List<String> skills) {
        for (String skill : skills) {
            addSkill(skill);
        }
        return this;
    }

    /**
     * Adds a skill if it is not already in the skills list for this profile
     * @param skill the skill to be added
     */
    public Profile addSkill(String skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        return this;
    }

    /**
     * Overwrites the current interests with a new set of interests
     * @param interests the interests to be set
     */
    public Profile setAllInterests(List<String> interests) {
        this.interests = interests;
        return this;
    }

    /**
     * Adds a list of interests, ignoring those that are already in the interests list for this profile
     * @param interests the list of interests to be added
     */
    public Profile addInterests(List<String> interests) {
        for (String interest : interests) {
            addInterest(interest);
        }
        return this;
    }

    /**
     * Adds a interest if it is not already in the interests list for this profile
     * @param interest the interest to be added
     */
    public Profile addInterest(String interest) {
        if (!this.interests.contains(interest)) {
            this.interests.add(interest);
        }
        return this;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public Profile setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
        return this;
    }

    public Profile setGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public Profile setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public Profile setJoinDate(String joinDate) {
        this.joinDate = joinDate;
        return this;
    }
}
