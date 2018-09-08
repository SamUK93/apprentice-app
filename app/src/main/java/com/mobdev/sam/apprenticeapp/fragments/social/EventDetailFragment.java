package com.mobdev.sam.apprenticeapp.fragments.social;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.DatePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.TimePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.profile.AddSkillsInterestsFragment;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The 'Event Detail' fragment, which displays information on an event, and allows the user
 * to view attendees, share, mark as attending.
 * <p>
 * If the user is the creator of the event, they can also edit it and save the changes to the
 * database.
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
    private TextView dateText;
    private TextView timeText;
    private Button setTimeButton;
    private Button setDateButton;
    private EditText locationText;
    private EditText goodForText;
    private EditText prerequisitesText;
    private TextView skillInfoText;
    private Button addSkillButton;
    private Button attendButton;
    private Button shareButton;
    private Button saveButton;
    private Button cancelAttendanceButton;
    private Button deleteEventButton;
    private TextView noSkillsLabel;

    // Date formats
    SimpleDateFormat dateFormatFull = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");
    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat(
            "dd/MM/yyyy");
    SimpleDateFormat timeOnlyFormat = new SimpleDateFormat(
            "HH:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.event_detail_layout, container, false);

        // Date/Time buttons and fields
        setTimeButton = myView.findViewById(R.id.pickTimeButton);
        setDateButton = myView.findViewById(R.id.pickDateButton);
        dateText = myView.findViewById(R.id.dateText);
        timeText = myView.findViewById(R.id.timeText);

        if (!isNew) {
            // Set the date
            Date date = null;

            try {
                date = dateFormatFull.parse(event.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            dateText.setText(String.valueOf(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))));

            timeText.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(calendar.get(Calendar.MINUTE)));
        }


        // Check if the current user is attending the event
        if (!owner) {
            List<Event> events = dbHelper.getAllEventsProfileAttending(userProfile.getId());
            if (events.size() < 1) {
                isAttending = false;
            }
            for (Event eventCheck : events) {
                if (eventCheck.getEventId().equals(this.event.getEventId())) {
                    isAttending = true;
                    break;
                } else
                    isAttending = false;
            }

            // Hide the datepicker buttons
            setTimeButton.setVisibility(View.GONE);
            setDateButton.setVisibility(View.GONE);
        } else {
            // User is owner of event
            //isAttending = false;

            setTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set time button clicked, open timepicker
                    showTimePickerDialog();
                }
            });


            setDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set date button clicked, open datepicker
                    showDatePickerDialog();
                }
            });
        }


        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillsLayout = myView.findViewById(R.id.skillsSection);
        final LinearLayout skillsHeaderLayout = myView.findViewById(R.id.skillsHeaderSection);
        final LinearLayout currentSkillsLayout = myView.findViewById(R.id.currentSkillsSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);
        if (!owner)
            disableEditText(titleText);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);
        if (!owner)
            disableEditText(descriptionText);

        viewAttendeesButton = myView.findViewById(R.id.viewAttendeesButton);
        if (isNew) {
            viewAttendeesButton.setVisibility(View.GONE);
        }
        viewAttendeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                // Pass it the event
                bundle.putSerializable("event", event);
                bundle.putSerializable("profile", userProfile);
                // Create a new Event Attendees fragment
                EventAttendeesFragment eventAttendeesFragment = new EventAttendeesFragment();
                eventAttendeesFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Event Attendees fragment
                transaction.replace(R.id.content_frame, eventAttendeesFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // LOCATION
        locationText = myView.findViewById(R.id.locationText);
        if (!owner)
            disableEditText(locationText);

        // GOOD FOR
        goodForText = myView.findViewById(R.id.goodForText);
        if (!owner)
            disableEditText(goodForText);

        // PREREQUISITES
        prerequisitesText = myView.findViewById(R.id.prerequisitesText);
        if (!owner)
            disableEditText(prerequisitesText);

        // SKILL INFO TEXT
        skillInfoText = myView.findViewById(R.id.skillInfoText);
        if (!isNew) {
            skillInfoText.setVisibility(View.GONE);
        }

        // ADD SKILL BUTTON
        addSkillButton = myView.findViewById(R.id.addSkillButton);
        if (!owner || isNew)
            addSkillButton.setVisibility(View.GONE);
        addSkillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Add skill button clicked, start a new 'AddSkillsInterests' fragment
                // with the searchType 'event'
                Bundle bundle = new Bundle();
                // Pass it the event
                bundle.putSerializable("event", event);
                bundle.putString("searchType", "event");
                // Create a new AddSkillsInterests fragment
                AddSkillsInterestsFragment addSkillsInterestsFragment = new AddSkillsInterestsFragment();
                addSkillsInterestsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new AddSkillsInterests fragment
                transaction.replace(R.id.content_frame, addSkillsInterestsFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // SKILLS
        if (!isNew && event.getRelatedSkills().size() > 0) {
            for (Skill skill : event.getRelatedSkills()) {
                // For each skill on the event, create a textview add it to the view
                TextView skillRow = new TextView(getContext());
                skillRow.setText(skill.getName());
                skillsLayout.addView(skillRow);
            }
        }
        else if (!isNew) {
            noSkillsLabel = myView.findViewById(R.id.noSkillsLabel);
                noSkillsLabel.setVisibility(View.VISIBLE);
        }


        // ATTEND BUTTON
        attendButton = myView.findViewById(R.id.attendButton);
        if (isAttending || isNew || owner)
            attendButton.setVisibility(View.GONE);

        attendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Attend button clicked, add current user to the event attendees in the database
                dbHelper.insertEventAttendee(event.getEventId(), userProfile.getId());
                // Hide the attend button and display the cancel attendance button
                attendButton.setVisibility(View.GONE);
                cancelAttendanceButton.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "You are now attending!", Toast.LENGTH_LONG).show();
            }
        });

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.eventSaveButton);
        if (!owner)
            saveButton.setVisibility(View.GONE);
        if (isNew)
            saveButton.setText("CREATE EVENT");

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked

                if (titleText.getText().toString().equals("") || descriptionText.getText().toString().equals("") || locationText.getText().toString().equals("")
                        || dateText.getText().toString().equals("") || timeText.getText().toString().equals("") || goodForText.getText().toString().equals("")
                        || prerequisitesText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "One or more fields are empty, ensure all fields are completed and try again", Toast.LENGTH_LONG).show();
                }
                else {
                    if (isNew) {
                        // If this is a new event, create a new Event object, add the values from the fields
                        // and add it to the database
                        event = new Event(titleText.getText().toString(),
                                descriptionText.getText().toString(),
                                locationText.getText().toString(),
                                dateText.getText().toString() + " " + timeText.getText().toString(),
                                goodForText.getText().toString(),
                                prerequisitesText.getText().toString(),
                                new ArrayList<Skill>(),
                                userProfile.getId());

                        event.setEventId(dbHelper.insertEvent(event));

                        // Enable the add skill button, because the event now exists
                        addSkillButton.setVisibility(View.VISIBLE);
                        isNew = false;
                        skillInfoText.setVisibility(View.GONE);
                        saveButton.setText("SAVE EVENT");


                        Toast.makeText(getActivity(), "New Event Created Successfully!", Toast.LENGTH_LONG).show();
                    } else {

                        // This is not a new event, so update the existing event in the database
                        event.setName(titleText.getText().toString());
                        event.setDescription(descriptionText.getText().toString());
                        event.setDate(dateText.getText().toString() + " " + timeText.getText().toString());
                        event.setLocation(locationText.getText().toString());
                        event.setGoodFor(goodForText.getText().toString());
                        event.setPrerequisites(prerequisitesText.getText().toString());

                        dbHelper.updateEvent(event);

                        Toast.makeText(getActivity(), "Event Saved Successfully!", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                }
            }
        });

        // SHARE BUTTON
        shareButton = myView.findViewById(R.id.shareButton);
        if (isNew)
            shareButton.setVisibility(View.GONE);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share button clicked, so start a new ACTION SEND with the event info
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareContent = "Hello!\n " + userProfile.getName() + " has shared an event with you using the Capgemini Apprentice App!\n\n" +
                        "Event Name: " + event.getName() + "\n\n" +
                        "Description: " + event.getDescription() + "\n\n" +
                        "Location: " + event.getLocation() + "\n\n" +
                        "Date/Time: " + event.getDate();
                intent.putExtra(Intent.EXTRA_SUBJECT, "New Event Invite!");
                intent.putExtra(Intent.EXTRA_TEXT, shareContent);
                startActivity(Intent.createChooser(intent, "Select the method to share"));
            }
        });


        // CANCEL ATTENDANCE BUTTON
        cancelAttendanceButton = myView.findViewById(R.id.cancelAttendanceButton);

        // Hide the button if the user is already attending, or is the creator of the event
        if (owner || isNew) {
            cancelAttendanceButton.setVisibility(View.GONE);
        } else if (!owner && !isAttending) {
            cancelAttendanceButton.setVisibility(View.GONE);
        }

        cancelAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel attendance button clicked, remove user from event attendees table in database
                dbHelper.deleteEventAttendee(event.getEventId(), userProfile.getId());
                Toast.makeText(getActivity(), "You are no longer attending the event", Toast.LENGTH_LONG).show();

                // Hide this button and display the attend button
                cancelAttendanceButton.setVisibility(View.GONE);
                attendButton.setVisibility(View.VISIBLE);
            }
        });


        // DELETE EVENT BUTTON
        deleteEventButton = myView.findViewById(R.id.deleteEventButton);

        // Hide the button if the user is not the event creator, or if this is a new event
        if (!owner || isNew) {
            deleteEventButton.setVisibility(View.GONE);
        }

        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete event button clicked, delete the event from the database, along with all attendees and related skills
                dbHelper.deleteEvent(event.getEventId());
                Toast.makeText(getActivity(), "Event deleted!", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();
            }
        });


        // Set all values to user's event
        if (!isNew) {
            titleText.setText(event.getName());
            descriptionText.setText(event.getDescription());
            locationText.setText(event.getLocation());
            goodForText.setText(event.getGoodFor());
            prerequisitesText.setText(event.getPrerequisites());
        }


        return myView;
    }


    // Date picker methods
    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        Calendar calendar = Calendar.getInstance();

        Date date = null;
        if (isNew) {

        } else {
            // This is not a new event, so use the event's current time as the default values
            // for the timepicker
            try {
                date = timeOnlyFormat.parse(timeText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
        }

        // Start the timepicker
        args.putInt("hours", calendar.get(Calendar.HOUR_OF_DAY));
        args.putInt("minutes", calendar.get(Calendar.MINUTE));
        newFragment.setArguments(args);
        newFragment.setCallBack(onTime);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            // Set the time field to the values selected in the timepicker
            timeText.setText(hour + ":" + minute);
        }
    };


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        Calendar calendar = Calendar.getInstance();

        if (isNew) {

        } else {
            // This is not a new event, so use the event's current date as the default values
            // for the datepicker
            Date date = null;
            try {
                date = dateOnlyFormat.parse(dateText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
        }


        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("year", calendar.get(Calendar.YEAR));
        newFragment.setArguments(args);
        newFragment.setCallBack(onDate);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // Set the date field to the values selected in the datepicker
            // (month is +1 because it starts at 0)
            dateText.setText(day + "/" + (month + 1) + "/" + year);
        }
    };


    private void disableEditText(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);
        editText.setSingleLine(false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile) getArguments().getSerializable("userProfile");
            owner = getArguments().getBoolean("owner");
            id = getArguments().getLong("eventId");
            isNew = getArguments().getBoolean("isNew");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        if (isNew) {
            getActivity().setTitle("New Event");
        }
        else {
            // If this is not a new event, get it from the db
            event = dbHelper.getEvent(id);
            getActivity().setTitle("Event - " + event.getName());
        }
    }
}
