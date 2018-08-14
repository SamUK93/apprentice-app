package com.mobdev.sam.apprenticeapp.fragments.social;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.profile.AddSkillsInterestsFragment;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 02/07/2018.
 */

public class EventDetailFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private String title;
    private Profile userProfile;
    private Event event;
    private boolean owner;
    private boolean isNew;
    private boolean isAttending;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button viewAttendeesButton;
    private EditText dateText;
    private EditText locationText;
    private EditText goodForText;
    private EditText prerequisitesText;
    private Button addSkillButton;
    private Button attendButton;
    private Button shareButton;
    private Button saveButton;
    private Button cancelAttendanceButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.event_detail_layout, container, false);

        if (!isNew) {
            event = dbHelper.getEvent(id);
        }


        if (!owner) {
            List<Event> events = dbHelper.getAllEventsProfileAttending(userProfile.getId());
            if (events.size() < 1) {
                isAttending = false;
            }
            for (Event eventCheck : events) {
                if (eventCheck.getEventId().equals(this.event.getEventId())) {
                    isAttending = true;
                    break;
                }
                else
                    isAttending = false;
            }
        }
        else {
            isAttending = false;
        }



        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillsLayout = myView.findViewById(R.id.skillsSection);
        final LinearLayout skillsHeaderLayout = myView.findViewById(R.id.skillsHeaderSection);
        final LinearLayout currentSkillsLayout = myView.findViewById(R.id.currentSkillsSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);
        if (!owner)
            titleText.setInputType(InputType.TYPE_NULL);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);
        if (!owner)
            descriptionText.setInputType(InputType.TYPE_NULL);

        viewAttendeesButton = myView.findViewById(R.id.viewAttendeesButton);
        if (isNew) {
            viewAttendeesButton.setVisibility(View.INVISIBLE);
        }
        viewAttendeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement view attendees", Toast.LENGTH_LONG).show();
            }
        });

        // DATE
        dateText = myView.findViewById(R.id.dateText);
        if (!owner)
            dateText.setInputType(InputType.TYPE_NULL);

        // LOCATION
        locationText = myView.findViewById(R.id.locationText);
        if (!owner)
            locationText.setInputType(InputType.TYPE_NULL);

        // GOOD FOR
        goodForText = myView.findViewById(R.id.goodForText);
        if (!owner)
            goodForText.setInputType(InputType.TYPE_NULL);

        // PREREQUISITES
        prerequisitesText = myView.findViewById(R.id.prerequisitesText);
        if (!owner)
            prerequisitesText.setInputType(InputType.TYPE_NULL);

        // ADD SKILL BUTTON
        //TODO THIS DOESN'T WORK FOR NEW SKILLS (BEFORE SAVED) because the event doesn't exist yet
        addSkillButton = myView.findViewById(R.id.addSkillButton);
        if (!owner)
            addSkillButton.setVisibility(View.INVISIBLE);
        addSkillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", event);
                bundle.putString("searchType", "event");
                // Create a new Search fragment
                AddSkillsInterestsFragment addSkillsInterestsFragment = new AddSkillsInterestsFragment();
                addSkillsInterestsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.content_frame, addSkillsInterestsFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // SKILLS
        if (!isNew && event.getRelatedSkills().size() > 0) {
            for (Skill skill : event.getRelatedSkills()) {
                TextView skillRow = new TextView(getContext());
                skillRow.setText(skill.getName());
                skillsLayout.addView(skillRow);
            }
        }




        // ATTEND BUTTON
        attendButton = myView.findViewById(R.id.attendButton);
        if (isAttending || isNew || owner)
            attendButton.setVisibility(View.INVISIBLE);

        attendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dbHelper.insertEventAttendee(event.getEventId(),userProfile.getId());
            }
        });

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.eventSaveButton);
        if (!owner)
            saveButton.setVisibility(View.INVISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked
                if (isNew) {
                    event = new Event(titleText.getText().toString(),
                            descriptionText.getText().toString(),
                            dateText.getText().toString(),
                            locationText.getText().toString(),
                            goodForText.getText().toString(),
                            prerequisitesText.getText().toString(),
                            new ArrayList<Skill>(),
                            userProfile.getId());

                    dbHelper.insertEvent(event);
                }

                else {
                    event.setName(titleText.getText().toString());
                    event.setDescription(descriptionText.getText().toString());
                    event.setDate(dateText.getText().toString());
                    event.setLocation(locationText.getText().toString());
                    event.setGoodFor(goodForText.getText().toString());
                    event.setPrerequisites(prerequisitesText.getText().toString());

                    dbHelper.updateEvent(event);
                }
            }
        });

        // SHARE BUTTON
        shareButton = myView.findViewById(R.id.shareButton);
        if (isNew)
            shareButton.setVisibility(View.INVISIBLE);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Implement share", Toast.LENGTH_LONG).show();
            }
        });



        // CANCEL ATTENDANCE BUTTON
        cancelAttendanceButton = myView.findViewById(R.id.cancelAttendanceButton);
        if (owner || isNew) {
            cancelAttendanceButton.setVisibility(View.INVISIBLE);
        }
        else if (!owner && !isAttending) {
            cancelAttendanceButton.setVisibility(View.INVISIBLE);
        }
        cancelAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteEventAttendee(event.getEventId(),userProfile.getId());
            }
        });


        // Set all values to user's event
        if (!isNew) {
            titleText.setText(event.getName());
            descriptionText.setText(event.getDescription());
            locationText.setText(event.getLocation());
            dateText.setText(event.getDate());
            goodForText.setText(event.getGoodFor());
            prerequisitesText.setText(event.getPrerequisites());
        }



        return myView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile)getArguments().getSerializable("userProfile");
            owner = getArguments().getBoolean("owner");
            id = getArguments().getLong("eventId");
            isNew = getArguments().getBoolean("isNew");
        }
        dbHelper = new DBHelper(getContext());
    }
}
