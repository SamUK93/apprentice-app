package com.mobdev.sam.apprenticeapp.fragments.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.DatePickerFragment;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sam on 02/07/2018.
 */

public class CapgeminiInfoFragment extends Fragment {

    View myView;
    private DBHelper dbHelper;
    private Profile userProfile;
    private Profile profile;
    private boolean owner;

    // UI Elements
    private EditText emailText;
    private EditText baseLocationText;
    private EditText gradeText;
    private EditText jobTitleText;
    //TODO: Change to datepicker?
    private TextView joinDateText;
    private Button setJoinDateButton;
    private Button saveButton;

    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.capgemini_info_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

        // EMAIL
        emailText = myView.findViewById(R.id.emailText);
        if (!owner)
            emailText.setInputType(InputType.TYPE_NULL);

        // BASE LOCATION
        baseLocationText = myView.findViewById(R.id.baseLocationText);
        if (!owner)
        baseLocationText.setInputType(InputType.TYPE_NULL);

        // GRADE
        gradeText = myView.findViewById(R.id.gradeText);
        if (!owner)
        gradeText.setInputType(InputType.TYPE_NULL);

        // JOB TITLE
        jobTitleText = myView.findViewById(R.id.jobTitleText);
        if (!owner)
        jobTitleText.setInputType(InputType.TYPE_NULL);

        // JOIN DATE
        joinDateText = myView.findViewById(R.id.joinDateText);
        if (!owner)
        joinDateText.setInputType(InputType.TYPE_NULL);

        // SET JOIN DATE BUTTON
        setJoinDateButton = myView.findViewById(R.id.joinDateButton);
        if (!owner)
            setJoinDateButton.setVisibility(View.GONE);
        setJoinDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Set join date button clicked
                showDatePickerDialog();
            }
        });

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.saveCapgeminiInfoButton);
        if (!owner)
            saveButton.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save button clicked
                profile.setEmail(emailText.getText().toString());
                profile.setBaseLocation(baseLocationText.getText().toString());
                profile.setGrade(Integer.parseInt(gradeText.getText().toString()));
                profile.setJobTitle(jobTitleText.getText().toString());
                profile.setJoinDate(joinDateText.getText().toString());

                dbHelper.updateProfile(profile);

                Toast.makeText(getActivity(), "Capgemini Info Saved Successfully!", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();

            }
        });


        // Set fields
        emailText.setText(profile.getEmail());
        baseLocationText.setText(profile.getBaseLocation());
        gradeText.setText(String.valueOf(profile.getGrade()));
        jobTitleText.setText(profile.getJobTitle());
        joinDateText.setText(profile.getJoinDate());


        return myView;
    }


    public void showDatePickerDialog() {
        Date date = null;
        try {
            date = dateFormat.parse(joinDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
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
            joinDateText.setText(day + "/" + (month+1) + "/" + year);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile)getArguments().getSerializable("userProfile");
            profile = (Profile)getArguments().getSerializable("profile");
            owner = getArguments().getBoolean("owner");
        }

        // Set main title
        getActivity().setTitle("Capgemini Info - " + profile.getName());

        dbHelper = new DBHelper(getContext());

    }
}
