package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobdev.sam.apprenticeapp.R;

/**
 * Created by Sam on 02/07/2018.
 */

public class CapgeminiInfoFragment extends Fragment {

    View myView;

    // UI Elements
    private EditText emailText;
    private EditText baseLocationText;
    private EditText gradeText;
    private EditText jobTitleText;
    //TODO: Change to datepicker?
    private EditText joinDateText;
    private Button saveButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.capgemini_info_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

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

        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.saveCapgeminiInfoButton);


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Set main title
        getActivity().setTitle("STUDY");

    }
}
