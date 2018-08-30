package com.mobdev.sam.apprenticeapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * The Date Picker Fragment, which displays a date picker dialog for the user to select a date
 */
public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;
    private int day, month, year;

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        day = args.getInt("day");
        month = args.getInt("month");
        year = args.getInt("year");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
    }
}