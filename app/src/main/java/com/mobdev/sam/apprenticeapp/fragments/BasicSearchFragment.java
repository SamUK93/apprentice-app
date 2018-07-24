package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;

/**
 * Created by Sam on 02/07/2018.
 */

public class BasicSearchFragment extends Fragment {

    View myView;
    Profile profile;
    String searchType;

    // UI Elements
    private SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.basic_search_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

        // SEARCH BOX
        searchView = myView.findViewById(R.id.notesSearchBox);
        searchView.setQueryHint("Search " + searchType + "...");


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle params = getArguments();
            profile = (Profile)params.getSerializable("profile");
            searchType = params.getString("searchType");
        }

        // Set main title
        getActivity().setTitle("Notes Search");

    }
}
