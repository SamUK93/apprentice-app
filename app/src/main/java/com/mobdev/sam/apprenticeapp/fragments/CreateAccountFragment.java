package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

/**
 * Created by Sam on 02/07/2018.
 */

public class CreateAccountFragment extends android.support.v4.app.Fragment {

    View myView;
    private DBHelper dbHelper;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private EditText emailText;
    private EditText baseLocationText;
    private EditText gradeText;
    private EditText jobTitleText;
    private EditText joinDateText;
    private Button createAccountButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.create_account_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);

        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);

        // EMAIL
        emailText = myView.findViewById(R.id.emailText);

        // BASE LOCATION
        baseLocationText = myView.findViewById(R.id.baseLocationText);

        // GRADE
        gradeText = myView.findViewById(R.id.gradeText);

        // JOB TITLE
        jobTitleText = myView.findViewById(R.id.jobTitleText);

        // JOIN DATE
        joinDateText = myView.findViewById(R.id.joinDateText);

        // CREATE ACCOUNT
        createAccountButton = myView.findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create account button clicked

                // Get all fields
                String name = titleText.getText().toString();
                String description = descriptionText.getText().toString();
                String email = emailText.getText().toString();
                String baseLocation = baseLocationText.getText().toString();
                Integer grade = Integer.valueOf(gradeText.getText().toString());
                String jobTitle = jobTitleText.getText().toString();
                String joinDate = joinDateText.getText().toString();

                Profile profile = new Profile(name,description,null,null,email,baseLocation,grade,jobTitle,joinDate,null,null,null);

                // Add the new account to the database
                Long id = dbHelper.insertProfile(profile);

                // Success toast message
                Toast.makeText(getActivity(), "Account created! Your id is - " + id, Toast.LENGTH_LONG).show();

                // Pop the fragment to go back to the log in screen
                getFragmentManager().popBackStackImmediate();
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
        getActivity().setTitle("CREATE ACCOUNT");

        dbHelper = new DBHelper(getContext());

    }
}
