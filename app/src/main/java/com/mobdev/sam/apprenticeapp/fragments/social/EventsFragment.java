package com.mobdev.sam.apprenticeapp.fragments.social;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.List;

/**
 * The 'Events' fragment, which displays the events the user is currently marked as attending, and the
 * events that were created by the user.
 * <p>
 * Also has buttons for finding new events and creating events.
 */

public class EventsFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    // UI Elements
    LinearLayout eventsSection;
    LinearLayout eventsAttendingSection;
    LinearLayout eventsCreatedSection;
    Button findNewEventsButton;
    Button createNewEventButton;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.events_layout, container, false);

        findNewEventsButton = myView.findViewById(R.id.findNewEventsButton);
        findNewEventsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Find new events button clicked, start new 'FindNewEvent' fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);
                // Create a new FindNewEvent fragment
                FindNewEventFragment findNewEventFragment = new FindNewEventFragment();
                findNewEventFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new FindNewEvent fragment
                transaction.replace(R.id.content_frame, findNewEventFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        createNewEventButton = myView.findViewById(R.id.createNewEventButton);
        createNewEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create new event button clicked, start new 'EventDetailFragment' with 'isNew' set
                // to true in the bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                bundle.putBoolean("owner", true);
                bundle.putBoolean("isNew", true);
                // Create a new EventDetailFragment
                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                eventDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new EventDetailFragment
                transaction.replace(R.id.content_frame, eventDetailFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        // Sections
        eventsSection = myView.findViewById(R.id.eventsSection);
        eventsAttendingSection = myView.findViewById(R.id.eventsAttendingSection);
        eventsCreatedSection = myView.findViewById(R.id.eventsCreatedSection);

        // Get all of events that the user is attending
        List<Event> attendingEvents = dbHelper.getAllEventsProfileAttending(myProfile.getId());

        // Add events to the view
        for (final Event event : attendingEvents) {
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
                    // Event clicked, so start a new 'Event Detail' fragment and pass it the event
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userProfile", myProfile);
                    bundle.putLong("eventId", event.getEventId());
                    bundle.putBoolean("owner", false);
                    bundle.putBoolean("isNew", false);
                    // Create a new Event Detail fragment
                    EventDetailFragment eventDetailFragment = new EventDetailFragment();
                    eventDetailFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace the current fragment with the new Event Detail fragment
                    transaction.replace(R.id.content_frame, eventDetailFragment);
                    // Add transaction to the back stack and commit
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            // Add TextViews for event name and description, and add all to the view
            TextView nameRow = new TextView(getContext());
            TextView dateRow = new TextView(getContext());

            nameRow.setText(event.getName());
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            dateRow.setText(event.getDate());

            linearLayout.addView(nameRow);
            linearLayout.addView(dateRow);

            // Add the event to the 'Events Attending' section
            eventsAttendingSection.addView(linearLayout);
        }


        // Get all of events created by the user
        List<Event> createdEvents = dbHelper.getAllEventsCreatedByUser(myProfile.getId());

        // Add events to the view
        for (final Event event : createdEvents) {
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
                    // Event clicked, so start a new 'Event Detail' fragment and pass it the event
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userProfile", myProfile);
                    bundle.putLong("eventId", event.getEventId());
                    bundle.putBoolean("owner", true);
                    bundle.putBoolean("isNew", false);
                    // Create a new Event Detail fragment
                    EventDetailFragment eventDetailFragment = new EventDetailFragment();
                    eventDetailFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace the current fragment with the new Event Detail fragment
                    transaction.replace(R.id.content_frame, eventDetailFragment);
                    // Add transaction to the back stack and commit
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            // Add TextViews for both event name and description
            TextView nameRow = new TextView(getContext());
            TextView dateRow = new TextView(getContext());

            nameRow.setText(event.getName());
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            dateRow.setText(event.getDate());

            linearLayout.addView(nameRow);
            linearLayout.addView(dateRow);

            // Add event to the 'Events Created' section
            eventsCreatedSection.addView(linearLayout);
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
        getActivity().setTitle("Events");

    }
}
