package com.mobdev.sam.apprenticeapp.fragments.profile;

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
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.List;

/**
 * Created by Sam on 02/07/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private String title;
    private Profile userProfile;
    private Profile profile;
    private boolean owner;
    private boolean isContact;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button addSkillButton;
    private Button addInterestButton;
    private Button capgeminiInfoButton;
    private Button saveButton;
    private Button addContactButton;
    private Button removeContactButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        profile = dbHelper.getProfile(id);

        if (!owner) {
            List<Contact> contacts = dbHelper.getAllContactsForProfile(userProfile.getId());
            if (contacts.size() < 1) {
                isContact = false;
            }
            for (Contact contact : contacts) {
                if (contact.getContactId().equals(profile.getId())) {
                    isContact = true;
                    break;
                }
                else
                    isContact = false;
            }
        }
        else {
            isContact = false;
        }



        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillsInterestsLayout = myView.findViewById(R.id.skillsInterestsSection);
        final LinearLayout skillsLayout = myView.findViewById(R.id.skillsSection);
        final LinearLayout skillsHeaderLayout = myView.findViewById(R.id.skillsHeaderSection);
        final LinearLayout currentSkillsLayout = myView.findViewById(R.id.currentSkillsSection);
        final LinearLayout interestsLayout = myView.findViewById(R.id.interestsSection);
        final LinearLayout interestsHeaderLayout = myView.findViewById(R.id.interestsHeaderSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);
        if (!owner)
            titleText.setInputType(InputType.TYPE_NULL);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);
        if (!owner)
            descriptionText.setInputType(InputType.TYPE_NULL);

        // ADD SKILL BUTTON
        addSkillButton = myView.findViewById(R.id.addSkillButton);
        if (!owner)
            addSkillButton.setVisibility(View.INVISIBLE);
        addSkillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
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
        for (Skill skill : profile.getSkills()) {
            TextView skillRow = new TextView(getContext());
            skillRow.setText(skill.getName());
            skillsLayout.addView(skillRow);
        }

        // ADD INTEREST BUTTON
        addInterestButton = myView.findViewById(R.id.addInterestButton);
        if (!owner)
            addInterestButton.setVisibility(View.INVISIBLE);
        addInterestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
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
        for (Skill interest : profile.getInterests()) {
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
                bundle.putSerializable("profile", profile);
                bundle.putBoolean("owner", owner);
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
        if (!owner)
            saveButton.setVisibility(View.INVISIBLE);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked
                profile.setName(titleText.getText().toString());
                profile.setDescription(descriptionText.getText().toString());

                dbHelper.updateProfile(profile);

            }
        });

        // ADD CONTACT BUTTON
        addContactButton = myView.findViewById(R.id.addContactButton);
        if (owner) {
            addContactButton.setVisibility(View.INVISIBLE);
        }
        else if (!owner && isContact) {
            addContactButton.setVisibility(View.INVISIBLE);
        }
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertContact(userProfile.getId(),profile.getId());
                addContactButton.setVisibility(View.INVISIBLE);
                removeContactButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Contact added!", Toast.LENGTH_LONG).show();
            }
        });



        // REMOVE CONTACT BUTTON
        removeContactButton = myView.findViewById(R.id.removeContactButton);
        if (owner) {
            removeContactButton.setVisibility(View.INVISIBLE);
        }
        else if (!owner && !isContact) {
            removeContactButton.setVisibility(View.INVISIBLE);
        }
        removeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(userProfile.getId(),profile.getId());
                removeContactButton.setVisibility(View.INVISIBLE);
                addContactButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Contact removed!", Toast.LENGTH_LONG).show();
            }
        });


        // Set all values to user's profile
        titleText.setText(profile.getName());
        descriptionText.setText(profile.getDescription());


        return myView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile)getArguments().getSerializable("userProfile");
            id = getArguments().getLong("userId");
            owner = getArguments().getBoolean("owner");
        }
        dbHelper = new DBHelper(getContext());
    }
}
