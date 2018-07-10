package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.activites.MainActivity;

/**
 * Created by Sam on 02/07/2018.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        // Hide fab
        //((MainActivity)getActivity()).hideFloatingActionButton();

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Set main title
        getActivity().setTitle("PROFILE");

    }
}
