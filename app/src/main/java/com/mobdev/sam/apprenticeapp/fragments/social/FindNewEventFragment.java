package com.mobdev.sam.apprenticeapp.fragments.social;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.EventReason;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The 'Find New Events' fragment that uses the user's skills and interests to match events that
 * may be of interest to them, and then displays them to the user.
 */

public class FindNewEventFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    // UI Elements
    LinearLayout skillEventsSection;
    Button skillSectionButton;
    LinearLayout contactEventSection;
    Button contactSectionButton;

    // Date format
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.find_new_event_layout, container, false);

        ///////////////////////////////
        //  SKILL BASED SUGGESTIONS  //
        ///////////////////////////////
        skillEventsSection = myView.findViewById(R.id.skillEventsSection);

        // Skill events section show/hide button
        skillSectionButton = myView.findViewById(R.id.skillEventsSectionButton);
        skillSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (skillSectionButton.getText().toString().equals("SHOW")) {
                    skillEventsSection.setVisibility(View.VISIBLE);
                    skillSectionButton.setText("HIDE");
                }
                else if (skillSectionButton.getText().toString().equals("HIDE")) {
                    skillEventsSection.setVisibility(View.GONE);
                    skillSectionButton.setText("SHOW");
                }
            }
        });


        // Get events with similar interests and skills to populate the 'Suggested Events' section
        List<EventReason> matchingEvents = dbHelper.getAllEventsAllCriteria(myProfile.getSkills(), myProfile.getInterests());
        Log.i("EVENTMATCH::", "TOTAL EVENTS MATCHED = " + matchingEvents.size());

        final List<EventReason> pastEvents = new ArrayList<>();
        // Get all of events in the past that the user attended
        for (EventReason ev : matchingEvents) {
            // For each of the events that the user is attending

            // Convert the date string into a calendar object
            Date date = null;
            try {
                date = dateFormat.parse(ev.event.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (calendar.compareTo(Calendar.getInstance()) < 0) {
                // If the date of the event is in the past, add the the past events list
                pastEvents.add(ev);
            }
        }

            matchingEvents.removeAll(pastEvents);


        if (matchingEvents.size() > 0) {
            // Get current events of user
            List<Event> events = dbHelper.getAllEventsProfileAttending(myProfile.getId());
            List<Long> eventIds = new ArrayList<>();
            for (Event event : events) {
                eventIds.add(event.getEventId());
            }

            // Remove the user's current events from the matching events list
            List<EventReason> eventsToRemove = new ArrayList<>();
            for (EventReason event : matchingEvents) {
                // For each of the matching events
                if (event.event.getCreatorId().equals(myProfile.getId())) {
                    // If it was created by the user, add it to the remove list
                    eventsToRemove.add(event);
                } else if (eventIds.contains(event.event.getEventId())) {
                    // If the user is already attending the event, add it to the remove list
                    eventsToRemove.add(event);
                    Log.i("EVENTMATCH::", "REMOVING ALREADY ATTENDING EVENT with ID " + event.event.getEventId());
                }
            }
            // Remove all 'events to remove' from the matching events list
            matchingEvents.removeAll(eventsToRemove);

            // Add the, now pruned, events to the view
            for (final EventReason eventReason : matchingEvents) {
                // For each event, create a layout
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(3, 3, 3, 15);
                linearLayout.setLayoutParams(params);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Event clicked, start a new 'Event Detail' fragment and pass the event
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userProfile", myProfile);
                        bundle.putSerializable("eventId", eventReason.event.getEventId());
                        bundle.putBoolean("owner", false);
                        bundle.putBoolean("attending", false);
                        bundle.putBoolean("isNew", false);
                        // Create a new Event Detail fragment
                        EventDetailFragment eventFragment = new EventDetailFragment();
                        eventFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace the current fragment with the new Event Detail fragment
                        transaction.replace(R.id.content_frame, eventFragment);
                        // Add transaction to the back stack and commit
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                // Add name and reason TextViews for the event
                TextView nameRow = new TextView(getContext());
                TextView reasonRow = new TextView(getContext());

                nameRow.setText(eventReason.event.getName());
                nameRow.setTextSize(15);
                nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
                reasonRow.setText(eventReason.reason);

                linearLayout.addView(nameRow);
                linearLayout.addView(reasonRow);

                // Add the layout to the Events section
                skillEventsSection.addView(linearLayout);
            }
        } else {
            // No matching events found, inform the user

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);
            TextView nameRow = new TextView(getContext());

            nameRow.setText("There are currently no upcoming events we think you'd be interested in! Add some more skills or interests to your profile and please check back soon.");
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(nameRow);
            skillEventsSection.addView(linearLayout);
        }



        ///////////////////////////////
        // CONTACT BASED SUGGESTIONS //
        ///////////////////////////////
        contactEventSection = myView.findViewById(R.id.contactEventsSection);
        contactEventSection.setVisibility(View.GONE);

        // Contact events section show/hide button
        contactSectionButton = myView.findViewById(R.id.contactEventsSectionButton);
        contactSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactSectionButton.getText().toString().equals("SHOW")) {
                    contactEventSection.setVisibility(View.VISIBLE);
                    contactSectionButton.setText("HIDE");
                }
                else if (contactSectionButton.getText().toString().equals("HIDE")) {
                    contactEventSection.setVisibility(View.GONE);
                    contactSectionButton.setText("SHOW");
                }
            }
        });


        // Get events that the user's contacts are attending
        List<EventReason> contactEvents = new ArrayList<>();
        for (Contact contact : dbHelper.getAllContactsForProfile(myProfile.getId())) {
            // For each contact of the user
            // Get the events that they are attending
            List<Event> eventsSpecificContact = dbHelper.getAllEventsProfileAttending(contact.getContactId());
            for(Event specificEvent : eventsSpecificContact) {
                // For each event they are attending, add it, plus the reason to the contact events list.
                contactEvents.add(new EventReason(specificEvent,"Because your contact " + dbHelper.getProfile(contact.getContactId()).getName() + " is attending this event"));
            }
        }

            final List<EventReason> pastContactEvents = new ArrayList<>();
            // Get all of events in the past that the user attended
            for (EventReason contactEv : contactEvents) {
                // For each of the events that the user is attending

                // Convert the date string into a calendar object
                Date date = null;
                try {
                    date = dateFormat.parse(contactEv.event.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                if (calendar.compareTo(Calendar.getInstance()) < 0) {
                    // If the date of the event is in the past, add the the past events list
                    pastContactEvents.add(contactEv);
                }
            }


                contactEvents.removeAll(pastContactEvents);



        Log.i("CONTACTEVENT!!::::","Number of events matched for contacts = " + contactEvents.size());

        if (contactEvents.size() > 0) {
            // Get current events of user
            List<Event> events = dbHelper.getAllEventsProfileAttending(myProfile.getId());
            List<Long> eventIds = new ArrayList<>();
            for (Event event : events) {
                eventIds.add(event.getEventId());
            }

            // Remove the user's current events from the matching events list
            List<EventReason> eventsToRemove = new ArrayList<>();
            for (EventReason event : contactEvents) {
                // For each of the matching events
                if (event.event.getCreatorId().equals(myProfile.getId())) {
                    // If it was created by the user, add it to the remove list
                    eventsToRemove.add(event);
                } else if (eventIds.contains(event.event.getEventId())) {
                    // If the user is already attending the event, add it to the remove list
                    eventsToRemove.add(event);
                    Log.i("EVENTMATCH::", "REMOVING ALREADY ATTENDING EVENT with ID " + event.event.getEventId());
                }
            }
            // Remove all 'events to remove' from the matching events list
            contactEvents.removeAll(eventsToRemove);

            // Add the, now pruned, events to the view
            for (final EventReason eventReason : contactEvents) {
                // For each event, create a layout
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(3, 3, 3, 15);
                linearLayout.setLayoutParams(params);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Event clicked, start a new 'Event Detail' fragment and pass the event
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userProfile", myProfile);
                        bundle.putSerializable("eventId", eventReason.event.getEventId());
                        bundle.putBoolean("owner", false);
                        bundle.putBoolean("attending", false);
                        bundle.putBoolean("isNew", false);
                        // Create a new Event Detail fragment
                        EventDetailFragment eventFragment = new EventDetailFragment();
                        eventFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace the current fragment with the new Event Detail fragment
                        transaction.replace(R.id.content_frame, eventFragment);
                        // Add transaction to the back stack and commit
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                // Add name and reason TextViews for the event
                TextView nameRow = new TextView(getContext());
                TextView reasonRow = new TextView(getContext());

                nameRow.setText(eventReason.event.getName());
                nameRow.setTextSize(15);
                nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
                reasonRow.setText(eventReason.reason);

                linearLayout.addView(nameRow);
                linearLayout.addView(reasonRow);

                // Add the layout to the Events section
                contactEventSection.addView(linearLayout);
            }
        } else {
            // No matching events found, inform the user

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);
            TextView nameRow = new TextView(getContext());

            nameRow.setText("There are currently no upcoming events we think you'd be interested in! Add some more skills or interests to your profile and please check back soon.");
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(nameRow);
            contactEventSection.addView(linearLayout);
        }


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("profile");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Find New Events");

    }
}
