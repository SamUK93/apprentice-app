package com.mobdev.sam.apprenticeapp.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Profile;
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


    // Event Attendees
    private static final String EVENT_ATTENDEES_TABLE = "EventAttendees";


    // Grid Diary
    private static final String GRID_DIARY_TABLE = "GridDiary";


    // Contacts
    private static final String CONTACTS_TABLE = "Contacts";


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
                PROFILE_TABLE + " (" + PROFILE_ID + "), UNIQUE (" + SKILL_NAME + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Interests table
        sql = "CREATE TABLE " + INTERESTS_TABLE +
                "(" + INTEREST_NAME + " TEXT, " + CATEGORY_ID + " INTEGER, " + PROFILE_ID +
                " INTEGER, FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " +
                CATEGORIES_TABLE + " (" + CATEGORY_ID + "), FOREIGN KEY (" + PROFILE_ID + ") REFERENCES " +
                PROFILE_TABLE + " (" + PROFILE_ID + "), UNIQUE (" + INTEREST_NAME + "));";
        Log.i("DBHELPER", sql);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);


        // Create Notes table


        // Create Note Add table


        // Create Modules table


        // Create Module Participants table


        // Create Events table


        // Create Event Attendees table


        // Create Grid Diary table


        // Create Contacts table


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

        Profile profile = new Profile(name,desc,new ArrayList<Skill>(),new ArrayList<Skill>(),email,base,grade,jobTitle,joinDate,null,null,null);
        profile.setId(id);
        return profile;
    }

    /**
     * Inserts a new profile in the database
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

        myProfileId = db.insert(PROFILE_TABLE,PROFILE_ID,cv1);
        Log.i("DBHELPER", "Inserted new PROFILE with id " + profile.getId());

        profile.setId(myProfileId);
        return myProfileId;
    }

    /**
     * Updates a profile in the database
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

        db.update(PROFILE_TABLE,cv1,PROFILE_ID + " = ?",
                new String[] { String.valueOf(profile.getId()) });
        Log.i("DBHELPER", "Updated PROFILE with id " + profile.getId());

        db.close();
    }


    /**
     * Gets the profile with the specified ID from the database
     * @param id the id of the profile to get
     * @return the profile, or null if no profiles with the specified id were found
     */
    public Profile getProfile(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE + " WHERE " + PROFILE_ID + " = " + id, null);
        if (cursor.getCount() < 1) {
            return null;
        }
        else {
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

        Skill skill = new Skill(name,categoryId,profileId);
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






    public Category cursorToCategory(Cursor cursor) {
        Long id = cursor.getLong(0);
        String name = cursor.getString(1);

        Category category = new Category(id,name);
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
        db.insert(PROFILE_TABLE,PROFILE_ID,cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(PROFILE_NAME, "Steve Skeleton");
        cv2.put(PROFILE_DESC, "Joined as a graduate, now working in the Brighton area on several projects");
        cv2.put(PROFILE_EMAIL, "steve.skeleton@capgemini.com");
        cv2.put(PROFILE_BASE, "Telford");
        cv2.put(PROFILE_GRADE, 7);
        cv2.put(PROFILE_JOB_TITLE, "Manager");
        cv2.put(PROFILE_JOIN_DATE, "12/04/2015");
        db.insert(PROFILE_TABLE,PROFILE_ID,cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(PROFILE_NAME, "Alison Crowler");
        cv3.put(PROFILE_DESC, "Previously worked at IBM, have worked on several different accounts, most in India region");
        cv3.put(PROFILE_EMAIL, "alison.crowler@capgemini.com");
        cv3.put(PROFILE_BASE, "Aston");
        cv3.put(PROFILE_GRADE, 6);
        cv3.put(PROFILE_JOB_TITLE, "Business Analyst");
        cv3.put(PROFILE_JOIN_DATE, "18/01/2005");
        db.insert(PROFILE_TABLE,PROFILE_ID,cv3);


        ////////////////
        // Categories //
        ////////////////
        ContentValues cv16 = new ContentValues();
        cv16.put(CATEGORY_NAME, "Development");
        db.insert(CATEGORIES_TABLE,CATEGORY_ID,cv16);

        ContentValues cv17 = new ContentValues();
        cv17.put(CATEGORY_NAME, "Design");
        db.insert(CATEGORIES_TABLE,CATEGORY_ID,cv17);

        ContentValues cv18 = new ContentValues();
        cv18.put(CATEGORY_NAME, "Testing");
        db.insert(CATEGORIES_TABLE,CATEGORY_ID,cv18);

        ContentValues cv19 = new ContentValues();
        cv19.put(CATEGORY_NAME, "Project Management");
        db.insert(CATEGORIES_TABLE,CATEGORY_ID,cv19);

        ContentValues cv20 = new ContentValues();
        cv20.put(CATEGORY_NAME, "Business Analyst");
        db.insert(CATEGORIES_TABLE,CATEGORY_ID,cv20);


        //////////////
        //  SKILLS  //
        //////////////
        ContentValues cv4 = new ContentValues();
        cv4.put(SKILL_NAME, "SQL");
        cv4.put(CATEGORY_ID, 1);
        cv4.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE,null,cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put(SKILL_NAME, "Java");
        cv5.put(CATEGORY_ID, 1);
        cv5.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE,null,cv5);

        ContentValues cv6 = new ContentValues();
        cv6.put(SKILL_NAME, "UI Design");
        cv6.put(CATEGORY_ID, 2);
        cv6.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE,null,cv6);

        ContentValues cv7 = new ContentValues();
        cv7.put(SKILL_NAME, "Requirements Gathering");
        cv7.put(CATEGORY_ID, 5);
        cv7.put(PROFILE_ID, 2);
        db.insert(SKILLS_TABLE,null,cv7);

        ContentValues cv8 = new ContentValues();
        cv8.put(SKILL_NAME, "Requirements Analysis");
        cv8.put(CATEGORY_ID, 5);
        cv8.put(PROFILE_ID, 1);
        db.insert(SKILLS_TABLE,null,cv8);

        ContentValues cv9 = new ContentValues();
        cv9.put(SKILL_NAME, "Test Automation");
        cv9.put(CATEGORY_ID, 3);
        cv9.put(PROFILE_ID, 3);
        db.insert(SKILLS_TABLE,null,cv9);


        /////////////////
        //  INTERESTS  //
        /////////////////
        ContentValues cv10 = new ContentValues();
        cv10.put(INTEREST_NAME, "Test Planning");
        cv10.put(CATEGORY_ID, 3);
        cv10.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE,null,cv10);

        ContentValues cv11 = new ContentValues();
        cv11.put(INTEREST_NAME, "Test Automation");
        cv11.put(CATEGORY_ID, 3);
        cv11.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE,null,cv11);

        ContentValues cv12 = new ContentValues();
        cv12.put(INTEREST_NAME, "Deployment");
        cv12.put(CATEGORY_ID, 1);
        cv12.put(PROFILE_ID, 3);
        db.insert(INTERESTS_TABLE,null,cv12);

        ContentValues cv13 = new ContentValues();
        cv13.put(INTEREST_NAME, "AGILE");
        cv13.put(CATEGORY_ID, 4);
        cv13.put(PROFILE_ID, 2);
        db.insert(INTERESTS_TABLE,null,cv13);

        ContentValues cv14 = new ContentValues();
        cv14.put(INTEREST_NAME, "Database Management");
        cv14.put(CATEGORY_ID, 1);
        cv14.put(PROFILE_ID, 1);
        db.insert(INTERESTS_TABLE,null,cv14);

        ContentValues cv15 = new ContentValues();
        cv15.put(INTEREST_NAME, "Requirements Gathering");
        cv15.put(CATEGORY_ID, 5);
        cv15.put(PROFILE_ID, 3);
        db.insert(INTERESTS_TABLE,null,cv15);
    }
}
