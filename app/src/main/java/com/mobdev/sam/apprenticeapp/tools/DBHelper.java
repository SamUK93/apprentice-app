package com.mobdev.sam.apprenticeapp.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Deadline;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.EventReason;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.ProfileReason;
import com.mobdev.sam.apprenticeapp.models.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Helper class, used for SQLite database creation and SQL execution
 * Also converts retrieved database values into model objects for manipulation / general use in the app.
 */

public class DBHelper extends SQLiteOpenHelper {

    ////////////////////////////////
    // TABLE / FIELD NAME STRINGS //
    ////////////////////////////////

    // DB
    private static final String DB_NAME = "apprenticeAppDB";
    private static final int DB_VERSION = 1;

    // Profiles
    private static final String PROFILE_TABLE = "Profiles";
    private static final String PROFILE_ID = "profileId";
    private static final String PROFILE_NAME = "name";
    private static final String PROFILE_DESC = "description";
    private static final String PROFILE_EMAIL = "email";
    private static final String PROFILE_BASE = "baseLocation";
    private static final String PROFILE_GRADE = "grade";
    private static final String PROFILE_JOB_TITLE = "jobTitle";
    private static final String PROFILE_JOIN_DATE = "joinDate";
    private static final String PROFILE_IS_ADMIN = "isAdmin";

    // Skills
    private static final String SKILLS_TABLE = "Skills";
    private static final String SKILL_NAME = "name";

    // Interests
    private static final String INTERESTS_TABLE = "Interests";
    private static final String INTEREST_NAME = "name";

    // Categories
    private static final String CATEGORIES_TABLE = "categories";
    private static final String CATEGORY_ID = "categoryId";
    private static final String CATEGORY_NAME = "categoryName";


    // Modules
    private static final String MODULES_TABLE = "Modules";
    private static final String MODULE_ID = "moduleId";
    private static final String MODULE_NAME = "name";
    private static final String MODULE_DESCRIPTION = "description";


    // Module deadlines
    private static final String MODULE_DEADLINES_TABLE = "ModuleDeadlines";
    private static final String MODULE_DEADLINE_ID = "deadlineId";
    private static final String MODULE_DEADLINE_NAME = "name";
    private static final String MODULE_DEADLINE_DATE = "date";


    // Module Participants
    private static final String MODULE_PARTICIPANTS_TABLE = "ModuleParticipants";


    // Events
    private static final String EVENTS_TABLE = "Events";
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "name";
    private static final String EVENT_DESCRIPTION = "description";
    private static final String EVENT_LOCATION = "location";
    private static final String EVENT_DATE = "date";
    private static final String EVENT_GOOD_FOR = "goodFor";
    private static final String EVENT_PREREQUISITES = "prerequisites";
    private static final String EVENT_CREATOR = "creatorId";


    // Event Attendees
    private static final String EVENT_ATTENDEES_TABLE = "EventAttendees";


    // Contacts
    private static final String CONTACTS_TABLE = "Contacts";
    private static final String CONTACT_ID = "contactId";


    // Constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        ////////////////////////////////
        //        CREATE TABLES       //
        ////////////////////////////////

        // Create Profiles table
        String sql = "CREATE TABLE " + PROFILE_TABLE +
                "(" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_NAME + " TEXT, " +
                PROFILE_DESC + " TEXT, " + PROFILE_EMAIL + " TEXT, " +
                PROFILE_BASE + " TEXT, " + PROFILE_GRADE + " INTEGER, " +
                PROFILE_JOB_TITLE + " TEXT, " + PROFILE_JOIN_DATE + " TEXT, " + PROFILE_IS_ADMIN + " INTEGER);";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Categories table
        sql = "CREATE TABLE " + CATEGORIES_TABLE +
                "(" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY_NAME + " TEXT);";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Skills table
        sql = "CREATE TABLE " + SKILLS_TABLE +
                "(" + SKILL_NAME + " TEXT, " + CATEGORY_ID + " INTEGER, " + PROFILE_ID +
                " INTEGER, " + EVENT_ID + " INTEGER, FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " +
                CATEGORIES_TABLE + " (" + CATEGORY_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "), FOREIGN KEY (" + EVENT_ID + ") REFERENCES " +
                EVENTS_TABLE + " (" + EVENT_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Interests table
        sql = "CREATE TABLE " + INTERESTS_TABLE +
                "(" + INTEREST_NAME + " TEXT, " + CATEGORY_ID + " INTEGER, " + PROFILE_ID +
                " INTEGER, " + EVENT_ID + " INTEGER, FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " +
                CATEGORIES_TABLE + " (" + CATEGORY_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "), FOREIGN KEY (" + EVENT_ID + ") REFERENCES " +
                EVENTS_TABLE + " (" + EVENT_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Modules table
        sql = "CREATE TABLE " + MODULES_TABLE +
                "(" + MODULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MODULE_NAME + " TEXT, " +
                MODULE_DESCRIPTION + " TEXT);";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Module Deadlines table
        sql = "CREATE TABLE " + MODULE_DEADLINES_TABLE +
                "(" + MODULE_DEADLINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MODULE_DEADLINE_NAME + " TEXT, " +
                MODULE_DEADLINE_DATE + " TEXT, " + MODULE_ID + " INTEGER, FOREIGN KEY (" + MODULE_ID + ") REFERENCES " +
                MODULES_TABLE + " (" + MODULE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Module Participants table
        sql = "CREATE TABLE " + MODULE_PARTICIPANTS_TABLE +
                "(" + MODULE_ID + " INTEGER, " + PROFILE_ID + " INTEGER, FOREIGN KEY (" + MODULE_ID + ") REFERENCES " +
                MODULES_TABLE + " (" + MODULE_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Events table
        sql = "CREATE TABLE " + EVENTS_TABLE +
                "(" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_NAME + " TEXT, " +
                EVENT_DESCRIPTION + " TEXT, " + EVENT_LOCATION + " TEXT, " +
                EVENT_DATE + " TEXT, " + EVENT_GOOD_FOR + " TEXT, " +
                EVENT_PREREQUISITES + " TEXT, " + EVENT_CREATOR + " INTEGER, FOREIGN KEY (" + EVENT_CREATOR + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Event Attendees table
        sql = "CREATE TABLE " + EVENT_ATTENDEES_TABLE +
                "(" + EVENT_ID + " INTEGER, " + PROFILE_ID + " INTEGER, FOREIGN KEY (" + EVENT_ID + ") REFERENCES " +
                EVENTS_TABLE + " (" + EVENT_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Grid Diary table


