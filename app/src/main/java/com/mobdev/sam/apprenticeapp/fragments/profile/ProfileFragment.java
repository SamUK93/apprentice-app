package com.mobdev.sam.apprenticeapp.fragments.profile;

import android.content.Intent;
import android.net.Uri;
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
 * The 'Profile' fragment. This displays a profile with their information, and links to view
 * their skills, interests and capgemini info.
 * <p>
 * If the profile is the logged in user's then they may edit the fields and save changes to the database.
 * <p>
 * If the profile is another users, then they can be added as a contact, or sent a message.
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
    private Button sendMessageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        // Check if this profile is a contact of the logged in user.
        if (!owner) {
            List<Contact> contacts = dbHelper.getAllContactsForProfile(userProfile.getId());
            if (contacts.size() < 1) {
                isContact = false;
            }
            for (Contact contact : contacts) {
                if (contact.getContactId().equals(profile.getId())) {
                    isContact = true;
                    break;
                } else
                    isContact = false;
            }
        } else {
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
            disableEditText(titleText);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);
        if (!owner)
            disableEditText(descriptionText);


        // ADD SKILL BUTTON
        addSkillButton = myView.findViewById(R.id.addSkillButton);
        if (!owner)
            addSkillButton.setVisibility(View.GONE);
        addSkillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Add skill button clicked, so launch a new 'AddSkillsInterests' fragment
                // with the 'skills' searchType.
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
                bundle.putString("searchType", "skills");
                // Create a new AddSkillsInterests fragment
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
            // For each skill, display it in the view.
            TextView skillRow = new TextView(getContext());
            skillRow.setText(skill.getName());
            skillsLayout.addView(skillRow);
        }

        // ADD INTEREST BUTTON
        addInterestButton = myView.findViewById(R.id.addInterestButton);
        if (!owner)
            addInterestButton.setVisibility(View.GONE);
        addInterestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Add interest button clicked, so launch a new 'AddSkillsInterests' fragment
                // with the 'interests' searchType.
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
                bundle.putString("searchType", "interests");
                // Create a new AddSkillsInterests fragment
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
            // For each interest on the profile, add it to the view
            TextView interestRow = new TextView(getContext());
            interestRow.setText(interest.getName());
            interestsLayout.addView(interestRow);
        }

        // CAPGEMINI INFO BUTTON
        capgeminiInfoButton = myView.findViewById(R.id.capgeminiInfoButton);
        capgeminiInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Capgemini Info button clicked
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
                bundle.putBoolean("owner", owner);
                // Create a new Capgemini Info fragment
                CapgeminiInfoFragment capgeminiInfoFragment = new CapgeminiInfoFragment();
                capgeminiInfoFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new CapgeminiInfo fragment
                transaction.replace(R.id.content_frame, capgeminiInfoFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.profileSaveButton);
        if (!owner)
            saveButton.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked
                profile.setName(titleText.getText().toString());
                profile.setDescription(descriptionText.getText().toString());

                // Update profile in the database
                dbHelper.updateProfile(profile);

                Toast.makeText(getActivity(), "Profile Saved Successfully!", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();
            }
        });

        // ADD CONTACT BUTTON
        addContactButton = myView.findViewById(R.id.addContactButton);

        // Hide button if the user is the profile owner, or already a contact
        if (owner) {
            addContactButton.setVisibility(View.GONE);
        } else if (!owner && isContact) {
            addContactButton.setVisibility(View.GONE);
        }
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add contact button clicked, update database and hide button
                dbHelper.insertContact(userProfile.getId(), profile.getId());
                addContactButton.setVisibility(View.GONE);

                // Display remove contact button
                removeContactButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Contact added!", Toast.LENGTH_LONG).show();
            }
        });


        // REMOVE CONTACT BUTTON
        removeContactButton = myView.findViewById(R.id.removeContactButton);

        // Hide button if user is the profile owner, or is not an existing contact
        if (owner) {
            removeContactButton.setVisibility(View.GONE);
        } else if (!owner && !isContact) {
            removeContactButton.setVisibility(View.GONE);
        }
        removeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(userProfile.getId(), profile.getId());
                removeContactButton.setVisibility(View.GONE);
                addContactButton.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Contact removed!", Toast.LENGTH_LONG).show();
            }
        });


        // SEND MESSAGE BUTTON
        sendMessageButton = myView.findViewById(R.id.sendMessageButton);
        if (owner) {
            sendMessageButton.setVisibility(View.GONE);
        }
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send message button clicked, so start new SENDTO action for emails
                // And populate with the profile's email address
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.fromParts("mailto", profile.getEmail(), null));
                startActivity(Intent.createChooser(intent, "Select the method to share"));
            }
        });


        // Set all fields to user's profile
        titleText.setText(profile.getName());
        descriptionText.setText(profile.getDescription());


        return myView;
    }


    private void disableEditText(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setSingleLine(false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile) getArguments().getSerializable("userProfile");
            id = getArguments().getLong("userId");
            owner = getArguments().getBoolean("owner");
        }
        dbHelper = new DBHelper(getContext());

        // Get the profile using the ID
        profile = dbHelper.getProfile(id);

        // Set main title
        getActivity().setTitle("Profile - " + profile.getName());
    }
}
