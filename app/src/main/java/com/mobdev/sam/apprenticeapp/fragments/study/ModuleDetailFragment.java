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
 * The 'Module Detail' fragment, which displays the details of a particular module, with buttons to
 * view participants, deadlines.
 * <p>
 * If the user is an admin, then they are able to edit these fields and make changes, including adding
 * participants to the module.
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
            // If this is not a new module, get the existing module from the database using the provided ID
            module = dbHelper.getModule(id);
        }

        // Set if the user is an admin
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
                // View participants button clicked, start a new 'Module Participants' fragment for
                // this module
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", userProfile);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putSerializable("module", module);
                // Create a new Module Participants fragment
                ModuleParticipantsFragment moduleParticipantsFragment = new ModuleParticipantsFragment();
                moduleParticipantsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Module Participants fragment
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
                // View deadlines button clicked, start a new 'Module Deadlines' fragment for this module
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", userProfile);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putSerializable("module", module);
                // Create a new Module Deadlines fragment
                ModuleDeadlinesFragment moduleDeadlinesFragment = new ModuleDeadlinesFragment();
                moduleDeadlinesFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Module Deadlines fragment
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
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(), "Feature not yet implemented", Toast.LENGTH_LONG).show();
            }
        });


        // SAVE BUTTON
        saveButton = myView.findViewById(R.id.saveModuleButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save button clicked
                if (isNew) {
                    // If this is a new module, add a new entry into the database
                    module = new Module(titleText.getText().toString(),
                            descriptionText.getText().toString(),
                            new ArrayList<Deadline>(),
                            new ArrayList<Long>());

                    dbHelper.insertModule(module);
                    Toast.makeText(getActivity(), "New Module Created Successfully!", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    // This not a new module, so update the existing entry in the database
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
        getActivity().setTitle("Module - " + module.getName());

    }
}
