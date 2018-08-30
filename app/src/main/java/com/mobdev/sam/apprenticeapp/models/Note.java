package com.mobdev.sam.apprenticeapp.models;

import java.util.List;

/**
 * Model for a note
 */

public class Note {

    // Fields
    private Long noteId;
    private String name;
    private String content;
    //TODO Should tags be a string? Return to this when known
    private List<String> tags;
    private List<Long> moduleIds;
    private boolean openToPublic;


    // Constructor
    public Note(String name, String content, List<String> tags, List<Long> moduleIds, boolean openToPublic) {
        setName(name);
        setContent(content);
        setTags(tags);
        setOpenToPublic(openToPublic);
    }


    // Get
    public Long getNoteId() {
        return this.noteId;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<Long> getModuleIds() {
        return this.moduleIds;
    }

    public boolean getOpenToPublic() {
        return this.openToPublic;
    }


    // Set
    public Note setNoteId(Long noteId) {
        this.noteId = noteId;
        return this;
    }

    public Note setName(String name) {
        this.name = name;
        return this;
    }

    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    public Note setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Note setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
        return this;
    }

    public Note addModuleIds(List<Long> moduleIds) {
        for (Long moduleId : moduleIds)
            addModuleId(moduleId);
        return this;
    }

    public Note addModuleId(Long moduleId) {
        if (!this.moduleIds.contains(moduleId))
            moduleIds.add(moduleId);
        return this;
    }

    public Note addTags(List<String> tags) {
        for (String tag : tags) {
            addTag(tag);
        }
        return this;
    }

    public Note addTag(String tag) {
        if (!this.tags.contains(tag))
            this.tags.add(tag);
        return this;
    }

    public Note setOpenToPublic(boolean openToPublic) {
        this.openToPublic = openToPublic;
        return this;
    }
}