        // Create Contacts table
        sql = "CREATE TABLE " + CONTACTS_TABLE +
                "(" + PROFILE_ID + " INTEGER, " + CONTACT_ID + " INTEGER, FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // INSERT INITIAL FIELDS AND VALUES IN TABLES
        insertInitialFields(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    ///////////////////
    //    PROFILES   //
    ///////////////////

    /**
     * Converts a profile retrieved from the database into a Profile model object
     *
     * @param cursor the cursor containing the database values
     * @return a Profile object containing the values retrieved from the database
     */
    public Profile cursorToProfile(Cursor cursor) {
        boolean isAdmin = false;

        Long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String desc = cursor.getString(2);
        String email = cursor.getString(3);
        String base = cursor.getString(4);
        int grade = cursor.getInt(5);
        String jobTitle = cursor.getString(6);
        String joinDate = cursor.getString(7);
        if (cursor.getInt(8) == 1) {
            isAdmin = true;
        } else if (cursor.getInt(8) == 0) {
            isAdmin = false;
        }

        Profile profile = new Profile(name, desc, new ArrayList<Skill>(), new ArrayList<Skill>(), email, base, grade, jobTitle, joinDate, null, null, null, isAdmin);
        profile.setId(id);
        return profile;
    }

    /**
     * Inserts a new profile in the database
     *
     * @param profile the profile to enter
     * @return the ID of the newly entered profile
     */
    public long insertProfile(Profile profile) {
        long myProfileId;

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new PROFILE with name " + profile.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(PROFILE_NAME, profile.getName());
        Log.i("DBHELPER", "Name " + profile.getName());

        cv1.put(PROFILE_DESC, profile.getDescription());
        Log.i("DBHELPER", "Description " + profile.getDescription());

        cv1.put(PROFILE_EMAIL, profile.getEmail());
        Log.i("DBHELPER", "Email " + profile.getEmail());

        cv1.put(PROFILE_BASE, profile.getBaseLocation());
        Log.i("DBHELPER", "Base Location " + profile.getBaseLocation());

        cv1.put(PROFILE_GRADE, profile.getGrade());
        Log.i("DBHELPER", "Grade " + profile.getGrade());

        cv1.put(PROFILE_JOB_TITLE, profile.getJobTitle());
        Log.i("DBHELPER", "Job Title " + profile.getJobTitle());

        cv1.put(PROFILE_JOIN_DATE, profile.getJoinDate());
        Log.i("DBHELPER", "Join Date " + profile.getJoinDate());

        cv1.put(PROFILE_IS_ADMIN, profile.getIsAdmin());
        Log.i("DBHELPER", "Is Admin " + profile.getIsAdmin());

        myProfileId = db.insert(PROFILE_TABLE, PROFILE_ID, cv1);
        Log.i("DBHELPER", "Inserted new PROFILE with id " + profile.getId());

        profile.setId(myProfileId);
        return myProfileId;
    }

    /**
     * Updates a profile in the database
     *
     * @param profile the profile to update
     */
    public void updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new PROFILE with name " + profile.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(PROFILE_NAME, profile.getName());
        Log.i("DBHELPER", "Name " + profile.getName());

        cv1.put(PROFILE_DESC, profile.getDescription());
        Log.i("DBHELPER", "Description " + profile.getDescription());

        cv1.put(PROFILE_EMAIL, profile.getEmail());
        Log.i("DBHELPER", "Email " + profile.getEmail());

        cv1.put(PROFILE_BASE, profile.getBaseLocation());
        Log.i("DBHELPER", "Base Location " + profile.getBaseLocation());

        cv1.put(PROFILE_GRADE, profile.getGrade());
        Log.i("DBHELPER", "Grade " + profile.getGrade());

        cv1.put(PROFILE_JOB_TITLE, profile.getJobTitle());
        Log.i("DBHELPER", "Job Title " + profile.getJobTitle());

        cv1.put(PROFILE_JOIN_DATE, profile.getJoinDate());
        Log.i("DBHELPER", "Join Date " + profile.getJoinDate());

        db.update(PROFILE_TABLE, cv1, PROFILE_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
        Log.i("DBHELPER", "Updated PROFILE with id " + profile.getId());

        db.close();
    }


    /**
     * Gets the profile with the specified ID from the database
     *
     * @param id the id of the profile to get
     * @return the profile, or null if no profiles with the specified id were found
     */
    public Profile getProfile(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE + " WHERE " + PROFILE_ID + " = " + id, null);
        if (cursor.getCount() < 1) {
            return null;
        } else {
            cursor.moveToFirst();
            Profile profile = cursorToProfile(cursor);

            // Get all skills and interests, and add to profile
            String sql = "SELECT * FROM " + SKILLS_TABLE + " WHERE " + PROFILE_ID + " = " + id;
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + PROFILE_ID + " = " + id, null);
            Log.i("DBHELPER", sql);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                profile.addSkill(cursorToSkill(cursor2));
                cursor2.moveToNext();
            }

            Cursor cursor3 = db.rawQuery("SELECT * FROM " + INTERESTS_TABLE + " WHERE " + PROFILE_ID + " = " + id, null);
            cursor3.moveToFirst();
            while (!cursor3.isAfterLast()) {
                profile.addInterest(cursorToSkill(cursor3));
                cursor3.moveToNext();
            }

