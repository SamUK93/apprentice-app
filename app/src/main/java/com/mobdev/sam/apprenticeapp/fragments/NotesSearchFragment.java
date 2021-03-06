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

/**
 * Created by Sam on 02/07/2018.
 */

public class NotesSearchFragment extends Fragment {

    View myView;

    // UI Elements
    private SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.notes_search_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout filterLayout = myView.findViewById(R.id.filterSection);

        // SEARCH BOX
        searchView = myView.findViewById(R.id.notesSearchBox);
        searchView.setQueryHint("Search notes...");


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Set main title
        getActivity().setTitle("Notes Search");

    }
}
