package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.ProfileReason;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.List;

/**
 * Created by Sam on 13/07/2018.
 */

public class NewContactFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    // UI Elements
    LinearLayout contactsSection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.new_contact_layout, container, false);

        contactsSection = myView.findViewById(R.id.contactsSection);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        List<ProfileReason> matchingProfiles = dbHelper.getAllProfilesSameSkills(myProfile.getSkills());

        for (ProfileReason profileReason : matchingProfiles) {
            TextView nameRow = new TextView(getContext());
            TextView reasonRow = new TextView(getContext());
            nameRow.setText(profileReason.profile.getName());
            reasonRow.setText(profileReason.reason);

            contactsSection.addView(nameRow);
            contactsSection.addView(reasonRow);
        }




        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile)getArguments().getSerializable("profile");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Contact Finder");

    }
}
