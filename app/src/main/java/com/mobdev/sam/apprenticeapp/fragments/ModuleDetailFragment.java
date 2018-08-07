package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

/**
 * Created by Sam on 02/07/2018.
 */

public class ModuleDetailFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private String title;
    private Profile userProfile;
    private Module module;
    private boolean isNew;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button viewAttendeesButton;
    private EditText dateText;
    private EditText locationText;
    private EditText goodForText;
    private EditText prerequisitesText;
    private Button addSkillButton;
    private Button attendButton;
    private Button shareButton;
    private Button saveButton;
    private Button cancelAttendanceButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_detail_layout, container, false);

        if (!isNew) {
            module = dbHelper.getModule(id);
        }

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile)getArguments().getSerializable("userProfile");
            id = getArguments().getLong("moduleId");
            isNew = getArguments().getBoolean("isNew");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Module Name (PLACEHOLDER)");

    }
}
