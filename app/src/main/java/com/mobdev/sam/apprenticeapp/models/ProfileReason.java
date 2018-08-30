package com.mobdev.sam.apprenticeapp.models;

/**
 * Model for a ProfileReason. A profile reason is a Profile with an additional 'reason' string.
 * Used for suggested contacts, where the 'reason' string is used to describe why the profile is being
 * suggested to the user.
 */

public class ProfileReason {

    public Profile profile;
    public String reason;

    public ProfileReason(Profile profile, String reason) {
        this.profile = profile;
        this.reason = reason;
    }
}
