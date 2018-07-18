package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.activites.MainActivity;

/**
 * Created by Sam on 02/07/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    View myView;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button capgeminiInfoButton;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillsInterestsLayout = myView.findViewById(R.id.skillsInterestsSection);
        final LinearLayout skillsLayout = myView.findViewById(R.id.skillsSection);
        final LinearLayout interestsLayout = myView.findViewById(R.id.interestsSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);

        // CAPGEMINI INFO BUTTON
        capgeminiInfoButton = myView.findViewById(R.id.capgeminiInfoButton);
        capgeminiInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create a new Capgemini Info fragment
                Fragment capgeminiInfoFragment = new CapgeminiInfoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new modules fragment
                transaction.replace(R.id.content_frame,capgeminiInfoFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.profileSaveButton);


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Set main title
        getActivity().setTitle("PROFILE");

    }
}
