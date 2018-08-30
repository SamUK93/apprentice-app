package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.profile.ProfileFragment;
import com.mobdev.sam.apprenticeapp.fragments.ProfileSpinnerAdapter;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The 'Module Participants' fragment, which displays the users that are participating in a module
 * If the user is an admin, they can add and remove participants from this screen
 */

public class ModuleParticipantsFragment extends android.support.v4.app.Fragment {

    View myView;
    boolean isAdmin;

    private DBHelper dbHelper;
    private Profile myProfile;
    private Module module;
    private Spinner participantsSpinner;
    private Button addParticipantButton;

    final List<TextView> profiles = new ArrayList<>();
    final List<Button> removeButtons = new ArrayList<>();

    // UI Elements
    LinearLayout participantsSection;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_participants_layout, container, false);

        // ALL CURRENT PARTICIPANTS
        List<Profile> allProfilesModule = dbHelper.getAllProfilesForModule(module);

        participantsSection = myView.findViewById(R.id.participantsSection);

        // PARTICIPANT SPINNER
        participantsSpinner = myView.findViewById(R.id.participantsSpinner);
        if (!isAdmin) {
            participantsSpinner.setVisibility(View.GONE);
        }

        // Get all profiles
        List<Profile> allProfiles = dbHelper.getAllProfiles();

        // Remove the current participants from the list
        // Current participant IDs
        List<Long> participatingIds = new ArrayList<>();
        for (Profile profile : allProfilesModule) {
            participatingIds.add(profile.getId());
        }

        // List of IDs to remove
        List<Long> profilesToRemove = new ArrayList<>();
        for (Profile profile : allProfiles) {
            if (participatingIds.contains(profile.getId())) {
                profilesToRemove.add(profile.getId());
            }
        }

        List<Profile> nonParticipatingProfiles = new ArrayList<>();

        for (Profile profile : allProfiles) {
            // For each of the total list of profiles
            if (!profilesToRemove.contains(profile.getId())) {
                // If the profile is not currently participating in the module, add it to
                // the list of non participating profiles
                nonParticipatingProfiles.add(profile);
            }
        }

        // Populate the spinner with the list of non-participating profiles
        final ProfileSpinnerAdapter spinnerAdapter = new ProfileSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, nonParticipatingProfiles);
        participantsSpinner.setAdapter(spinnerAdapter);


        // ADD PARTICIPANT BUTTON
        addParticipantButton = myView.findViewById(R.id.addParticipantButton);
        if (!isAdmin) {
            addParticipantButton.setVisibility(View.GONE);
        }
        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add participant button clicked
                // Get the selected user from the spinner and add them as a new participant in the database
                // and update the view
                Profile profile = (Profile) participantsSpinner.getSelectedItem();
                dbHelper.insertParticipants(module.getModuleId(), new ArrayList<Long>(Arrays.asList(profile.getId())));
                addParticipant(profile);
                Toast.makeText(getActivity(), "Added participant - " + profile.getName(), Toast.LENGTH_LONG).show();
            }
        });


        if (allProfilesModule.size() > 0) {
            // Add profiles to the view
            for (final Profile profile : allProfilesModule) {
                addParticipant(profile);
            }
        }

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("userProfile");
            isAdmin = getArguments().getBoolean("isAdmin");
            module = (Module) getArguments().getSerializable("module");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Module Participants - " + module.getName());

    }

    @SuppressLint("NewApi")
    public void addParticipant(final Profile profile) {
        // Add a new layout
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(3, 3, 3, 15);
        linearLayout.setLayoutParams(params);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Profile tapped, so start a new 'Profile' fragment for that profile
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                bundle.putSerializable("userId", profile.getId());
                if (profile.getId().equals(myProfile.getId()))
                    bundle.putBoolean("owner", true);
                else
                    bundle.putBoolean("owner", false);
                // Create a new Profile fragment
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Profile fragment
                transaction.replace(R.id.content_frame, profileFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Add a new name TextView for the profile
        TextView nameRow = new TextView(getContext());

        if (isAdmin) {
            // If the user is an admin, add a remove button to the view
            final Button removeButton = new Button(getContext());
            removeButton.setText(R.string.remove_button_text);
            removeButtons.add(removeButton);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove button clicked
                    // Get the index of the remove button in the list of buttons
                    int i = removeButtons.indexOf(removeButton);

                    // Remove both the note and the remove button from the view
                    linearLayout.removeView(profiles.get(i));
                    linearLayout.removeView(removeButtons.get(i));
                    participantsSection.removeView(linearLayout);

                    // Remove both the note and remove button from their respective lists
                    profiles.remove(i);
                    removeButtons.remove(removeButton);

                    // Update the database to remove the participant
                    dbHelper.deleteSpecificParticipant(module.getModuleId(), profile.getId());
                }
            });
            linearLayout.addView(removeButton);
        }

        // Set the name TextView to the profile name
        nameRow.setText(profile.getName());
        nameRow.setTextSize(15);
        nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);

        // Add the profile to the profile list
        linearLayout.addView(nameRow);
        profiles.add(nameRow);

        // Add it all to the participants section
        participantsSection.addView(linearLayout);
    }
}
