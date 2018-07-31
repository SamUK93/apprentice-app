package com.mobdev.sam.apprenticeapp.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.ProfileReason;
import com.mobdev.sam.apprenticeapp.models.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 12/07/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

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

    // Notes
    private static final String NOTES_TABLE = "Notes";


    //TODO: Figure out name/functionality of note 'add'
    // Note 'add'
    private static final String NOTE_ADD_TABLE = "NoteAdd";


    // Modules
    private static final String MODULES_TABLE = "Modules";


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



    // Event Attendees
    private static final String EVENT_ATTENDEES_TABLE = "EventAttendees";


    // Grid Diary
    private static final String GRID_DIARY_TABLE = "GridDiary";


    // Contacts
    private static final String CONTACTS_TABLE = "Contacts";
    private static final String CONTACT_ID = "contactId";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create Profiles table
        String sql = "CREATE TABLE " + PROFILE_TABLE +
                "(" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_NAME + " TEXT, " +
                PROFILE_DESC + " TEXT, " + PROFILE_EMAIL + " TEXT, " +
                PROFILE_BASE + " TEXT, " + PROFILE_GRADE + " INTEGER, " +
                PROFILE_JOB_TITLE + " TEXT, " + PROFILE_JOIN_DATE + " TEXT);";
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
                " INTEGER, FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " +
                CATEGORIES_TABLE + " (" + CATEGORY_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Interests table
        sql = "CREATE TABLE " + INTERESTS_TABLE +
                "(" + INTEREST_NAME + " TEXT, " + CATEGORY_ID + " INTEGER, " + PROFILE_ID +
                " INTEGER, FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " +
                CATEGORIES_TABLE + " (" + CATEGORY_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Notes table


        // Create Note Add table


        // Create Modules table


        // Create Module Participants table


        // Create Events table
        sql = "CREATE TABLE " + EVENTS_TABLE +
                "(" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_NAME + " TEXT, " +
                EVENT_DESCRIPTION + " TEXT, " + EVENT_LOCATION + " TEXT, " +
                EVENT_DATE + " TEXT, " + EVENT_GOOD_FOR + " TEXT, " +
                EVENT_PREREQUISITES + " TEXT);";
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


        insertInitialFields(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Profile cursorToProfile(Cursor cursor) {
        Long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String desc = cursor.getString(2);
        String email = cursor.getString(3);
        String base = cursor.getString(4);
        int grade = cursor.getInt(5);
        String jobTitle = cursor.getString(6);
        String joinDate = cursor.getString(7);

        Profile profile = new Profile(name, desc, new ArrayList<Skill>(), new ArrayList<Skill>(), email, base, grade, jobTitle, joinDate, null, null, null);
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


    public Skill cursorToSkill(Cursor cursor) {
        String name = cursor.getString(0);
        Long categoryId = cursor.getLong(1);
        Long profileId = cursor.getLong(2);

        Skill skill = new Skill(name, categoryId, profileId);
        return skill;
    }

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
     * Updates a profile's skills in the database
     *
     * @param profileId the profile to update
     */
    public void updateSkills(Long profileId, List<Skill> skills) {
        Log.i("DBHELPER", "Updating skills for profile with id - " + profileId);

        // Delete all skills for profile
        deleteSkills(profileId);

        // Add all new new updated skills for profile
        insertSkills(profileId, skills);
    }

    public void insertSkills(Long profileId, List<Skill> skills) {

        Log.i("DBHELPER", "Inserting skills for profile with id - " + profileId);

        for (Skill skill : skills) {
            Log.i("DBHELPER", "Adding skill  - " + skill.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv1 = new ContentValues();

            cv1.put(SKILL_NAME, skill.getName());
            cv1.put(CATEGORY_ID, skill.getCategoryId());
            cv1.put(PROFILE_ID, skill.getProfileId());

            db.insert(SKILLS_TABLE, null, cv1);
        }
    }

    public void deleteSkills(Long profileId) {
        Log.i("DBHELPER", "Deleting skills for profile with id - " + profileId);

        // Delete all skills for profile
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(SKILLS_TABLE, PROFILE_ID + " = ?",
                new String[]{String.valueOf(profileId)});
        db.close();
    }

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
            if (!skillNames.contains(skill.getName())) {
                skillNames.add(skill.getName());
                uniqueSkills.add(skill);
            }
        }
        return uniqueSkills;
    }

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
            if (!skillNames.contains(skill.getName())) {
                skillNames.add(skill.getName());
                uniqueSkills.add(skill);
            }
        }
        return uniqueSkills;
    }


    public List<ProfileReason> getAllProfilesSameSkills(List<Skill> skills) {

        List<ProfileReason> matchingProfiles = new ArrayList<>();
        List<Long> profileIdsTotal = new ArrayList<>();

        // For each of the skills specified
        for (Skill skill : skills) {

            List<Long> profileIds = new ArrayList<>();

            // Get all Profile IDs that have that skill
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + SKILLS_TABLE + " WHERE " + SKILL_NAME
                    + " = '" + skill.getName() + "';", null);
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
                    matchingProfiles.add(new ProfileReason(getProfile(profileId), "Because shares skill(s) - " + skill.getName()));
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

    public void deleteInterests(Long profileId) {
        Log.i("DBHELPER", "Deleting interests for profile with id - " + profileId);

        // Delete all interests for profile
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(INTERESTS_TABLE, PROFILE_ID + " = ?",
                new String[]{String.valueOf(profileId)});
        db.close();
    }

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
                    matchingProfiles.add(new ProfileReason(getProfile(profileId), "Because shares interest(s) - " + interest.getName()));
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


    public List<ProfileReason> getAllProfilesAllCriteria(List<Skill> skills, List<Skill> interests) {
        // Get all profiles with matching skills and interests
        List<ProfileReason> matchingSkills = getAllProfilesSameSkills(skills);
        List<ProfileReason> matchingInterests = getAllProfilesSameInterests(interests);

        List<ProfileReason> newProfilesToAdd = new ArrayList<>();

        for (ProfileReason interestMatch : matchingInterests) {
            // For each matching interest
            boolean newProfile = true;
            for (ProfileReason skillMatch : matchingSkills) {
                // Check all matching skills, if same profile, append interests
                if (skillMatch.profile.getId().equals(interestMatch.profile.getId())) {
                    Log.i("CONTACTMATCH::", "Profile already has match, appending");
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


    public List<Skill> getAllSkillsInterestsUnique() {
        List<Skill> skills = getAllSkillsUnique();
        List<Skill> interests = getAllInterestsUnique();

        for (Skill interest : interests) {
            if (!skills.contains(interest)) {
                skills.add(interest);
            }
        }
        return skills;
    }

    public List<Skill> getAllSkillsInterestsUniqueInCategory(Long categoryId) {
        List<Skill> skills = getAllSkillsUniqueInCategory(categoryId);
        List<Skill> interests = getAllInterestsUniqueInCategory(categoryId);

        for (Skill interest : interests) {
            if (!skills.contains(interest)) {
                skills.add(interest);
            }
        }
        return skills;
    }

    public List<Skill> getAllSkillsAndInterestsForProfile(Long profileId) {
        List<Skill> skills = getAllSkillsForProfile(profileId);
        List<Skill> interests = getAllInterestsForProfile(profileId);

        for (Skill interest : interests) {
            skills.add(interest);
        }
        return skills;
    }


    public Category cursorToCategory(Cursor cursor) {
        Long id = cursor.getLong(0);
        String name = cursor.getString(1);

        Category category = new Category(id, name);
        return category;
    }

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


    public Contact cursorToContact(Cursor cursor) {
        Long profileId = cursor.getLong(0);
        Long contactId = cursor.getLong(1);

        Contact contact = new Contact(profileId, contactId);
        return contact;
    }

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

    public void insertContact(Long profileId, Long contactId) {
        Log.i("DBHELPER", "Adding new contact profile with id - " + profileId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();

        cv1.put(PROFILE_ID, profileId);
        cv1.put(CONTACT_ID, contactId);

        db.insert(CONTACTS_TABLE, null, cv1);
    }

    public void deleteContact(Long profileId, Long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(CONTACTS_TABLE, PROFILE_ID + " = ? AND " + CONTACT_ID + " = ?",
                new String[]{String.valueOf(profileId),String.valueOf(contactId)});
        db.close();
    }


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
        db.insert(PROFILE_TABLE, PROFILE_ID, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(PROFILE_NAME, "Steve Skeleton");
        cv2.put(PROFILE_DESC, "Joined as a graduate, now working in the Brighton area on several projects");
        cv2.put(PROFILE_EMAIL, "steve.skeleton@capgemini.com");
        cv2.put(PROFILE_BASE, "Telford");
        cv2.put(PROFILE_GRADE, 7);
        cv2.put(PROFILE_JOB_TITLE, "Manager");
        cv2.put(PROFILE_JOIN_DATE, "12/04/2015");
        db.insert(PROFILE_TABLE, PROFILE_ID, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(PROFILE_NAME, "Alison Crowler");
        cv3.put(PROFILE_DESC, "Previously worked at IBM, have worked on several different accounts, most in India region");
        cv3.put(PROFILE_EMAIL, "alison.crowler@capgemini.com");
        cv3.put(PROFILE_BASE, "Aston");
        cv3.put(PROFILE_GRADE, 6);
        cv3.put(PROFILE_JOB_TITLE, "Business Analyst");
        cv3.put(PROFILE_JOIN_DATE, "18/01/2005");
        db.insert(PROFILE_TABLE, PROFILE_ID, cv3);


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
        db.insert(SKILLS_TABLE, null, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put(SKILL_NAME, "Java");
        cv5.put(CATEGORY_ID, 1);
        cv5.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE, null, cv5);

        ContentValues cv6 = new ContentValues();
        cv6.put(SKILL_NAME, "UI Design");
        cv6.put(CATEGORY_ID, 2);
        cv6.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE, null, cv6);

        ContentValues cv7 = new ContentValues();
        cv7.put(SKILL_NAME, "Requirements Gathering");
        cv7.put(CATEGORY_ID, 5);
        cv7.put(PROFILE_ID, 2);
        db.insert(SKILLS_TABLE, null, cv7);

        ContentValues cv8 = new ContentValues();
        cv8.put(SKILL_NAME, "Requirements Analysis");
        cv8.put(CATEGORY_ID, 5);
        cv8.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE, null, cv8);

        ContentValues cv9 = new ContentValues();
        cv9.put(SKILL_NAME, "Test Automation");
        cv9.put(CATEGORY_ID, 3);
        cv9.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE, null, cv9);


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
    }
}