            cursor2.close();
            cursor.close();
            db.close();
            return profile;
        }
    }


    /////////////////
    //    SKILLS   //
    /////////////////

    /**
     * Converts a skill retrieved from the database into a Skill model object
     *
     * @param cursor the cursor containing the database values
     * @return a Skill object containing the values retrieved from the database
     */
    public Skill cursorToSkill(Cursor cursor) {
        String name = cursor.getString(0);
        Long categoryId = cursor.getLong(1);
        Long profileId = cursor.getLong(2);
        Long eventId = cursor.getLong(3);

        Skill skill = new Skill(name, categoryId, profileId, eventId);
        return skill;
    }

    /**
     * Gets a list of all skills for a specified profile from the database
     *
     * @param profileId the profile ID of the profile's skills to retrieve
     * @return a list of the Skill objects for the specified profile
     */
    public List<Skill> getAllSkillsForProfile(Long profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + PROFILE_ID + " = " + profileId, null);
        List<Skill> skills = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            skills.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return skills;
    }

    /**
     * Gets a list of all skills for a specified event from the database
     *
     * @param eventId the event ID of the event's skills to retrieve
     * @return a list of the Skill objects for the specified event
     */
    public List<Skill> getAllSkillsForEvent(Long eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + EVENT_ID + " = " + eventId, null);
        List<Skill> skills = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            skills.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return skills;
    }


    /**
     * Updates a profile's skills in the database
     *
     * @param profileId the profile to update
     */
    public void updateSkillsProfile(Long profileId, List<Skill> skills) {
        Log.i("DBHELPER", "Updating skills for profile with id - " + profileId);

        // Delete all skills for profile
        deleteSkillsProfile(profileId);

        // Add all new new updated skills for profile
        insertSkillsProfile(skills);
    }

    /**
     * Updates a event's skills in the database
     *
     * @param eventId the event to update
     */
    public void updateSkillsEvent(Long eventId, List<Skill> skills) {
        Log.i("DBHELPER", "Updating skills for event with id - " + eventId);

        // Delete all skills for profile
        deleteSkillsEvent(eventId);

        // Add all new new updated skills for profile
        insertSkillsEvent(skills);
    }

    /**
     * Adds a list of skills for a profile to the database
     *
     * @param skills the skills to add
     */
    public void insertSkillsProfile(List<Skill> skills) {

        for (Skill skill : skills) {
            Log.i("DBHELPER", "Adding skill  - " + skill.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(SKILL_NAME, skill.getName());
            cv1.put(CATEGORY_ID, skill.getCategoryId());
            cv1.put(PROFILE_ID, skill.getProfileId());

            db.insert(SKILLS_TABLE, EVENT_ID, cv1);
        }
    }

    /**
     * Adds a list of skills for an event to the database
     *
     * @param skills the skills to add
     */
    public void insertSkillsEvent(List<Skill> skills) {

        for (Skill skill : skills) {
            Log.i("DBHELPER", "Adding skill  - " + skill.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(SKILL_NAME, skill.getName());
            cv1.put(CATEGORY_ID, skill.getCategoryId());
            cv1.put(EVENT_ID, skill.getEventId());

            db.insert(SKILLS_TABLE, PROFILE_ID, cv1);
        }
    }

    /**
     * Deletes all skills for a specified profile from the database
     *
     * @param profileId the ID of the profile's skills to be deleted
     */
    public void deleteSkillsProfile(Long profileId) {
        Log.i("DBHELPER", "Deleting skills for profile with id - " + profileId);

        // Delete all skills for profile
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(SKILLS_TABLE, PROFILE_ID + " = ?",
                new String[]{String.valueOf(profileId)});
        db.close();
    }

    /**
     * Deletes all skills for a specified event from the database
     *
     * @param eventId the ID of the event's skills to be deleted
     */
    public void deleteSkillsEvent(Long eventId) {
        Log.i("DBHELPER", "Deleting skills for event with id - " + eventId);

        // Delete all skills for profile
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(SKILLS_TABLE, EVENT_ID + " = ?",
                new String[]{String.valueOf(eventId)});
        db.close();
    }

    /**
     * Gets a list of all of the unique skills (no duplicates) from the database
     *
     * @return the list of Skill objects
     */
    public List<Skill> getAllSkillsUnique() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE, null);
        List<Skill> skills = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            skills.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        List<String> skillNames = new ArrayList<>();
        List<Skill> uniqueSkills = new ArrayList<>();
        for (Skill skill : skills) {
            // For each skill retrieved from the database
            if (!skillNames.contains(skill.getName())) {
                // If the skill name has not been seen before, add it to the list of skills to be returned
                skillNames.add(skill.getName());
                uniqueSkills.add(skill);
            }
        }
        return uniqueSkills;
    }

    /**
     * Gets a list of all of the unique skills (no duplicates) in a specified category from the database
     *
     * @param categoryId the category of skills to be retrieved
     * @return the list of Skill objects
     */
    public List<Skill> getAllSkillsUniqueInCategory(Long categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + CATEGORY_ID
                + " = " + categoryId, null);
        List<Skill> skills = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            skills.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        List<String> skillNames = new ArrayList<>();
        List<Skill> uniqueSkills = new ArrayList<>();
        for (Skill skill : skills) {
            // For each skill retrieved from the database
            if (!skillNames.contains(skill.getName())) {
                // If the skill name has not been seen before, add it to the list of skills to be returned
                skillNames.add(skill.getName());
                uniqueSkills.add(skill);
            }
        }
        return uniqueSkills;
    }

    /**
     * Gets a list of all profiles from the database
     *
     * @return the list of Profile objects
     */
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();

        // Get all Profile IDs that have that skill
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE, null);
        cursor.moveToFirst();

        // Add all profile ids to list
        while (!cursor.isAfterLast()) {
            profiles.add(cursorToProfile(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return profiles;
    }

    /**
     * Gets a list of profiles that have any of a list of specified skills, each with a 'reason' string
     * containing an explanation of why the profile has been matched (what skills or interests it matched on)
     *
     * @param skills the list of skills to be checked on
     * @return the list of ProfileReason objects
     */
    public List<ProfileReason> getAllProfilesSameSkills(List<Skill> skills) {

        List<ProfileReason> matchingProfiles = new ArrayList<>();
        List<Long> profileIdsTotal = new ArrayList<>();

        // For each of the skills specified
        for (Skill skill : skills) {

            List<Long> profileIds = new ArrayList<>();

            // Get all Profile IDs that have that skill
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + SKILL_NAME
                    + " = '" + skill.getName() + "' AND " + PROFILE_ID + " IS NOT NULL;", null);
            cursor.moveToFirst();

            // Add all profile ids to list
            while (!cursor.isAfterLast()) {
                profileIds.add(cursor.getLong(2));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();

            // For each matching profile id
            for (Long profileId : profileIds) {
                if (!profileIdsTotal.contains(profileId)) {
                    // If profile not already matched (with a previous skill) create a new 'profile reason' and add to list
                    matchingProfiles.add(new ProfileReason(getProfile(profileId), "Because has skill(s) - " + skill.getName()));
                    profileIdsTotal.add(profileId);
                } else {
                    // Profile already matched on a previous skill, so add to existing 'profile reason' with new skill
                    for (ProfileReason profileReason : matchingProfiles) {
                        if (profileReason.profile.getId().equals(profileId)) {
                            profileReason.reason += ", " + skill.getName();
                            break;
                        }
                    }
                }
            }
        }

        return matchingProfiles;
    }


    ////////////////////
    //    INTERESTS   //
    ////////////////////

    /**
     * Gets all interests for a specified profile from the database
     *
     * @param profileId the profile ID of the profile's interests to be retrieved
     * @return the list of Skill objects
     */
    public List<Skill> getAllInterestsForProfile(Long profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERESTS_TABLE + " WHERE " + PROFILE_ID + " = " + profileId, null);
        List<Skill> skills = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            skills.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return skills;
    }

    /**
     * Updates a profile's skills in the database
     *
     * @param profileId the profile to update
     */
    public void updateInterests(Long profileId, List<Skill> skills) {
        Log.i("DBHELPER", "Updating interests for profile with id - " + profileId);

        // Delete all interests for profile
        deleteInterests(profileId);

        // Add all new updated interests for profile
        insertInterests(profileId, skills);
    }

    /**
     * Adds a list of interests for the specified profile to the database
     *
     * @param profileId the ID of the profile's interests being added to
     * @param interests the list of interests to add
     */
    public void insertInterests(Long profileId, List<Skill> interests) {

        Log.i("DBHELPER", "Inserting interests for profile with id - " + profileId);

        for (Skill interest : interests) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(INTEREST_NAME, interest.getName());
            cv1.put(CATEGORY_ID, interest.getCategoryId());
            cv1.put(PROFILE_ID, interest.getProfileId());

            db.insert(INTERESTS_TABLE, null, cv1);
        }
    }

    /**
     * Deletes all interests for a specified profile from the database
     *
     * @param profileId the ID of the profile
     */
    public void deleteInterests(Long profileId) {
        Log.i("DBHELPER", "Deleting interests for profile with id - " + profileId);

        // Delete all interests for profile
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(INTERESTS_TABLE, PROFILE_ID + " = ?",
                new String[]{String.valueOf(profileId)});
        db.close();
    }

    /**
     * Gets a list of all of the unique interests (no duplicates) from the database
     *
     * @return the list of Skill objects
     */
    public List<Skill> getAllInterestsUnique() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERESTS_TABLE, null);
        List<Skill> interests = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            interests.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        List<String> interestNames = new ArrayList<>();
        List<Skill> uniqueInterests = new ArrayList<>();
        for (Skill interest : interests) {
            if (!interestNames.contains(interest.getName())) {
                interestNames.add(interest.getName());
                uniqueInterests.add(interest);
            }
        }
        return uniqueInterests;
    }

    /**
     * Gets a list of all of the unique interests (no duplicates) in a specified category from the database
     *
     * @return the list of Skill objects
     */
    public List<Skill> getAllInterestsUniqueInCategory(Long categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + INTERESTS_TABLE + " WHERE " + CATEGORY_ID +
                " = " + categoryId, null);
        List<Skill> interests = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            interests.add(cursorToSkill(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        List<String> interestNames = new ArrayList<>();
        List<Skill> uniqueInterests = new ArrayList<>();
        for (Skill interest : interests) {
            if (!interestNames.contains(interest.getName())) {
                interestNames.add(interest.getName());
                uniqueInterests.add(interest);
            }
        }
        return uniqueInterests;
    }

    /**
     * Gets a list of profiles that have any of a list of specified interests, each with a 'reason' string
     * containing an explanation of why the profile has been matched (what interests it matched on)
     *
     * @param interests the list of interests to be checked on
     * @return the list of ProfileReason objects
     */
    public List<ProfileReason> getAllProfilesSameInterests(List<Skill> interests) {

        List<ProfileReason> matchingProfiles = new ArrayList<>();
        List<Long> profileIdsTotal = new ArrayList<>();

        for (Skill interest : interests) {
            // For each of the interests specified

            List<Long> profileIds = new ArrayList<>();

            // Get all Profile IDs that have that interest
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + INTERESTS_TABLE + " WHERE " + INTEREST_NAME
                    + " = '" + interest.getName() + "';", null);
            cursor.moveToFirst();

            // Add all profile ids to list
            while (!cursor.isAfterLast()) {
                profileIds.add(cursor.getLong(2));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();

            for (Long profileId : profileIds) {
                // For each matching profile id
                if (!profileIdsTotal.contains(profileId)) {
                    // If profile not already matched (with a previous interest) create a new 'profile reason' and add to list
                    matchingProfiles.add(new ProfileReason(getProfile(profileId), "Because has interest(s) - " + interest.getName()));
                    profileIdsTotal.add(profileId);
                } else {
                    // Profile already matched on a previous interest, so add to existing 'profile reason' with new interest
                    for (ProfileReason profileReason : matchingProfiles) {
                        if (profileReason.profile.getId().equals(profileId)) {
                            profileReason.reason += ", " + interest.getName();
                            break;
                        }
                    }
                }
            }
        }

        return matchingProfiles;
    }

    /**
     * Gets a list of profiles that have any of a list of specified skills or interests, each with a 'reason' string
     * containing an explanation of why the profile has been matched (what skills or interests it matched on)
     *
     * @param skills    the list of skills to be checked on
     * @param interests the list of interests to be checked on
     * @return the list of ProfileReason objects
     */
    public List<ProfileReason> getAllProfilesAllCriteria(List<Skill> skills, List<Skill> interests) {
        // Combine to get a total match (all skills match with all interests, cross matching)
        skills.addAll(interests);

        // Get all profiles with matching skills and interests
        List<ProfileReason> matchingSkills = getAllProfilesSameSkills(skills);
        List<ProfileReason> matchingInterests = getAllProfilesSameInterests(skills);

        List<ProfileReason> newProfilesToAdd = new ArrayList<>();

        for (ProfileReason interestMatch : matchingInterests) {
            // For each matching interest
            boolean newProfile = true;
            for (ProfileReason skillMatch : matchingSkills) {
                // Check all matching skills, if same profile, append interests
                if (skillMatch.profile.getId().equals(interestMatch.profile.getId())) {
                    skillMatch.reason += "\nand\n" + interestMatch.reason;
                    newProfile = false;
                    break;
                }
            }
            if (newProfile)
                // New profile with no previous matches, add to list of new profile matches
                newProfilesToAdd.add(interestMatch);
        }
        // Add all new profile matches
        matchingSkills.addAll(newProfilesToAdd);
        return matchingSkills;
    }

    /**
     * Gets a list of unique skills from both skills and interests tables
     * For example, if the same skill is found in both the Skills and Interests tables,
     * it will only be returned once.
     *
     * @return a list of skills and interests
     */
    public List<Skill> getAllSkillsInterestsUnique() {
        List<Skill> skills = getAllSkillsUnique();
        List<Skill> interests = getAllInterestsUnique();
        List<String> skillNames = new ArrayList<>();

        for (Skill skill : skills) {
            skillNames.add(skill.getName());
        }

        for (Skill interest : interests) {
            if (!skillNames.contains(interest.getName())) {
                skills.add(interest);
            }
        }
        return skills;
    }

    /**
     * Gets a list of unique skills from both skills and interests tables in a specified category
     * For example, if the same skill is found in both the Skills and Interests tables,
     * it will only be returned once.
     *
     * @return a list of skills and interests
     */
    public List<Skill> getAllSkillsInterestsUniqueInCategory(Long categoryId) {
        List<Skill> skills = getAllSkillsUniqueInCategory(categoryId);
        List<Skill> interests = getAllInterestsUniqueInCategory(categoryId);
        List<String> skillNames = new ArrayList<>();

        for (Skill skill : skills) {
            skillNames.add(skill.getName());
        }

        for (Skill interest : interests) {
            if (!skillNames.contains(interest.getName())) {
                skills.add(interest);
            }
        }
        return skills;
    }

    /**
     * Gets a list of all of the skills and interests for a specified profile
     *
     * @param profileId the ID of the profile
     * @return a list of skills and interests
     */
    public List<Skill> getAllSkillsAndInterestsForProfile(Long profileId) {
        List<Skill> skills = getAllSkillsForProfile(profileId);
        List<Skill> interests = getAllInterestsForProfile(profileId);

        for (Skill interest : interests) {
            skills.add(interest);
        }
        return skills;
    }


    /////////////////////
    //    CATEGORIES   //
    /////////////////////

    /**
     * Converts the values retrieved from the database into a Category model object
     *
     * @param cursor the values retrieved from the database
     * @return the Category object
     */
    public Category cursorToCategory(Cursor cursor) {
        Long id = cursor.getLong(0);
        String name = cursor.getString(1);

        Category category = new Category(id, name);
        return category;
    }

    /**
     * Gets a list of all categories from the database
     *
     * @return a list of Category objects
     */
    public List<Category> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CATEGORIES_TABLE, null);
        List<Category> categories = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            categories.add(cursorToCategory(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return categories;
    }


    ///////////////////
    //    CONTACTS   //
    ///////////////////

    /**
     * Converts the values retrieved from the database into a Contact model object
     *
     * @param cursor the values retrieved from the database
     * @return the Contact object
     */
    public Contact cursorToContact(Cursor cursor) {
        Long profileId = cursor.getLong(0);
        Long contactId = cursor.getLong(1);

        Contact contact = new Contact(profileId, contactId);
        return contact;
    }

    /**
     * Gets a list of all contacts from the database
     *
     * @return a list of Contact objects
     */
    public List<Contact> getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE, null);
        List<Contact> contacts = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            contacts.add(cursorToContact(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return contacts;
    }

    /**
     * Gets a list of all contacts for a specified profile
     *
     * @param profileId the ID of the profile
     * @return a list of Contact objects
     */
    public List<Contact> getAllContactsForProfile(Long profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE + " WHERE "
                + PROFILE_ID + " = " + profileId, null);
        List<Contact> contacts = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            contacts.add(cursorToContact(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return contacts;
    }

    /**
     * Inserts a contact into the database
     *
     * @param profileId the profile ID of the first user (the logged in user)
     * @param contactId the profile ID of the second user that the logged in user is adding to their contacts
     */
    public void insertContact(Long profileId, Long contactId) {
        Log.i("DBHELPER", "Adding new contact profile with id - " + profileId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();

        cv1.put(PROFILE_ID, profileId);
        cv1.put(CONTACT_ID, contactId);

        db.insert(CONTACTS_TABLE, null, cv1);
    }


    /////////////////
    //    EVENTS   //
    /////////////////

    /**
     * Gets the event with the specified ID from the database
     *
     * @param id the id of the event to get
     * @return the event, or null if no profiles with the specified id were found
     */
    public Event getEvent(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql1 = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + EVENT_ID + " = " + id;
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENTS_TABLE + " WHERE " + EVENT_ID + " = " + id, null);
        Log.i("DBHELPER", sql1);
        if (cursor.getCount() < 1) {
            return null;
        } else {
            cursor.moveToFirst();
            Event event = cursorToEvent(cursor);

            // Get all related skills, and add to event
            String sql = "SELECT * FROM " + SKILLS_TABLE + " WHERE " + EVENT_ID + " = " + id;
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + EVENT_ID + " = " + id, null);
            Log.i("DBHELPER", sql);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                event.addRelatedSkill(cursorToSkill(cursor2));
                cursor2.moveToNext();
            }

            cursor2.close();
            cursor.close();
            db.close();
            return event;
        }
    }


    /**
     * Deletes a contact from the database
     *
     * @param profileId the profile ID of the first user (the logged in user)
     * @param contactId the profile ID of the second user that the logged in user is removing from their contacts
     */
    public void deleteContact(Long profileId, Long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(CONTACTS_TABLE, PROFILE_ID + " = ? AND " + CONTACT_ID + " = ?",
                new String[]{String.valueOf(profileId), String.valueOf(contactId)});
        db.close();
    }


    /**
     * Inserts a new event in the database
     *
     * @param event the event to enter
     * @return the ID of the newly entered event
     */
    public long insertEvent(Event event) {
        long eventId;

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new EVENT with name " + event.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(EVENT_NAME, event.getName());
        Log.i("DBHELPER", "Name " + event.getName());

        cv1.put(EVENT_DESCRIPTION, event.getDescription());
        Log.i("DBHELPER", "Description " + event.getDescription());

        cv1.put(EVENT_LOCATION, event.getLocation());
        Log.i("DBHELPER", "Location " + event.getLocation());

        cv1.put(EVENT_DATE, event.getDate());
        Log.i("DBHELPER", "Date " + event.getDate());

        cv1.put(EVENT_GOOD_FOR, event.getGoodFor());
        Log.i("DBHELPER", "Good for " + event.getGoodFor());

        cv1.put(EVENT_PREREQUISITES, event.getPrerequisites());
        Log.i("DBHELPER", "Prerequisites " + event.getPrerequisites());

        cv1.put(EVENT_CREATOR, event.getCreatorId());
        Log.i("DBHELPER", "Creator ID " + event.getCreatorId());

        eventId = db.insert(EVENTS_TABLE, EVENT_ID, cv1);
        Log.i("DBHELPER", "Inserted new EVENT with id " + event.getEventId());

        event.setEventId(eventId);
        return eventId;
    }

    /**
     * Updates an event in the database
     *
     * @param event the event to update
     * @return the ID of the newly entered event
     */
    public void updateEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new EVENT with name " + event.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(EVENT_NAME, event.getName());
        Log.i("DBHELPER", "Name " + event.getName());

        cv1.put(EVENT_DESCRIPTION, event.getDescription());
        Log.i("DBHELPER", "Description " + event.getDescription());

        cv1.put(EVENT_LOCATION, event.getLocation());
        Log.i("DBHELPER", "Location " + event.getLocation());

        cv1.put(EVENT_DATE, event.getDate());
        Log.i("DBHELPER", "Date " + event.getDate());

        cv1.put(EVENT_GOOD_FOR, event.getGoodFor());
        Log.i("DBHELPER", "Good for " + event.getGoodFor());

        cv1.put(EVENT_PREREQUISITES, event.getPrerequisites());
        Log.i("DBHELPER", "Prerequisites " + event.getPrerequisites());

        db.update(EVENTS_TABLE, cv1, EVENT_ID + " = ?",
                new String[]{String.valueOf(event.getEventId())});
        Log.i("DBHELPER", "Updated PROFILE with id " + event.getEventId());
    }

    /**
     * Deletes an event from the database
     *
     * @param eventId the ID of the event
     */
    public void deleteEvent(Long eventId) {
        Log.i("DBHELPER", "Deleting event with id - " + eventId);



        // Delete all skills for event
        deleteSkillsEvent(eventId);

        // Delete all attendees for event
        deleteAllEventAttendees(eventId);

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete event
        db.delete(EVENTS_TABLE, EVENT_ID + " = ?",
                new String[]{String.valueOf(eventId)});
        db.close();
    }


    /**
     * Gets a list of events that have any of a list of specified skills or interests, each with a 'reason' string
     * containing an explanation of why the event has been matched (what skills it matched on)
     *
     * @param skills    the list of skills to be checked on
     * @param interests the list of interests to be checked on
     * @return the list of EventReason objects
     */
    public List<EventReason> getAllEventsAllCriteria(List<Skill> skills, List<Skill> interests) {
        // Get all events with matching skills and interests
        List<EventReason> matchingSkills = getAllEventsSameSkills(skills);
        List<EventReason> matchingInterests = getAllEventsSameSkills(interests);

        Log.i("EVENTMATCH::", "MATCHED " + matchingSkills.size() + " events for skills!");
        Log.i("EVENTMATCH::", "MATCHED " + matchingInterests.size() + " events for interests!");

        List<EventReason> newEventsToAdd = new ArrayList<>();

        for (EventReason interestMatch : matchingInterests) {
            // For each matching interest
            boolean newProfile = true;
            for (EventReason skillMatch : matchingSkills) {
                // Check all matching skills, if same profile, append interests
                if (skillMatch.event.getEventId().equals(interestMatch.event.getEventId())) {
                    Log.i("EVENTMATCH::", "Event already has match, appending");
                    skillMatch.reason += "\nand\n" + interestMatch.reason;
                    newProfile = false;
                    break;
                }
            }
            if (newProfile)
                // New profile with no previous matches, add to list of new profile matches
                newEventsToAdd.add(interestMatch);
        }
        // Add all new profile matches
        matchingSkills.addAll(newEventsToAdd);
        return matchingSkills;
    }

    /**
     * Gets a list of events that have any of a list of specified skills, each with a 'reason' string
     * containing an explanation of why the event has been matched (what skills or interests it matched on)
     *
     * @param skills the list of skills to be checked on
     * @return the list of EventReason objects
     */
    public List<EventReason> getAllEventsSameSkills(List<Skill> skills) {

        List<EventReason> matchingEvents = new ArrayList<>();
        List<Long> eventIdsTotal = new ArrayList<>();

        // For each of the skills specified
        for (Skill skill : skills) {

            List<Long> eventIds = new ArrayList<>();

            // Get all Event IDs that have that skill
            Log.i("EVENTDEBUG:::", "SELECT * FROM " + SKILLS_TABLE + " WHERE " + SKILL_NAME
                    + " = '" + skill.getName() + "' AND " + EVENT_ID + " IS NOT NULL;");
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + SKILL_NAME
                    + " = '" + skill.getName() + "' AND " + EVENT_ID + " IS NOT NULL;", null);
            cursor.moveToFirst();

            // Add all event ids to list
            while (!cursor.isAfterLast()) {
                eventIds.add(cursor.getLong(3));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();

            // For each matching event id
            for (Long eventId : eventIds) {
                if (!eventIdsTotal.contains(eventId)) {
                    // If profile not already matched (with a previous skill) create a new 'profile reason' and add to list
                    matchingEvents.add(new EventReason(getEvent(eventId), "Because good for people with your skill(s) or interest(s) - " + skill.getName()));
                    eventIdsTotal.add(eventId);
                } else {
                    // Event already matched on a previous skill, so add to existing 'event reason' with new skill
                    for (EventReason eventReason : matchingEvents) {
                        if (eventReason.event.getEventId().equals(eventId)) {
                            eventReason.reason += ", " + skill.getName();
                            break;
                        }
                    }
                }
            }
        }

        return matchingEvents;
    }

    /**
     * Gets a list of events that were created by the specified profile
     *
     * @param profileId the ID of the profile
     * @return a list of Event objects
     */
    public List<Event> getAllEventsCreatedByUser(Long profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENTS_TABLE + " WHERE "
                + EVENT_CREATOR + " = " + profileId, null);
        List<Event> events = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            events.add(cursorToEvent(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return events;
    }


    //////////////////////////
    //    EVENT ATTENDEES   //
    //////////////////////////

    /**
     * Inserts a new event attendee in the database
     *
     * @param eventId   the ID of the event
     * @param profileId the ID of the profile
     */
    public void insertEventAttendee(Long eventId, Long profileId) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new EVENT ATTENDEE with (event id  " + eventId + ", profile id " + profileId);
        ContentValues cv1 = new ContentValues();

        cv1.put(EVENT_ID, eventId);

        cv1.put(PROFILE_ID, profileId);

        db.insert(EVENT_ATTENDEES_TABLE, null, cv1);
        Log.i("DBHELPER", "Inserted new EVENT ATTENDEE with (event id  " + eventId + ", profile id " + profileId);
    }

    /**
     * Deletes an event attendee from the database
     *
     * @param eventId   the ID of the event that the profile is attending
     * @param profileId the ID of the profile
     */
    public void deleteEventAttendee(Long eventId, Long profileId) {
        Log.i("DBHELPER", "Deleting event attendee with id - " + profileId);

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete event attendee
        db.delete(EVENT_ATTENDEES_TABLE, EVENT_ID + " = ? AND " + PROFILE_ID + " = ?",
                new String[]{String.valueOf(eventId), String.valueOf(profileId)});
        db.close();
    }

    /**
     * Deletes all event attendees for a specified event
     *
     * @param eventId   the ID of the event
     */
    public void deleteAllEventAttendees(Long eventId) {
        Log.i("DBHELPER", "Deleting attendees for event with id - " + eventId);

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete event attendees
        db.delete(EVENT_ATTENDEES_TABLE, EVENT_ID + " = ?",
                new String[]{String.valueOf(eventId)});
        db.close();
    }

    /**
     * Gets a list of all of the events that a specified profile is attending
     *
     * @param profileId the ID of the profile
     * @return a list of Event objects
     */
    public List<Event> getAllEventsProfileAttending(Long profileId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENT_ATTENDEES_TABLE + " WHERE "
                + PROFILE_ID + " = " + profileId, null);
        List<Long> eventIds = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            eventIds.add(cursor.getLong(0));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        Log.i("EVENTMATCH::", "USER IS ATTENDING " + eventIds.size() + " events!");

        List<Event> attendingEvents = new ArrayList<>();
        for (Long eventId : eventIds) {
            attendingEvents.add(getEvent(eventId));
        }
        return attendingEvents;
    }


    /**
     * Gets the list of profiles attending a specified event
     *
     * @param eventId the ID of the event
     * @return a list of Profile objects
     */
    public List<Profile> getEventAttendees(Long eventId) {
        List<Profile> profilesAttending = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EVENT_ATTENDEES_TABLE + " WHERE "
                + EVENT_ID + " = " + eventId, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            profilesAttending.add(getProfile(cursor.getLong(1)));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return profilesAttending;
    }

    /**
     * Converts the values retrieved from the database to an Event model object
     *
     * @param cursor the values retrieved from the database
     * @return an Event object
     */
    public Event cursorToEvent(Cursor cursor) {
        Long eventId = cursor.getLong(0);
        String name = cursor.getString(1);
        String description = cursor.getString(2);
        String location = cursor.getString(3);
        String date = cursor.getString(4);
        String goodFor = cursor.getString(5);
        String prerequisites = cursor.getString(6);
        Long creatorId = cursor.getLong(7);

        Event event = new Event(name, description, location, date, goodFor, prerequisites, new ArrayList<Skill>(), creatorId);
        event.setEventId(eventId);
        return event;
    }


    ////////////////
    //   MODULES  //
    ////////////////

    /**
     * Inserts a new module in the database
     *
     * @param module the module to enter
     * @return the ID of the newly entered module
     */
    public long insertModule(Module module) {
        long moduleId;

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new MODULE with name " + module.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(MODULE_NAME, module.getName());
        Log.i("DBHELPER", "Name " + module.getName());

        cv1.put(MODULE_DESCRIPTION, module.getDescription());
        Log.i("DBHELPER", "Description " + module.getDescription());

        moduleId = db.insert(MODULES_TABLE, MODULE_ID, cv1);

        module.setModuleId(moduleId);
        Log.i("DBHELPER", "Inserted new MODULE with id " + module.getModuleId());

        return moduleId;
    }

    /**
     * Updates an event in the database
     *
     * @param module the module to update
     * @return the ID of the newly updated module
     */
    public void updateModule(Module module) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Inserting new module with name " + module.getName());
        ContentValues cv1 = new ContentValues();

        cv1.put(MODULE_NAME, module.getName());
        Log.i("DBHELPER", "Name " + module.getName());

        cv1.put(MODULE_DESCRIPTION, module.getDescription());
        Log.i("DBHELPER", "Description " + module.getDescription());

        db.update(MODULES_TABLE, cv1, MODULE_ID + " = ?",
                new String[]{String.valueOf(module.getModuleId())});
        Log.i("DBHELPER", "Updated MODULE with id " + module.getModuleId());
    }

    /**
     * Gets the event with the specified ID from the database
     *
     * @param id the id of the event to get
     * @return the event, or null if no profiles with the specified id were found
     */
    public Module getModule(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql1 = "SELECT * FROM " + MODULES_TABLE + " WHERE " + MODULE_ID + " = " + id;
        Cursor cursor = db.rawQuery("SELECT * FROM " + MODULES_TABLE + " WHERE " + MODULE_ID + " = " + id, null);
        Log.i("DBHELPER", sql1);
        if (cursor.getCount() < 1) {
            return null;
        } else {
            cursor.moveToFirst();
            Module module = cursorToModule(cursor);

            // Get all deadlines, and add to module
            String sql = "SELECT * FROM " + MODULE_DEADLINES_TABLE + " WHERE " + MODULE_ID + " = " + id;
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + MODULE_DEADLINES_TABLE + " WHERE " + MODULE_ID + " = " + id, null);
            Log.i("DBHELPER", sql);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                module.addDeadline(cursorToDeadline(cursor2));
                cursor2.moveToNext();
            }

            cursor2.close();

            // Get all participants, and add to module
            String sql2 = "SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + MODULE_ID + " = " + id;
            Cursor cursor3 = db.rawQuery("SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + MODULE_ID + " = " + id, null);
            Log.i("DBHELPER", sql2);
            cursor3.moveToFirst();
            while (!cursor3.isAfterLast()) {
                module.addModuleParticipant(cursor3.getLong(1));
                cursor3.moveToNext();
            }

            cursor3.close();

            cursor.close();
            db.close();
            return module;
        }
    }

    /**
     * Gets all modules from the database
     *
     * @return a list of Module objects
     */
    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql1 = "SELECT * FROM " + MODULES_TABLE;
        Cursor cursor = db.rawQuery("SELECT * FROM " + MODULES_TABLE, null);
        Log.i("DBHELPER", sql1);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            modules.add(getModule(cursor.getLong(0)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return modules;

    }

    /**
     * Gets a list of modules that a specified profile is participating in
     *
     * @param profile the profile
     * @return a list of Module objects
     */
    public List<Module> getAllModulesForProfile(Profile profile) {
        List<Module> modules = new ArrayList<>();
        List<Long> moduleIds = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql2 = "SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + PROFILE_ID + " = " + profile.getId();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + PROFILE_ID + " = " + profile.getId(), null);
        Log.i("DBHELPER", sql2);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            modules.add(getModule(cursor.getLong(0)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return modules;
    }

    /**
     * Gets a list of all participating profiles of a specified module
     *
     * @param module the module
     * @return a list of Profile objects
     */
    public List<Profile> getAllProfilesForModule(Module module) {
        List<Profile> profiles = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql1 = "SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + MODULE_ID + " = " + module.getModuleId();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MODULE_PARTICIPANTS_TABLE + " WHERE " + MODULE_ID + " = " + module.getModuleId(), null);
        Log.i("DBHELPER", sql1);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            profiles.add(getProfile(cursor.getLong(1)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return profiles;
    }

    /**
     * Converts values retrieved from the database to a Module model object
     *
     * @param cursor the values retrieved from the database
     * @return a list of Module objects
     */
    public Module cursorToModule(Cursor cursor) {
        Long moduleId = cursor.getLong(0);
        String name = cursor.getString(1);
        String description = cursor.getString(2);

        Module module = new Module(name, description, new ArrayList<Deadline>(), new ArrayList<Long>());
        module.setModuleId(moduleId);
        return module;
    }


    ////////////////////
    //    DEADLINES   //
    ////////////////////

    /**
     * Converts the values retrieved from the database to a Deadline object
     *
     * @param cursor the values retrieved from the database
     * @return a Deadline object
     */
    public Deadline cursorToDeadline(Cursor cursor) {
        Long deadlineId = cursor.getLong(0);
        String name = cursor.getString(1);
        String date = cursor.getString(2);
        Long moduleId = cursor.getLong(3);

        Deadline deadline = new Deadline(name, date, moduleId);
        deadline.setDeadlineId(deadlineId);
        return deadline;
    }


    /**
     * Updates a module's deadlines in the database
     *
     * @param moduleId the module to update
     */
    public void updateDeadlines(Long moduleId, List<Deadline> deadlines) {
        Log.i("DBHELPER", "Updating deadlines for module with id - " + moduleId);

        // Delete all deadlines for module
        deleteDeadlines(moduleId);

        // Add all new updated deadlines for module
        insertDeadlines(moduleId, deadlines);
    }

    /**
     * Updates a deadline in the database
     *
     * @param deadline the deadline to update
     */
    public void updateDeadline(Deadline deadline) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("DBHELPER", "Updating DEADLINE with ID " + deadline.getDeadlineId());
        ContentValues cv1 = new ContentValues();

        cv1.put(MODULE_DEADLINE_ID, deadline.getDeadlineId());

        cv1.put(MODULE_DEADLINE_NAME, deadline.getName());

        cv1.put(MODULE_DEADLINE_DATE, deadline.getDate());

        cv1.put(MODULE_ID, deadline.getModuleId());

        db.update(MODULE_DEADLINES_TABLE, cv1, MODULE_DEADLINE_ID + " = ?",
                new String[]{String.valueOf(deadline.getDeadlineId())});
        Log.i("DBHELPER", "Updated DEADLINE with id " + deadline.getDeadlineId());

        db.close();
    }

    /**
     * Adds a list of deadlines to the database
     *
     * @param moduleId  the ID of the module
     * @param deadlines the deadlines
     */
    public void insertDeadlines(Long moduleId, List<Deadline> deadlines) {

        Log.i("DBHELPER", "Inserting deadlines for module with id - " + moduleId);

        for (Deadline deadline : deadlines) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(MODULE_DEADLINE_NAME, deadline.getName());
            cv1.put(MODULE_DEADLINE_DATE, deadline.getDate());
            cv1.put(MODULE_ID, moduleId);

            db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv1);
        }
    }

    /**
     * Deletes all deadlines from a specified module
     *
     * @param moduleId the ID of the module
     */
    public void deleteDeadlines(Long moduleId) {
        Log.i("DBHELPER", "Deleting deadlines for module with id - " + moduleId);

        // Delete all deadlines for module
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MODULE_DEADLINES_TABLE, MODULE_ID + " = ?",
                new String[]{String.valueOf(moduleId)});
        db.close();
    }

    /**
     * Gets a list of all deadlines for a specified module
     *
     * @param module the ID of the module
     * @return a list of Deadline objects
     */
    public List<Deadline> getAllDeadlinesForModule(Module module) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Deadline> deadlines = new ArrayList<>();

        String sql = "SELECT * FROM " + MODULE_DEADLINES_TABLE + " WHERE " + MODULE_ID + " = " + module.getModuleId();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MODULE_DEADLINES_TABLE + " WHERE " + MODULE_ID + " = " + module.getModuleId(), null);
        Log.i("DBHELPER", sql);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            deadlines.add(cursorToDeadline(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return deadlines;
    }

    /**
     * Deletes a specific deadline from the database
     *
     * @param moduleId   the ID of the module
     * @param deadlineId the ID of the deadline
     */
    public void deleteSpecificDeadline(Long moduleId, Long deadlineId) {
        Log.i("DBHELPER", "Deleting deadline with id " + deadlineId + " and module with id - " + moduleId);

        // Delete participant from module
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MODULE_DEADLINES_TABLE, MODULE_ID + " = ? AND " + MODULE_DEADLINE_ID + " = ?",
                new String[]{String.valueOf(moduleId), String.valueOf(deadlineId)});
    }


    //////////////////////////////
    //    MODULE PARTICIPANTS   //
    //////////////////////////////

    /**
     * Updates a module's participants in the database
     *
     * @param moduleId the module to update
     */
    public void updateParticipants(Long moduleId, List<Long> participants) {
        Log.i("DBHELPER", "Updating participants for module with id - " + moduleId);

        // Delete all interests for profile
        deleteParticipants(moduleId);

        // Add all new updated interests for profile
        insertParticipants(moduleId, participants);
    }

    /**
     * Adds a list of participants to a specified module
     *
     * @param moduleId     the ID of the module
     * @param participants the IDs of the participants
     */
    public void insertParticipants(Long moduleId, List<Long> participants) {

        Log.i("DBHELPER", "Inserting participants for module with id - " + moduleId);

        for (Long participant : participants) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(MODULE_ID, moduleId);
            cv1.put(PROFILE_ID, participant);

            db.insert(MODULE_PARTICIPANTS_TABLE, null, cv1);
        }
    }

    /**
     * Deletes all participants from a specified module
     *
     * @param moduleId
     */
    public void deleteParticipants(Long moduleId) {
        Log.i("DBHELPER", "Deleting participants for module with id - " + moduleId);

        // Delete all participants for module
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MODULE_PARTICIPANTS_TABLE, MODULE_ID + " = ?",
                new String[]{String.valueOf(moduleId)});
        db.close();
    }

    /**
     * Deletes a specified module participant from the database
     *
     * @param moduleId  the ID of the module
     * @param profileId the ID of the participant
     */
    public void deleteSpecificParticipant(Long moduleId, Long profileId) {
        Log.i("DBHELPER", "Deleting participant with id " + profileId + " and module with id - " + moduleId);

        // Delete participant from module
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MODULE_PARTICIPANTS_TABLE, MODULE_ID + " = ? AND " + PROFILE_ID + " = ?",
                new String[]{String.valueOf(moduleId), String.valueOf(profileId)});
    }


    /**
     * Inserts some initial fields into the database
     *
     * @param db the database
     */
    private void insertInitialFields(SQLiteDatabase db) {
        //////////////
        // PROFILES //
        //////////////
        ContentValues cv = new ContentValues();
        cv.put(PROFILE_NAME, "Sam Ford");
        cv.put(PROFILE_DESC, "Joined through the Higher Apprentice programme and joined the DSU business unit");
        cv.put(PROFILE_EMAIL, "sam.ford@capgemini.com");
        cv.put(PROFILE_BASE, "Holborn");
        cv.put(PROFILE_GRADE, 4);
        cv.put(PROFILE_JOB_TITLE, "Software Engineer");
        cv.put(PROFILE_JOIN_DATE, "15/07/2013");
        cv.put(PROFILE_IS_ADMIN, 1);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(PROFILE_NAME, "Steve Andrews");
        cv2.put(PROFILE_DESC, "Joined as a graduate, now working in the Brighton area on several projects");
        cv2.put(PROFILE_EMAIL, "steve.andrews@capgemini.com");
        cv2.put(PROFILE_BASE, "Telford");
        cv2.put(PROFILE_GRADE, 7);
        cv2.put(PROFILE_JOB_TITLE, "Manager");
        cv2.put(PROFILE_JOIN_DATE, "12/04/2015");
        cv2.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(PROFILE_NAME, "Alison Crowler");
        cv3.put(PROFILE_DESC, "Previously worked at IBM, have worked on several different accounts, most in India region");
        cv3.put(PROFILE_EMAIL, "alison.crowler@capgemini.com");
        cv3.put(PROFILE_BASE, "Aston");
        cv3.put(PROFILE_GRADE, 6);
        cv3.put(PROFILE_JOB_TITLE, "Business Analyst");
        cv3.put(PROFILE_JOIN_DATE, "18/01/2005");
        cv3.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv3);

        ContentValues cv21 = new ContentValues();
        cv21.put(PROFILE_NAME, "Harry Karl");
        cv21.put(PROFILE_DESC, "Had a wide range of roles throughout my career, both technical and non-technical.");
        cv21.put(PROFILE_EMAIL, "harry.karl@capgemini.com");
        cv21.put(PROFILE_BASE, "Holborn");
        cv21.put(PROFILE_GRADE, 7);
        cv21.put(PROFILE_JOB_TITLE, "Test Analyst");
        cv21.put(PROFILE_JOIN_DATE, "16/02/2011");
        cv21.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv21);

        ContentValues cv22 = new ContentValues();
        cv22.put(PROFILE_NAME, "Jim Portland");
        cv22.put(PROFILE_DESC, "Previously worked at BAE Systems before joining Capgemini as an apprentice");
        cv22.put(PROFILE_EMAIL, "jim.portland@capgemini.com");
        cv22.put(PROFILE_BASE, "Aston");
        cv22.put(PROFILE_GRADE, 2);
        cv22.put(PROFILE_JOB_TITLE, "Business Analyst");
        cv22.put(PROFILE_JOIN_DATE, "11/04/2008");
        cv22.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv22);

        ContentValues cv23 = new ContentValues();
        cv23.put(PROFILE_NAME, "Tim Asler");
        cv23.put(PROFILE_DESC, "Studied maths at college, now looking to use my skills to enhance my technology career!");
        cv23.put(PROFILE_EMAIL, "tim.asler@capgemini.com");
        cv23.put(PROFILE_BASE, "Holborn");
        cv23.put(PROFILE_GRADE, 2);
        cv23.put(PROFILE_JOB_TITLE, "Software Engineer");
        cv23.put(PROFILE_JOIN_DATE, "15/09/2014");
        cv23.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv23);

        ContentValues cv24 = new ContentValues();
        cv24.put(PROFILE_NAME, "Charles Gregory");
        cv24.put(PROFILE_DESC, "Studied physics in college, now working as a Business Analyst");
        cv24.put(PROFILE_EMAIL, "charles.gregory@capgemini.com");
        cv24.put(PROFILE_BASE, "Telford");
        cv24.put(PROFILE_GRADE, 8);
        cv24.put(PROFILE_JOB_TITLE, "Business Analyst");
        cv24.put(PROFILE_JOIN_DATE, "16/01/2011");
        cv24.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv24);

        ContentValues cv25 = new ContentValues();
        cv25.put(PROFILE_NAME, "Sarah Paul");
        cv25.put(PROFILE_DESC, "Currently working as a Java developer on an account in Wales, previously worked in the South East.");
        cv25.put(PROFILE_EMAIL, "sarah.paul@capgemini.com");
        cv25.put(PROFILE_BASE, "Holborn");
        cv25.put(PROFILE_GRADE, 5);
        cv25.put(PROFILE_JOB_TITLE, "Software Engineer");
        cv25.put(PROFILE_JOIN_DATE, "18/09/2010");
        cv25.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv25);

        ContentValues cv26 = new ContentValues();
        cv26.put(PROFILE_NAME, "Jane McGil");
        cv26.put(PROFILE_DESC, "Working as a manager on several accounts based in Scotland, previously worked as a C# Dev");
        cv26.put(PROFILE_EMAIL, "jane.mcgil@capgemini.com");
        cv26.put(PROFILE_BASE, "Scotland");
        cv26.put(PROFILE_GRADE, 8);
        cv26.put(PROFILE_JOB_TITLE, "Manager");
        cv26.put(PROFILE_JOIN_DATE, "03/02/2016");
        cv26.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv26);

        ContentValues cv27 = new ContentValues();
        cv27.put(PROFILE_NAME, "Barry Carlos");
        cv27.put(PROFILE_DESC, "Working as a front end web developer on an account in Ireland.");
        cv27.put(PROFILE_EMAIL, "harry.carlos@capgemini.com");
        cv27.put(PROFILE_BASE, "Aston");
        cv27.put(PROFILE_GRADE, 2);
        cv27.put(PROFILE_JOB_TITLE, "Software Engineer");
        cv27.put(PROFILE_JOIN_DATE, "21/08/2007");
        cv27.put(PROFILE_IS_ADMIN, 0);
        db.insert(PROFILE_TABLE, PROFILE_ID, cv27);


        ////////////////
        // Categories //
        ////////////////
        ContentValues cv16 = new ContentValues();
        cv16.put(CATEGORY_NAME, "Development");
        db.insert(CATEGORIES_TABLE, CATEGORY_ID, cv16);

        ContentValues cv17 = new ContentValues();
        cv17.put(CATEGORY_NAME, "Design");
        db.insert(CATEGORIES_TABLE, CATEGORY_ID, cv17);

        ContentValues cv18 = new ContentValues();
        cv18.put(CATEGORY_NAME, "Testing");
        db.insert(CATEGORIES_TABLE, CATEGORY_ID, cv18);

        ContentValues cv19 = new ContentValues();
        cv19.put(CATEGORY_NAME, "Project Management");
        db.insert(CATEGORIES_TABLE, CATEGORY_ID, cv19);

        ContentValues cv20 = new ContentValues();
        cv20.put(CATEGORY_NAME, "Business Analyst");
        db.insert(CATEGORIES_TABLE, CATEGORY_ID, cv20);


        //////////////
        //  SKILLS  //
        //////////////
        ContentValues cv4 = new ContentValues();
        cv4.put(SKILL_NAME, "SQL");
        cv4.put(CATEGORY_ID, 1);
        cv4.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE, EVENT_ID, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put(SKILL_NAME, "Java");
        cv5.put(CATEGORY_ID, 1);
        cv5.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE, EVENT_ID, cv5);

        ContentValues cv6 = new ContentValues();
        cv6.put(SKILL_NAME, "UI Design");
        cv6.put(CATEGORY_ID, 2);
        cv6.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE, EVENT_ID, cv6);

        ContentValues cv7 = new ContentValues();
        cv7.put(SKILL_NAME, "Requirements Gathering");
        cv7.put(CATEGORY_ID, 5);
        cv7.put(PROFILE_ID, 2);
        db.insert(SKILLS_TABLE, EVENT_ID, cv7);

        ContentValues cv8 = new ContentValues();
        cv8.put(SKILL_NAME, "Requirements Analysis");
        cv8.put(CATEGORY_ID, 5);
        cv8.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE, EVENT_ID, cv8);

        ContentValues cv9 = new ContentValues();
        cv9.put(SKILL_NAME, "Test Automation");
        cv9.put(CATEGORY_ID, 3);
        cv9.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE, EVENT_ID, cv9);

        ContentValues cv28 = new ContentValues();
        cv28.put(SKILL_NAME, "Time Management");
        cv28.put(CATEGORY_ID, 4);
        cv28.put(EVENT_ID, 1);
        db.insert(SKILLS_TABLE, PROFILE_ID, cv28);

        ContentValues cv29 = new ContentValues();
        cv29.put(SKILL_NAME, "SQL");
        cv29.put(CATEGORY_ID, 1);
        cv29.put(PROFILE_ID, 4);
        db.insert(SKILLS_TABLE, EVENT_ID, cv29);

        ContentValues cv30 = new ContentValues();
        cv30.put(SKILL_NAME, "Java");
        cv30.put(CATEGORY_ID, 1);
        cv30.put(PROFILE_ID, 5);
        db.insert(SKILLS_TABLE, EVENT_ID, cv30);

        ContentValues cv31 = new ContentValues();
        cv31.put(SKILL_NAME, "UI Design");
        cv31.put(CATEGORY_ID, 2);
        cv31.put(PROFILE_ID, 6);
        db.insert(SKILLS_TABLE, EVENT_ID, cv31);

        ContentValues cv32 = new ContentValues();
        cv32.put(SKILL_NAME, "Exploratory Testing");
        cv32.put(CATEGORY_ID, 3);
        cv32.put(PROFILE_ID, 7);
        db.insert(SKILLS_TABLE, EVENT_ID, cv32);

        ContentValues cv33 = new ContentValues();
        cv33.put(SKILL_NAME, "Project Bidding");
        cv33.put(CATEGORY_ID, 4);
        cv33.put(PROFILE_ID, 8);
        db.insert(SKILLS_TABLE, EVENT_ID, cv33);

        ContentValues cv34 = new ContentValues();
        cv34.put(SKILL_NAME, "Test Planning");
        cv34.put(CATEGORY_ID, 3);
        cv34.put(PROFILE_ID, 9);
        db.insert(SKILLS_TABLE, EVENT_ID, cv34);

        ContentValues cv35 = new ContentValues();
        cv35.put(SKILL_NAME, "UI Design");
        cv35.put(CATEGORY_ID, 2);
        cv35.put(PROFILE_ID, 10);
        db.insert(SKILLS_TABLE, EVENT_ID, cv35);

        ContentValues cv36 = new ContentValues();
        cv36.put(SKILL_NAME, "Java");
        cv36.put(CATEGORY_ID, 1);
        cv36.put(EVENT_ID, 2);
        db.insert(SKILLS_TABLE, PROFILE_ID, cv36);


        /////////////////
        //  INTERESTS  //
        /////////////////
        ContentValues cv10 = new ContentValues();
        cv10.put(INTEREST_NAME, "Test Planning");
        cv10.put(CATEGORY_ID, 3);
        cv10.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE, null, cv10);

        ContentValues cv11 = new ContentValues();
        cv11.put(INTEREST_NAME, "Test Automation");
        cv11.put(CATEGORY_ID, 3);
        cv11.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE, null, cv11);

        ContentValues cv12 = new ContentValues();
        cv12.put(INTEREST_NAME, "Deployment");
        cv12.put(CATEGORY_ID, 1);
        cv12.put(PROFILE_ID, 3);
        db.insert(INTERESTS_TABLE, null, cv12);

        ContentValues cv13 = new ContentValues();
        cv13.put(INTEREST_NAME, "AGILE");
        cv13.put(CATEGORY_ID, 4);
        cv13.put(PROFILE_ID, 2);
        db.insert(INTERESTS_TABLE, null, cv13);

        ContentValues cv14 = new ContentValues();
        cv14.put(INTEREST_NAME, "Database Management");
        cv14.put(CATEGORY_ID, 1);
        cv14.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE, null, cv14);

        ContentValues cv15 = new ContentValues();
        cv15.put(INTEREST_NAME, "Requirements Gathering");
        cv15.put(CATEGORY_ID, 5);
        cv15.put(PROFILE_ID, 3);
        db.insert(INTERESTS_TABLE, null, cv15);

        ContentValues cv41 = new ContentValues();
        cv41.put(INTEREST_NAME, "HTML");
        cv41.put(CATEGORY_ID, 1);
        cv41.put(PROFILE_ID, 5);
        db.insert(INTERESTS_TABLE, null, cv41);

        ContentValues cv42 = new ContentValues();
        cv42.put(INTEREST_NAME, "Database Management");
        cv42.put(CATEGORY_ID, 1);
        cv42.put(PROFILE_ID, 6);
        db.insert(INTERESTS_TABLE, null, cv42);

        ContentValues cv43 = new ContentValues();
        cv43.put(INTEREST_NAME, "Requirements Gathering");
        cv43.put(CATEGORY_ID, 5);
        cv43.put(PROFILE_ID, 7);
        db.insert(INTERESTS_TABLE, null, cv15);


        /////////////////
        //    EVENTS   //
        /////////////////
        ContentValues cv37 = new ContentValues();
        cv37.put(EVENT_NAME, "Managing Your Time: The Basics");
        cv37.put(EVENT_DESCRIPTION, "Goes over the basics of how to manage your time when working on complex, fast paced projects, including how to avoid stress and other common issues.");
        cv37.put(EVENT_LOCATION, "18 Force Road, SW12 4EP, London");
        cv37.put(EVENT_DATE, "14/12/2018 14:30");
        cv37.put(EVENT_GOOD_FOR, "People that have very time-restricted roles, where time management is an issue");
        cv37.put(EVENT_PREREQUISITES, "General understanding of the different stages of a project, including deadlines, and due dates.");
        cv37.put(EVENT_CREATOR, 1);
        db.insert(EVENTS_TABLE, EVENT_ID, cv37);

        ContentValues cv38 = new ContentValues();
        cv38.put(EVENT_NAME, "Java Team Apprentice Meet Up!");
        cv38.put(EVENT_DESCRIPTION, "A chance to get to know other apprentices that are interested in Java development!");
        cv38.put(EVENT_LOCATION, "17 Angel Hill, SW10 9PA, London");
        cv38.put(EVENT_DATE, "17/11/2018 18:45");
        cv38.put(EVENT_GOOD_FOR, "Apprentices looking to meet new people with similar development interests");
        cv38.put(EVENT_PREREQUISITES, "At least a beginner level understanding of Java development");
        cv38.put(EVENT_CREATOR, 1);
        db.insert(EVENTS_TABLE, EVENT_ID, cv38);


        ////////////////
        //   MODULES  //
        ////////////////
        ContentValues cv39 = new ContentValues();
        cv39.put(MODULE_NAME, "Datamining");
        cv39.put(MODULE_DESCRIPTION, "What is datamining? What is it used for? This module will take you through the uses and applications of datamining, and goes into other subjects such as machine learning, AI, neural networks.");
        db.insert(MODULES_TABLE, MODULE_ID, cv39);

        ContentValues cv40 = new ContentValues();
        cv40.put(MODULE_NAME, "Programming Language Concepts");
        cv40.put(MODULE_DESCRIPTION, "Goes over the basics and fundamentals of several important programming languages, including Java, Python and SQL");
        db.insert(MODULES_TABLE, MODULE_ID, cv40);


        /////////////////////////
        //   MODULE DEADLINES  //
        /////////////////////////
        ContentValues cv44 = new ContentValues();
        cv44.put(MODULE_DEADLINE_NAME, "Datamining Lab 1 Test");
        cv44.put(MODULE_DEADLINE_DATE, "13/09/2018 14:50");
        cv44.put(MODULE_ID, 1);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv44);

        ContentValues cv45 = new ContentValues();
        cv45.put(MODULE_DEADLINE_NAME, "Datamining Lab 2 Test");
        cv45.put(MODULE_DEADLINE_DATE, "17/09/2018 16:30");
        cv45.put(MODULE_ID, 1);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv45);

        ContentValues cv46 = new ContentValues();
        cv46.put(MODULE_DEADLINE_NAME, "Datamining Final Exam");
        cv46.put(MODULE_DEADLINE_DATE, "27/10/2018 11:15");
        cv46.put(MODULE_ID, 1);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv46);

        ContentValues cv47 = new ContentValues();
        cv47.put(MODULE_DEADLINE_NAME, "PLC Coursework Definition");
        cv47.put(MODULE_DEADLINE_DATE, "17/09/2018 17:30");
        cv47.put(MODULE_ID, 2);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv47);

        ContentValues cv48 = new ContentValues();
        cv48.put(MODULE_DEADLINE_NAME, "PLC Lab 1 Test");
        cv48.put(MODULE_DEADLINE_DATE, "08/09/2018 18:00");
        cv48.put(MODULE_ID, 2);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv48);

        ContentValues cv49 = new ContentValues();
        cv49.put(MODULE_DEADLINE_NAME, "PLC Coursework Final Deadline");
        cv49.put(MODULE_DEADLINE_DATE, "26/11/2018 13:30");
        cv49.put(MODULE_ID, 2);
        db.insert(MODULE_DEADLINES_TABLE, MODULE_DEADLINE_ID, cv49);
    }
}
