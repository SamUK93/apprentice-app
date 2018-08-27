package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.DatePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.TimePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.profile.ProfileFragment;
import com.mobdev.sam.apprenticeapp.models.Deadline;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sam on 13/07/2018.
 */

public class ModuleDeadlineDetailFragment extends android.support.v4.app.Fragment {

    View myView;
    boolean isNew;
    private DBHelper dbHelper;
    private Profile myProfile;
    private Deadline deadline;
    private Module module;

    // UI Elements
    private TextView deadlineNameText;
    private Button setTimeButton;
    private Button setDateButton;
    private Button saveDeadlineButton;

    private EditText yearText;
    private EditText monthText;
    private EditText dayText;

    private EditText hourText;
    private EditText minuteText;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_deadline_detail_layout, container, false);

        deadlineNameText = myView.findViewById(R.id.deadlineNameText);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        setTimeButton = myView.findViewById(R.id.pickTimeButton);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(myView);
            }
        });


        setDateButton = myView.findViewById(R.id.pickDateButton);
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(myView);
            }
        });

        yearText = myView.findViewById(R.id.yearText);
        monthText = myView.findViewById(R.id.monthText);
        dayText = myView.findViewById(R.id.dayText);

        hourText = myView.findViewById(R.id.hoursText);
        minuteText = myView.findViewById(R.id.minutesText);

        if (!isNew) {
            deadlineNameText.setText(deadline.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm");

            Date date = null;

            try {
                date = dateFormat.parse(deadline.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            yearText.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            //MONTHS START FROM 0 so add one!
            monthText.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
            dayText.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

            hourText.setText(String.valueOf(date.getHours()));
            minuteText.setText(String.valueOf(date.getMinutes()));
        }


        saveDeadlineButton = myView.findViewById(R.id.saveDeadlineButton);
        saveDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = deadlineNameText.getText().toString();
                String year = yearText.getText().toString();
                String month = monthText.getText().toString();
                String day = dayText.getText().toString();
                String hour = hourText.getText().toString();
                String minute = minuteText.getText().toString();
                String fullDate = day + "/" + month + "/" + year + " " + hour + ":" + minute;
                deadline = new Deadline(name,fullDate,module.getModuleId());

                if (isNew) {
                    dbHelper.insertDeadlines(module.getModuleId(),new ArrayList<>(Arrays.asList(deadline)));

                    Toast.makeText(getActivity(), "New Deadline Created Successfully!", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                }
                else {
                    dbHelper.updateDeadline(deadline);
                }
            }
        });

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("userProfile");
            isNew = getArguments().getBoolean("isNew");
            module = (Module) getArguments().getSerializable("module");
            deadline = (Deadline) getArguments().getSerializable("deadline");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Edit Deadline");

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
