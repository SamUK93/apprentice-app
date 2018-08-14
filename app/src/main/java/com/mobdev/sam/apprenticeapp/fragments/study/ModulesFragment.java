package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.List;

/**
 * Created by Sam on 13/07/2018.
 */

public class ModulesFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Profile myProfile;

    private LinearLayout currentModulesLayout;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.modules_layout, container, false);

        currentModulesLayout = myView.findViewById(R.id.currentModulesSection);

        // Get all modules from the database
        List<Module> allModules = dbHelper.getAllModulesForProfile(myProfile);
        Log.i("EVENTMATCH::", "TOTAL MODULES FOUND = " + allModules.size());
        if (allModules.size() > 0) {
            // Add modules to the view
            for (final Module module : allModules) {
                LinearLayout linearLayout = new LinearLayout(getContext());
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
                        bundle.putSerializable("moduleId", module.getModuleId());
                        bundle.putBoolean("isNew", false);
                        // Create a new Module Detail fragment
                        ModuleDetailFragment moduleDetailFragment = new ModuleDetailFragment();
                        moduleDetailFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace the current fragment with the new module detail fragment
                        transaction.replace(R.id.content_frame, moduleDetailFragment);
                        // Add transaction to the back stack and commit
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                TextView nameRow = new TextView(getContext());
                TextView descriptionRow = new TextView(getContext());

                nameRow.setText(module.getName());
                nameRow.setTextSize(15);
                nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
                descriptionRow.setText(module.getDescription());

                linearLayout.addView(nameRow);
                linearLayout.addView(descriptionRow);
                currentModulesLayout.addView(linearLayout);
            }
        }
        else {

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 15);
            linearLayout.setLayoutParams(params);
            TextView nameRow = new TextView(getContext());

            nameRow.setText("There are currently no modules to display! Add some or contact your tutor/manager if you think this is an issue.");
            nameRow.setTextSize(15);
            nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(nameRow);
            currentModulesLayout.addView(linearLayout);
        }

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myProfile = (Profile) getArguments().getSerializable("userProfile");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("My Modules");

    }
}
