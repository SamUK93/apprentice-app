package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.DatePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.TimePickerFragment;
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

/**
 * Created by Sam on 13/07/2018.
 */

public class ModuleDeadlineDetailFragment extends android.support.v4.app.Fragment {

    View myView;
    boolean isNew;
    boolean isAdmin;
    private DBHelper dbHelper;
    private Profile myProfile;
    private Deadline deadline;
    private Module module;

    // UI Elements
    private EditText deadlineNameText;
    private Button setTimeButton;
    private Button setDateButton;
    private Button saveDeadlineButton;

    private TextView dateText;

    private TextView timeText;

    SimpleDateFormat dateFormatFull = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");
    SimpleDateFormat dateOnlyFormat = new SimpleDateFormat(
            "dd/MM/yyyy");
    SimpleDateFormat timeOnlyFormat = new SimpleDateFormat(
            "HH:mm");

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_deadline_detail_layout, container, false);

        deadlineNameText = myView.findViewById(R.id.deadlineNameText);
        if (!isAdmin)
            deadlineNameText.setInputType(InputType.TYPE_NULL);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();



        dateText = myView.findViewById(R.id.dateText);

        timeText = myView.findViewById(R.id.timeText);

        if (!isNew) {
            deadlineNameText.setText(deadline.getName());



            Date date = null;

            try {
                date = dateFormatFull.parse(deadline.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            dateText.setText(String.valueOf(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))));

            timeText.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(calendar.get(Calendar.MINUTE)));
        }


        setTimeButton = myView.findViewById(R.id.pickTimeButton);
        setDateButton = myView.findViewById(R.id.pickDateButton);
        saveDeadlineButton = myView.findViewById(R.id.saveDeadlineButton);

        if (isAdmin) {

            setTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTimePickerDialog();
                }
            });



            setDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog();
                }
            });


            saveDeadlineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = deadlineNameText.getText().toString();
                    String date = dateText.getText().toString();
                    String time = timeText.getText().toString();
                    String fullDate = date + " " + time;
                    Deadline newDeadline = new Deadline(name,fullDate,module.getModuleId());

                    if (isNew) {
                        dbHelper.insertDeadlines(module.getModuleId(),new ArrayList<>(Arrays.asList(newDeadline)));

                        Toast.makeText(getActivity(), "New Deadline Created Successfully!", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                    else {
                        newDeadline.setDeadlineId(deadline.getDeadlineId());
                        dbHelper.updateDeadline(newDeadline);
                    }
                }
            });
        }
        else {
            setTimeButton.setVisibility(View.GONE);
            setDateButton.setVisibility(View.GONE);
            saveDeadlineButton.setVisibility(View.GONE);
        }


        return myView;
    }

    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        Calendar calendar = Calendar.getInstance();
        Date date = null;

        if (isNew) {

        }
        else {
            try {
                date = timeOnlyFormat.parse(timeText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.setTime(date);
        }



        args.putInt("hours", calendar.get(Calendar.HOUR_OF_DAY));
        args.putInt("minutes", calendar.get(Calendar.MINUTE));
        newFragment.setArguments(args);
        newFragment.setCallBack(onTime);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            timeText.setText(hour + ":" + minute);
        }
    };


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        Calendar calendar = Calendar.getInstance();
        Date date = null;

        if (isNew) {

        }
        else {
            try {
                date = dateOnlyFormat.parse(dateText.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.setTime(date);
        }



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
            dateText.setText(day + "/" + (month+1) + "/" + year);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("userProfile");
            isNew = getArguments().getBoolean("isNew");
            isAdmin = getArguments().getBoolean("isAdmin");
            module = (Module) getArguments().getSerializable("module");
            deadline = (Deadline) getArguments().getSerializable("deadline");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        if (isNew) {
            getActivity().setTitle("New Deadline");
        }
        else {
            getActivity().setTitle("View Deadline");
        }


    }
}
