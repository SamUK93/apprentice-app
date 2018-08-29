package com.mobdev.sam.apprenticeapp.fragments.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Deadline;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;

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
    private boolean isAdmin;

    // UI Elements
    private EditText titleText;
    private EditText descriptionText;
    private Button viewParticipantsButton;
    private Button viewDeadlinesButton;
    private Button viewNotesButton;
    private Button viewCalendarButton;
    private Button communityButton;
    private Button editTasksButton;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_detail_layout, container, false);

        if (!isNew) {
            module = dbHelper.getModule(id);
        }

        isAdmin = userProfile.getIsAdmin();

        // LAYOUTS
        final LinearLayout tasksLayout = myView.findViewById(R.id.tasksSection);

        // TITLE
        titleText = myView.findViewById(R.id.nameText);
        if (!isAdmin)
            titleText.setInputType(InputType.TYPE_NULL);


        // DESCRIPTION
        descriptionText = myView.findViewById(R.id.descriptionText);
        if (!isAdmin)
            titleText.setInputType(InputType.TYPE_NULL);


        // VIEW PARTICIPANTS BUTTON
        viewParticipantsButton = myView.findViewById(R.id.viewParticipantsButton);
        if (isNew) {
            viewParticipantsButton.setVisibility(View.GONE);
        }
        viewParticipantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", userProfile);
                bundle.putBoolean("isAdmin",isAdmin);
                bundle.putSerializable("module", module);
                // Create a new Search fragment
                ModuleParticipantsFragment moduleParticipantsFragment = new ModuleParticipantsFragment();
                moduleParticipantsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.content_frame, moduleParticipantsFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        // VIEW DEADLINES BUTTON
        viewDeadlinesButton = myView.findViewById(R.id.viewDeadlinesButton);
        if (isNew) {
            viewDeadlinesButton.setVisibility(View.GONE);
        }
        viewDeadlinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", userProfile);
                bundle.putBoolean("isAdmin",isAdmin);
                bundle.putSerializable("module", module);
                // Create a new Search fragment
                ModuleDeadlinesFragment moduleDeadlinesFragment = new ModuleDeadlinesFragment();
                moduleDeadlinesFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.content_frame, moduleDeadlinesFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        // VIEW NOTES BUTTON
        viewNotesButton = myView.findViewById(R.id.viewNotesButton);
        if (isNew) {
            viewNotesButton.setVisibility(View.GONE);
        }
        viewNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement view notes", Toast.LENGTH_LONG).show();
            }
        });


        // VIEW CALENDAR BUTTON
        viewCalendarButton = myView.findViewById(R.id.viewCalendarButton);
        if (isNew) {
            viewCalendarButton.setVisibility(View.GONE);
        }
        viewCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement view calendar", Toast.LENGTH_LONG).show();
            }
        });


        // COMMUNITY BUTTON
        communityButton = myView.findViewById(R.id.communityButton);
        if (isNew) {
            communityButton.setVisibility(View.GONE);
        }
        communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement community feature", Toast.LENGTH_LONG).show();
            }
        });


        // EDIT TASKS BUTTON
        editTasksButton = myView.findViewById(R.id.editTasksButton);
        if (isNew) {
            editTasksButton.setVisibility(View.GONE);
        }
        editTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement edit tasks", Toast.LENGTH_LONG).show();
            }
        });


        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.saveModuleButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save button clicked
                if (isNew) {
                    module = new Module(titleText.getText().toString(),
                            descriptionText.getText().toString(),
                            new ArrayList<Deadline>(),
                            new ArrayList<Long>());

                    dbHelper.insertModule(module);
                    Toast.makeText(getActivity(), "New Module Created Successfully!", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    module.setName(titleText.getText().toString());
                    module.setDescription(descriptionText.getText().toString());

                    dbHelper.updateModule(module);
                    Toast.makeText(getActivity(), "Module Saved Successfully!", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });

        // Set all values to the current module
        if (!isNew) {
            titleText.setText(module.getName());
            descriptionText.setText(module.getDescription());
        }

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userProfile = (Profile) getArguments().getSerializable("userProfile");
            Log.i("YOUR PROFILE", "YO PROFILE ID IS " + userProfile.getId());
            id = getArguments().getLong("moduleId");
            isNew = getArguments().getBoolean("isNew");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Module Name (PLACEHOLDER)");

    }
}
