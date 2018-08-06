package com.mobdev.sam.apprenticeapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * Model for a profile of a specific person
 */

public class Profile implements Serializable {

    // Fields
    private Long id;
    private String name;
    private String description;
    private List<Skill> skills;
    private List<Skill> interests;

    // TODO: Add more fields? Split into separate class?
    // Capgemini info fields
    private String email;
    //TODO: Make some sort of location type instead of string?
    private String baseLocation;
    private int grade;
    private String jobTitle;
    //TODO: Make date instead of string?
    private String joinDate;
    private boolean isAdmin;

    //Database/storage/associations
    List<Note> associatedNotes;
    List<Event> eventsAttending;
    List<Module> assignedModules;

    // Constructor
    public Profile(String name, String description, List<Skill> skills, List<Skill> interests,
                   String email, String baseLocation, int grade, String jobTitle, String joinDate,
                   List<Note> associatedNotes, List<Event> eventsAttending, List<Module> assignedModules, boolean isAdmin) {

        setName(name);
        setDescription(description);
        setAllSkills(skills);
        setAllInterests(interests);
        setEmail(email);
        setBaseLocation(baseLocation);
        setGrade(grade);
        setJobTitle(jobTitle);
        setJoinDate(joinDate);
        setIsAdmin(isAdmin);
    }


    // Get
    public Long getId() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public List<Skill> getSkills() { return this.skills; }

    public List<Skill> getInterests() { return this.interests; }

    public String getEmail() { return this.email; }

    public String getBaseLocation() { return this.baseLocation; }

    public int getGrade() { return this.grade; }

    public String getJobTitle() { return this.jobTitle; }

    public String getJoinDate() { return this.joinDate; }

    public boolean getIsAdmin() { return this.isAdmin; }


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
    public Profile setAllSkills(List<Skill> skills) {
        for (Skill skill : skills) {
            Log.i("SKILLADD:::","ADDING SKILL - " + skill.getName());
        }
        this.skills = skills;
        return this;
    }

    /**
     * Adds a list of skills, ignoring those that are already in the skills list for this profile
     * @param skills the list of skills to be added
     */
    public Profile addSkills(List<Skill> skills) {
        for (Skill skill : skills) {
            addSkill(skill);
        }
        return this;
    }

    /**
     * Adds a skill if it is not already in the skills list for this profile
     * @param skill the skill to be added
     */
    public Profile addSkill(Skill skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        return this;
    }

    /**
     * Removes a skill specified by the skill name
     * @param skillName a string value representing the skill to be removed
     */
    public Profile removeSkillByName(String skillName) {
        Skill skillToRemove = null;
        for (Skill skill : this.skills) {
            if (skill.getName().equals(skillName)) {
                Log.i("REMOVESKILL", "REMOVING SKILL WITH NAME - " + skillName);
                skillToRemove = skill;
                break;
            }
        }
        this.skills.remove(skillToRemove);
        return this;
    }

    /**
     * Overwrites the current interests with a new set of interests
     * @param interests the interests to be set
     */
    public Profile setAllInterests(List<Skill> interests) {
        this.interests = interests;
        return this;
    }

    /**
     * Adds a list of interests, ignoring those that are already in the interests list for this profile
     * @param interests the list of interests to be added
     */
    public Profile addInterests(List<Skill> interests) {
        for (Skill interest : interests) {
            addInterest(interest);
        }
        return this;
    }

    /**
     * Adds a interest if it is not already in the interests list for this profile
     * @param interest the interest to be added
     */
    public Profile addInterest(Skill interest) {
        if (!this.interests.contains(interest)) {
            this.interests.add(interest);
        }
        return this;
    }

    /**
     * Removes a interest specified by the interest name
     * @param interestName a string value representing the interest to be removed
     */
    public Profile removeInterestByName(String interestName) {
        Skill interestToRemove = null;
        for (Skill interest : this.interests) {
            if (interest.getName().equals(interestName)) {
                Log.i("REMOVEINTEREST", "REMOVING INTEREST WITH NAME - " + interestName);
                interestToRemove = interest;
                break;
            }
        }
        this.interests.remove(interestToRemove);
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

    public Profile setGrade(int grade) {
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


    /**
     * Overwrites the current notes with a new set of notes
     * @param notes the skills to be set
     */
    public Profile setAllNotes(List<Note> notes) {
        this.associatedNotes = notes;
        return this;
    }

    /**
     * Adds a list of notes, ignoring those that are already in the notes list for this profile
     * @param notes the list of skills to be added
     */
    public Profile addNotes(List<Note> notes) {
        for (Note note : notes) {
            addNote(note);
        }
        return this;
    }

    /**
     * Adds a note if it is not already in the notes list for this profile
     * @param note the skill to be added
     */
    public Profile addNote(Note note) {
        if (!this.associatedNotes.contains(note)) {
            this.associatedNotes.add(note);
        }
        return this;
    }


    /**
     * Overwrites the current events with a new set of events
     * @param events the events to be set
     */
    public Profile setAllEvents(List<Event> events) {
        this.eventsAttending = events;
        return this;
    }

    /**
     * Adds a list of events, ignoring those that are already in the events list for this profile
     * @param events the list of events to be added
     */
    public Profile addEvents(List<Event> events) {
        for (Event event : events) {
            addEvent(event);
        }
        return this;
    }

    /**
     * Adds an event if it is not already in the events list for this profile
     * @param event the event to be added
     */
    public Profile addEvent(Event event) {
        if (!this.eventsAttending.contains(event)) {
            this.eventsAttending.add(event);
        }
        return this;
    }


    /**
     * Overwrites the current modules with a new set of modules
     * @param modules the skills to be set
     */
    public Profile setAllModules(List<Module> modules) {
        this.assignedModules = modules;
        return this;
    }

    /**
     * Adds a list of modules, ignoring those that are already in the modules list for this profile
     * @param modules the list of skills to be added
     */
    public Profile addModules(List<Module> modules) {
        for (Module module : modules) {
            addModule(module);
        }
        return this;
    }

    /**
     * Adds a module if it is not already in the modules list for this profile
     * @param module the module to be added
     */
    public Profile addModule(Module module) {
        if (!this.assignedModules.contains(module)) {
            this.assignedModules.add(module);
        }
        return this;
    }

    public Profile setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
}
