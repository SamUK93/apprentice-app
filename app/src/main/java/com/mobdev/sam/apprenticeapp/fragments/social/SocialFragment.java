package com.mobdev.sam.apprenticeapp.fragments.social;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

/**
 * The Social fragment, which allows the user to switch between 'People' and 'Events' sections,
 * which will display those respective fragments in the sub content frame.
 */

public class SocialFragment extends android.support.v4.app.Fragment {

    View myView;

    private DBHelper dbHelper;
    private Long id;
    private Profile myProfile;

    // UI Elements
    TabLayout tabLayout;
    TabItem peopleTab;
    TabItem eventsTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.social_layout, container, false);

        myProfile = dbHelper.getProfile(id);


        // INITIAL VIEW SET UP (PEOPLE TAB)
        PeopleFragment initialFragment = new PeopleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("profile", myProfile);
        initialFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace the current sub-content fragment with the People fragment
        transaction.replace(R.id.sub_content_frame, initialFragment);
        // Add transaction to the back stack and commit
        transaction.addToBackStack(null);
        transaction.commit();


        // TAB LAYOUT
        tabLayout = myView.findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment displayedFragment = null;
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", myProfile);

                // Change the displayed sub-content fragment based on which position the tab switcher
                // is in. First position for People Fragment, second for Events Fragment.
                switch (tab.getPosition()) {
                    case 0:
                        displayedFragment = new PeopleFragment();
                        break;
                    case 1:
                        displayedFragment = new EventsFragment();
                        break;
                }

                displayedFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new search fragment
                transaction.replace(R.id.sub_content_frame, displayedFragment);
                // Add transaction to the back stack and commit
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // PEOPLE TAB
        peopleTab = myView.findViewById(R.id.peopleTab);


        // EVENTS TAB
        eventsTab = myView.findViewById(R.id.eventsTab);

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("userId");
        }
        dbHelper = new DBHelper(getContext());

        // Set main title
        getActivity().setTitle("SOCIAL");

    }
}
