package com.mobdev.sam.apprenticeapp.fragments;

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

public class AdminFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private String title;
    private Profile userProfile;
    private Profile profile;
    private boolean owner;
    private boolean isContact;

    // UI Elements
    private Button createModuleButton;
    private Button viewModulesButton;
    private Button viewProfilesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.admin_layout, container, false);

        profile = dbHelper.getProfile(id);



        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);


        // CREATE MODULE BUTTON
        createModuleButton = myView.findViewById(R.id.createModuleButton);
        createModuleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", profile);
                // Create a new Module Detail fragment
                ModuleDetailFragment moduleDetailFragment = new ModuleDetailFragment();
                moduleDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new modules fragment
                transaction.replace(R.id.content_frame,moduleDetailFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
/*
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

*/
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
