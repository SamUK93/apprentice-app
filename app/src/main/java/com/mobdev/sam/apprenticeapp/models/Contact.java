package com.mobdev.sam.apprenticeapp.models;

/**
 * Created by Sam on 29/07/2018.
 */

public class Contact {

    private Long profileId;
    private Long contactId;

    // Constructor
    public Contact(Long profileId, Long contactId) {
        this.profileId = profileId;
        this.contactId = contactId;
    }


    // Get
    public Long getProfileId() {
        return this.profileId;
    }

    public Long getContactId() {
        return this.contactId;
    }


    // Set
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
