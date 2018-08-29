package com.mobdev.sam.apprenticeapp.fragments.study;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.NotesSearchFragment;
import com.mobdev.sam.apprenticeapp.models.Deadline;
import com.mobdev.sam.apprenticeapp.models.Module;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sam on 02/07/2018.
 */

public class StudyFragment extends android.support.v4.app.Fragment {

    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    final List<TextView> deadlines = new ArrayList<>();
    final List<TextView> dates = new ArrayList<>();

    // UI Elements
    private TextView studyInfoBox; //TODO: Update this to card view?
    private Button myModulesButton;
    private Button notesButton;
    private LinearLayout upcomingDeadlinesSection;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.study_layout, container, false);
        myProfile = dbHelper.getProfile(id);

        upcomingDeadlinesSection = myView.findViewById(R.id.upcomingDeadlinesSection);

        List<Deadline> nearingDeadlines = new ArrayList<>();

        // Display upcoming deadlines for profile
        List<Deadline> deadlines = new ArrayList<>();
        List<Module> modules = dbHelper.getAllModulesForProfile(myProfile);
        for (Module module : modules) {
            deadlines.addAll(dbHelper.getAllDeadlinesForModule(module));
        }

        for (Deadline deadline : deadlines) {
            Date date = null;

            try {
                date = dateFormat.parse(deadline.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (calendar.get(Calendar.DAY_OF_YEAR) - Calendar.getInstance().get(Calendar.DAY_OF_YEAR) < 10) {
                addDeadline(deadline);
            }
        }


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
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                // Create a new Modules fragment
                Fragment modulesFragment = new ModulesFragment();
                modulesFragment.setArguments(bundle);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("userProfile", myProfile);
                Fragment modulesFragment = new NotesSearchFragment();
                modulesFragment.setArguments(bundle);
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


    @SuppressLint("NewApi")
    public void addDeadline(final Deadline deadline) {
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
                bundle.putBoolean("isNew", false);
                bundle.putBoolean("isAdmin", myProfile.getIsAdmin());
                bundle.putSerializable("module", dbHelper.getModule(deadline.getModuleId()));
                //bundle.putSerializable("module", module);
                bundle.putSerializable("deadline", deadline);
                // Create a new Profile fragment
                ModuleDeadlineDetailFragment deadlineDetailFragment = new ModuleDeadlineDetailFragment();
                deadlineDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.content_frame, deadlineDetailFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TextView nameRow = new TextView(getContext());
        TextView dateRow = new TextView(getContext());


        nameRow.setText(deadline.getName());
        nameRow.setTextSize(15);
        nameRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);

        dateRow.setText(deadline.getDate());
        dateRow.setTextSize(12);
        dateRow.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);



        linearLayout.addView(nameRow);
        linearLayout.addView(dateRow);
        deadlines.add(nameRow);
        dates.add(dateRow);


        upcomingDeadlinesSection.addView(linearLayout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("userId");
            myProfile = (Profile) getArguments().getSerializable("userProfile");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("STUDY");

    }
}
