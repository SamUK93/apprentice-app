package com.mobdev.sam.apprenticeapp.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.activites.MainActivity;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

/**
 * Created by Sam on 02/07/2018.
 */

public class LogInFragment extends android.support.v4.app.Fragment {

    View myView;
    private DBHelper dbHelper;
    private Profile profile;

    // UI Elements
    private EditText idText;
    private Button logInButton;
    private Button createAccountButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.log_in_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

        // ID TEXT INPUT
        idText = myView.findViewById(R.id.idText);

        // LOG IN
        logInButton = myView.findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Log in button clicked

                // Get the entered ID and use it to get a profile from the database
                Long id = Long.valueOf(idText.getText().toString());
                profile = dbHelper.getProfile(id);
                if (profile == null) {
                    Toast.makeText(getActivity(), "ID not recognised!", Toast.LENGTH_LONG).show();
                }
                else {
                    profile.setAllSkills(dbHelper.getAllSkillsForProfile(profile.getId()));
                    profile.setAllInterests(dbHelper.getAllInterestsForProfile(profile.getId()));
                    // Start a new main activity and pass the new profile
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("profile",profile);

                    Toast.makeText(getActivity(), "Hi " + profile.getName() + "!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }


            }
        });

        // CREATE ACCOUNT
        createAccountButton = myView.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create account button clicked
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace the current fragment with a new Create Account fragment
                transaction.replace(R.id.content_frame,new CreateAccountFragment());
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Set main title
        getActivity().setTitle("Apprentice App - LOG IN");

        dbHelper = new DBHelper(getContext());

    }
}
