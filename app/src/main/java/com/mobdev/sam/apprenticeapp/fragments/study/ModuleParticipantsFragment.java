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
 * Created by Sam on 13/07/2018.
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

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();


        // PARTICIPANT SPINNER
        participantsSpinner = myView.findViewById(R.id.participantsSpinner);
        if (!isAdmin) {
            participantsSpinner.setVisibility(View.INVISIBLE);
        }

        // Get all deadlines
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
            if (!profilesToRemove.contains(profile.getId())) {
                nonParticipatingProfiles.add(profile);
            }
        }

        final ProfileSpinnerAdapter spinnerAdapter = new ProfileSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, nonParticipatingProfiles);
        participantsSpinner.setAdapter(spinnerAdapter);
        //TODO: REMOVE
        /*if (visitedPlace.getAssociatedHolidayId() != null) {
            for (Holiday holiday : holidays) {
                if (holiday.getId().equals(visitedPlace.getAssociatedHolidayId())) {
                    associatedHoliday.setSelection(holidays.indexOf(holiday));
                }
            }*/

        participantsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("EXISTING DEBUG INFO:: ", "CHANGED! 1");
                Profile profile = spinnerAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // ADD PARTICIPANT BUTTON
        addParticipantButton = myView.findViewById(R.id.addParticipantButton);
        if (!isAdmin) {
            addParticipantButton.setVisibility(View.INVISIBLE);
        }
        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add participant button clicked
                // Get the selected user
                Profile profile = (Profile) participantsSpinner.getSelectedItem();
                dbHelper.insertParticipants(module.getModuleId(),new ArrayList<Long>(Arrays.asList(profile.getId())));
                addParticipant(profile);
                Toast.makeText(getActivity(), "Added participant - " + profile.getName(), Toast.LENGTH_LONG).show();
            }
        });



        if (allProfilesModule.size() > 0) {
            Log.i("MODULEMATCH::", "TOTAL PROFILES FOUND = " + allProfilesModule.size());
            // Add deadlines to the view
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
        getActivity().setTitle("Contact Finder");

    }

    @SuppressLint("NewApi")
    public void addParticipant(final Profile profile) {
        final LinearLayout linearLayout = new LinearLayout(getContext());
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
                bundle.putSerializable("userId", profile.getId());
                if (profile.getId().equals(myProfile.getId()))
                    bundle.putBoolean("owner", true);
                else
                    bundle.putBoolean("owner", false);
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
        if (isAdmin) {
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
                    dbHelper.deleteSpecificParticipant(module.getModuleId(),profile.getId());
                }
            });
            linearLayout.addView(removeButton);
        }


        nameRow.setText(profile.getName());
        nameRow.setTextSize(15);
        nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);



        linearLayout.addView(nameRow);
        profiles.add(nameRow);

        participantsSection.addView(linearLayout);
    }
}
