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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.TimePickerFragment;
import com.mobdev.sam.apprenticeapp.models.Deadline;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 'Module Deadlines' fragment which displays the list of deadlines for a module
 */

public class ModuleDeadlinesFragment extends android.support.v4.app.Fragment {

    View myView;
    boolean isAdmin;

    private DBHelper dbHelper;
    private Profile myProfile;
    private Module module;
    private Button addDeadlineButton;

    final List<TextView> deadlines = new ArrayList<>();
    final List<TextView> dates = new ArrayList<>();
    final List<Button> removeButtons = new ArrayList<>();

    // UI Elements
    LinearLayout deadlinesSection;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.module_deadlines_layout, container, false);

        deadlinesSection = myView.findViewById(R.id.deadlinesSection);

        addDeadlineButton = myView.findViewById(R.id.addDeadlineButton);
        if (!isAdmin)
            addDeadlineButton.setVisibility(View.GONE);

        addDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add deadline button clicked, start new 'Module Deadline Detail' fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                bundle.putSerializable("module", module);
                // Set isNew to true in the bundle
                bundle.putBoolean("isNew", true);
                bundle.putBoolean("isAdmin", myProfile.getIsAdmin());
                // Create a new Module Deadline Detail fragment
                ModuleDeadlineDetailFragment moduleDeadlineDetailFragment = new ModuleDeadlineDetailFragment();
                moduleDeadlineDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Module Deadline Detail fragment
                transaction.replace(R.id.content_frame, moduleDeadlineDetailFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // ALL CURRENT DEADLINES
        // Get all deadlines for module
        List<Deadline> allDeadlinesModule = dbHelper.getAllDeadlinesForModule(module);

        for (Deadline deadline : allDeadlinesModule) {
            // Add all deadlines to the view
            addDeadline(deadline);
        }

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
        getActivity().setTitle(module.getName() + " - Deadlines");

    }

    @SuppressLint("NewApi")
    public void addDeadline(final Deadline deadline) {
        // Create a new layout for the deadline
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(3, 3, 3, 15);
        linearLayout.setLayoutParams(params);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deadline tapped, start a new Deadline Detail fragment for that deadline
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                bundle.putBoolean("isNew", false);
                bundle.putBoolean("isAdmin", isAdmin);
                bundle.putSerializable("module", module);
                bundle.putSerializable("deadline", deadline);
                // Create a new Deadline Detail fragment
                ModuleDeadlineDetailFragment deadlineDetailFragment = new ModuleDeadlineDetailFragment();
                deadlineDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new Deadline Detail fragment
                transaction.replace(R.id.content_frame, deadlineDetailFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Add new name and date TextViews
        TextView nameRow = new TextView(getContext());
        TextView dateRow = new TextView(getContext());
        if (isAdmin) {
            // If user is admin, add a remove button
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
                    deadlinesSection.removeView(linearLayout);

                    // Remove both the deadline and remove button from their respective lists
                    deadlines.remove(i);
                    dates.remove(i);
                    removeButtons.remove(removeButton);
                    dbHelper.deleteSpecificDeadline(module.getModuleId(), deadline.getDeadlineId());
                }
            });
            linearLayout.addView(removeButton);
        }

        // Set the name and dates to the values for the current deadline
        nameRow.setText(deadline.getName());
        nameRow.setTextSize(15);
        nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);

        dateRow.setText(deadline.getDate());
        dateRow.setTextSize(12);
        dateRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);

        linearLayout.addView(nameRow);
        linearLayout.addView(dateRow);

        // Add the name and date rows to their respective lists
        deadlines.add(nameRow);
        dates.add(dateRow);

        // Add it all to the view
        deadlinesSection.addView(linearLayout);
    }
}
