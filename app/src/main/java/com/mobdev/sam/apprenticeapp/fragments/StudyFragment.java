package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;

/**
 * Created by Sam on 02/07/2018.
 */

public class StudyFragment extends android.support.v4.app.Fragment {

    View myView;

    // UI Elements
    private TextView studyInfoBox; //TODO: Update this to card view?
    private Button myModulesButton;
    private Button notesButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.study_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout menuLayout = myView.findViewById(R.id.menuButtonsSection);

        // STUDY INFO BOX TODO update this to card view?
        studyInfoBox = myView.findViewById(R.id.studyInfoBoxText);

        // MY MODULES BUTTON
        myModulesButton = myView.findViewById(R.id.myModulesButton);
        myModulesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create a new Modules fragment
                Fragment modulesFragment = new ModulesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new modules fragment
                transaction.replace(R.id.content_frame,modulesFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // NOTES BUTTON
        notesButton = myView.findViewById(R.id.notesButton);
        notesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create a new Modules fragment
                Fragment modulesFragment = new NotesSearchFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new modules fragment
                transaction.replace(R.id.content_frame,modulesFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
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
        getActivity().setTitle("STUDY");

    }
}
