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
import com.mobdev.sam.apprenticeapp.fragments.profile.ProfileFragment;
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Event;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 13/07/2018.
 */

public class EventAttendeesFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;
    private Event event;

    // UI Elements
    LinearLayout attendeesSection;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.event_attendees_layout, container, false);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        attendeesSection = myView.findViewById(R.id.attendeesSection);

        // Get all of profiles attending the event
        List<Profile> profiles = dbHelper.getEventAttendees(event.getEventId());

        // Add contacts to the view
        for (final Profile profile : profiles) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userProfile", myProfile);
                    bundle.putSerializable("userId", profile.getId());
                    if (!profile.getId().equals(myProfile.getId()))
                        bundle.putBoolean("owner", false);
                    else
                        bundle.putBoolean("owner", true);
                    // Create a new Profile fragment
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace the current fragment with the new search fragment
                    transaction.replace(R.id.content_frame, profileFragment);
                    // Add transaction to the back stack and commit
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            TextView nameRow = new TextView(getContext());
            TextView descriptionRow = new TextView(getContext());

            nameRow.setText(profile.getName());
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            descriptionRow.setText(profile.getEmail());

            linearLayout.addView(nameRow);
            linearLayout.addView(descriptionRow);
            attendeesSection.addView(linearLayout);
        }

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile)getArguments().getSerializable("profile");
            event = (Event)getArguments().getSerializable("event");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("People");

    }
}
