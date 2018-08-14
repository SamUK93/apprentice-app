package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.ProfileSpinnerAdapter;
import com.mobdev.sam.apprenticeapp.fragments.TimePickerFragment;
import com.mobdev.sam.apprenticeapp.fragments.profile.ProfileFragment;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sam on 13/07/2018.
 */

public class ModuleDeadlinesFragment extends android.support.v4.app.Fragment {

    View myView;
    boolean isAdmin;

    private DBHelper dbHelper;
    private Profile myProfile;
    private Module module;
    private Spinner participantsSpinner;
    private Button addParticipantButton;

    final List<TextView> deadlines = new ArrayList<>();
    final List<Button> removeButtons = new ArrayList<>();

    // UI Elements
    LinearLayout participantsSection;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_deadlines_layout, container, false);

        // ALL CURRENT DEADLINES
        List<Profile> allDeadlinesModule = dbHelper.getAllProfilesForModule(module);


        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();





        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("userProfile");
            isAdmin = getArguments().getBoolean("isAdmin");
            module = (Module) getArguments().getSerializable("module");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("Contact Finder");

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @SuppressLint("NewApi")
    public void addParticipant(final Profile profile) {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(3, 3, 3, 15);
        linearLayout.setLayoutParams(params);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                bundle.putSerializable("userId", profile.getId());
                if (profile.getId().equals(myProfile.getId()))
                    bundle.putBoolean("owner", true);
                else
                    bundle.putBoolean("owner", false);
                // Create a new Profile fragment
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.content_frame, profileFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TextView nameRow = new TextView(getContext());
        if (isAdmin) {
            final Button removeButton = new Button(getContext());
            removeButton.setText(R.string.remove_button_text);
            removeButtons.add(removeButton);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove button clicked
                    // Get the index of the remove button in the list of buttons
                    int i = removeButtons.indexOf(removeButton);

                    // Remove both the note and the remove button from the view
                    linearLayout.removeView(deadlines.get(i));
                    linearLayout.removeView(removeButtons.get(i));
                    participantsSection.removeView(linearLayout);

                    // Remove both the note and remove button from their respective lists
                    deadlines.remove(i);
                    removeButtons.remove(removeButton);
                    dbHelper.deleteSpecificParticipant(module.getModuleId(),profile.getId());
                }
            });
            linearLayout.addView(removeButton);
        }


        nameRow.setText(profile.getName());
        nameRow.setTextSize(15);
        nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);



        linearLayout.addView(nameRow);
        deadlines.add(nameRow);

        participantsSection.addView(linearLayout);
    }
}
