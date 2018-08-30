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
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * The 'People' fragment, which displays the user's current contacts, and has a button for finding
 * new contacts.
 */

public class PeopleFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    // UI Elements
    LinearLayout contactsSection;
    Button findNewContactsButton;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.people_layout, container, false);

        findNewContactsButton = myView.findViewById(R.id.findNewContactsButton);
        findNewContactsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Find New Contacts button clicked, so start a 'New Contact' fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);
                // Create a New Contact fragment
                NewContactFragment newContactFragment = new NewContactFragment();
                newContactFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the New Contact fragment
                transaction.replace(R.id.content_frame, newContactFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        contactsSection = myView.findViewById(R.id.contactsSection);

        // Get all of the users current contacts
        List<Contact> contacts = dbHelper.getAllContactsForProfile(myProfile.getId());
        List<Profile> contactProfiles = new ArrayList<>();
        for (Contact contact : contacts) {
            contactProfiles.add(dbHelper.getProfile(contact.getContactId()));
        }

        // Add contacts to the view
        for (final Profile contactProfile : contactProfiles) {
            // For each of the profiles, create a layout
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Profile clicked, so start a new Profile fragment for that profile
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userProfile", myProfile);
                    bundle.putSerializable("userId", contactProfile.getId());
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

            // Add name and description TextViews
            TextView nameRow = new TextView(getContext());
            TextView descriptionRow = new TextView(getContext());

            nameRow.setText(contactProfile.getName());
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            descriptionRow.setText(contactProfile.getEmail());

            linearLayout.addView(nameRow);
            linearLayout.addView(descriptionRow);

            // Add it all to the contacts section
            contactsSection.addView(linearLayout);
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
        getActivity().setTitle("People");

    }
}
