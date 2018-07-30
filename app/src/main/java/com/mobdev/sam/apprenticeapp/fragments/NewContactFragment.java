package com.mobdev.sam.apprenticeapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Contact;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.ProfileReason;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
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

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.new_contact_layout, container, false);

        contactsSection = myView.findViewById(R.id.contactsSection);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        // Get profiles with similar interests and skills to populate the 'Suggested Contacts' section
        List<ProfileReason> matchingProfiles = dbHelper.getAllProfilesAllCriteria(myProfile.getSkills(),myProfile.getInterests());

        // Get current contacts of user
        List<Contact> contacts = dbHelper.getAllContactsForProfile(myProfile.getId());
        List<Long> contactIds = new ArrayList<>();
        for (Contact contact : contacts) {
            contactIds.add(contact.getContactId());
        }

        // Remove current contacts from the matching profile list
        List<ProfileReason> profilesToRemove = new ArrayList<>();
        for (ProfileReason profile : matchingProfiles) {
            if (contactIds.contains(profile.profile.getId())) {
                profilesToRemove.add(profile);
            }
            // Remove users own profile from the list
            else if (myProfile.getId().equals(profile.profile.getId())) {
                profilesToRemove.add(profile);
            }
        }
        matchingProfiles.removeAll(profilesToRemove);

        // Add profiles to the view
        for (final ProfileReason profileReason : matchingProfiles) {
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
                    bundle.putSerializable("userId", profileReason.profile.getId());
                    bundle.putBoolean("owner", false);
                    // Create a new Search fragment
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
            TextView reasonRow = new TextView(getContext());

            nameRow.setText(profileReason.profile.getName());
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            reasonRow.setText(profileReason.reason);

            linearLayout.addView(nameRow);
            linearLayout.addView(reasonRow);
            contactsSection.addView(linearLayout);
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
