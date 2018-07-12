package com.mobdev.sam.apprenticeapp.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sam on 12/07/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    // DB
    private static final String DB_NAME = "ApprenticeAppDB";
    private static final int DB_VERSION = 1;

    // Profile
    private static final String PROFILE_TABLE = "Profiles";


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


        // Create Notes table


        // Create Note Add table


        // Create Modules table


        // Create Module Participants table


        // Create Events table


        // Create Event Attendees table


        // Create Grid Diary table


        // Create Contacts table


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private void insertInitialFields(SQLiteDatabase db) {

    }
}
