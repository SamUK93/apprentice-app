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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.EventReason;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
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
    LinearLayout eventsSection;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.find_new_event_layout, container, false);

        eventsSection = myView.findViewById(R.id.eventsSection);

        // Get events with similar interests and skills to populate the 'Suggested Events' section
        List<EventReason> matchingEvents = dbHelper.getAllEventsAllCriteria(myProfile.getSkills(), myProfile.getInterests());
        Log.i("EVENTMATCH::", "TOTAL EVENTS MATCHED = " + matchingEvents.size());

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
                eventsSection.addView(linearLayout);
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
            eventsSection.addView(linearLayout);
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
