package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;

/**
 * Created by Sam on 02/07/2018.
 */

public class GridDiaryFragment extends Fragment {

    View myView;

    // UI Elements
    private TextView title;
    private TextView wentWellLabel;
    private EditText wentWellText;
    private TextView notGoWellLabel;
    private EditText notGoWellText;
    private TextView additionalNotesLabel;
    private EditText additionalNotesText;
    private TextView rateDayLabel;
    private SeekBar rateDaySlider;
    private Button saveDayButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.notes_search_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);

        // TITLE
        title = myView.findViewById(R.id.gridDiaryTitle);

        // WHAT WENT WELL
        wentWellText = myView.findViewById(R.id.whatWentWellText);

        // WHAT DIDN'T GO WELL
        notGoWellText = myView.findViewById(R.id.notGoWellText);

        // ADDITIONAL NOTES
        additionalNotesText = myView.findViewById(R.id.gridDiaryAdditionalNotesText);

        // RATE DAY SLIDER
        rateDaySlider = myView.findViewById(R.id.rateDaySlider);

        // SAVE DAY BUTTON
        saveDayButton = myView.findViewById(R.id.saveDayButton);


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
