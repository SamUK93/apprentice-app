package com.mobdev.sam.apprenticeapp.fragments;

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
 * Created by Sam on 13/07/2018.
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

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

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

            // Remove current events from the matching events list
            List<EventReason> eventsToRemove = new ArrayList<>();
            for (EventReason event : matchingEvents) {
                if (event.event.getCreatorId().equals(myProfile.getId())) {
                    eventsToRemove.add(event);
                }
                else if (eventIds.contains(event.event.getEventId())) {
                    eventsToRemove.add(event);
                    Log.i("EVENTMATCH::", "REMOVING ALREADY ATTENDING EVENT with ID " + event.event.getEventId());
                }
            }
            matchingEvents.removeAll(eventsToRemove);

            // Add events to the view
            for (final EventReason eventReason : matchingEvents) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(3, 3, 3, 15);
                linearLayout.setLayoutParams(params);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userProfile", myProfile);
                        bundle.putSerializable("eventId", eventReason.event.getEventId());
                        bundle.putBoolean("owner", false);
                        bundle.putBoolean("attending", false);
                        bundle.putBoolean("isNew", false);
                        // Create a new Event fragment
                        EventDetailFragment eventFragment = new EventDetailFragment();
                        eventFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace the current fragment with the new search fragment
                        transaction.replace(R.id.content_frame, eventFragment);
                        // Add transaction to the back stack and commit
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                TextView nameRow = new TextView(getContext());
                TextView reasonRow = new TextView(getContext());

                nameRow.setText(eventReason.event.getName());
                nameRow.setTextSize(15);
                nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
                reasonRow.setText(eventReason.reason);

                linearLayout.addView(nameRow);
                linearLayout.addView(reasonRow);
                eventsSection.addView(linearLayout);
            }
        }
        else {

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);
            TextView nameRow = new TextView(getContext());

            nameRow.setText("There are currently no upcoming events we think you'd be interested in! Please check back soon.");
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
        getActivity().setTitle("Contact Finder");

    }
}
