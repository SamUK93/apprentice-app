package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

/**
 * Created by Sam on 02/07/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private String title;
    private Profile myProfile;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button addSkillButton;
    private Button addInterestButton;
    private Button capgeminiInfoButton;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        myProfile = dbHelper.getProfile(id);



        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillsInterestsLayout = myView.findViewById(R.id.skillsInterestsSection);
        final LinearLayout skillsLayout = myView.findViewById(R.id.skillsSection);
        final LinearLayout skillsHeaderLayout = myView.findViewById(R.id.skillsHeaderSection);
        final LinearLayout currentSkillsLayour = myView.findViewById(R.id.currentSkillsSection);
        final LinearLayout interestsLayout = myView.findViewById(R.id.interestsSection);
        final LinearLayout interestsHeaderLayout = myView.findViewById(R.id.interestsHeaderSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);

        // ADD SKILL BUTTON
        addSkillButton = myView.findViewById(R.id.addSkillButton);
        addSkillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);
                bundle.putString("searchType", "skills");
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
        for (Skill skill : myProfile.getSkills()) {
            TextView skillRow = new TextView(getContext());
            skillRow.setText(skill.getName());
            skillsLayout.addView(skillRow);
        }

        // ADD INTEREST BUTTON
        addInterestButton = myView.findViewById(R.id.addInterestButton);
        addInterestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);
                bundle.putString("searchType", "interests");
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

        // INTERESTS
        for (Skill interest : myProfile.getInterests()) {
            TextView interestRow = new TextView(getContext());
            interestRow.setText(interest.getName());
            interestsLayout.addView(interestRow);
        }

        // CAPGEMINI INFO BUTTON
        capgeminiInfoButton = myView.findViewById(R.id.capgeminiInfoButton);
        capgeminiInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);
                // Create a new Capgemini Info fragment
                CapgeminiInfoFragment capgeminiInfoFragment = new CapgeminiInfoFragment();
                capgeminiInfoFragment.setArguments(bundle);
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
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked
                myProfile.setName(titleText.getText().toString());
                myProfile.setDescription(descriptionText.getText().toString());

                dbHelper.updateProfile(myProfile);

            }
        });



        // Set all values to user's profile
        titleText.setText(myProfile.getName());
        descriptionText.setText(myProfile.getDescription());


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("userId");
        }
        dbHelper = new DBHelper(getContext());
    }
}
