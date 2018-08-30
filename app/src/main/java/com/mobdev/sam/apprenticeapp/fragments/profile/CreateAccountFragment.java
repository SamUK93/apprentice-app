package com.mobdev.sam.apprenticeapp.fragments.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.DatePickerFragment;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The 'Create Account' fragment, that lets the user create a new account and assigns them an ID.
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
    private Button setJoinDateButton;
    private EditText joinDateText;
    private Button createAccountButton;

    // Date format
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy");


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

        // SET JOIN DATE BUTTON
        setJoinDateButton = myView.findViewById(R.id.joinDateButton);
        setJoinDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Set join date button clicked
                showDatePickerDialog();
            }
        });

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

                // Create a new profile object
                Profile profile = new Profile(name, description, new ArrayList<Skill>(), new ArrayList<Skill>(), email, baseLocation, grade, jobTitle, joinDate, null, null, null, false);

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


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        Calendar calendar = Calendar.getInstance();

        // Open a datepicker and pass the current join date values in as the defaults
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("year", calendar.get(Calendar.YEAR));
        newFragment.setArguments(args);
        newFragment.setCallBack(onDate);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // When date is set, set the 'Join Date' field to the new value
            joinDateText.setText(day + "/" + (month + 1) + "/" + year);
        }
    };


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
