package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 02/07/2018.
 */

public class AddSkillsInterestsFragment extends android.support.v4.app.Fragment {

    View myView;
    Profile profile;
    String type;

    // UI Elements
    private SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_remove_skills_layout, container, false);

        // LAYOUTS
        final LinearLayout containerLayout = myView.findViewById(R.id.container);
        final LinearLayout skillNameSection = myView.findViewById(R.id.skillNamesSection);
        final LinearLayout skillButtonSection = myView.findViewById(R.id.skillButtonsSection);

        // ADD CURRENT SKILLS AND BUTTONS
        final List<TextView> skills = new ArrayList<>();
        final List<Button> removeButtons = new ArrayList<>();

        if (type == "skills") {


            for (Skill skill : profile.getSkills()) {
                TextView skillNameRow = new TextView(getContext());
                skillNameRow.setText(skill.getName());
                skillNameSection.addView(skillNameRow);
                skills.add(skillNameRow);

                final Button removeButton = new Button(getContext());
                removeButton.setText(R.string.remove_button_text);
                skillButtonSection.addView(removeButton);
                removeButtons.add(removeButton);

                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Remove note button clicked

                        // Get the index of the remove button in the list of buttons
                        int i = removeButtons.indexOf(removeButton);

                        // Remove both the note and the remove button from the view
                        skillNameSection.removeView(skills.get(i));
                        skillButtonSection.removeView(removeButtons.get(i));

                        // Remove both the note and remove button from their respective lists
                        skills.remove(i);
                        removeButtons.remove(removeButton);
                    }
                });
            }
        }
        else if (type == "interests") {
                for (Skill interest : profile.getInterests()) {
                    TextView interestNameRow = new TextView(getContext());
                    interestNameRow.setText(interest.getName());
                    skillNameSection.addView(interestNameRow);
                    skills.add(interestNameRow);

                    final Button removeButton = new Button(getContext());
                    removeButton.setText(R.string.remove_button_text);
                    skillButtonSection.addView(removeButton);
                    removeButtons.add(removeButton);

                    removeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Remove note button clicked

                            // Get the index of the remove button in the list of buttons
                            int i = removeButtons.indexOf(removeButton);

                            // Remove both the note and the remove button from the view
                            skillNameSection.removeView(skills.get(i));
                            skillButtonSection.removeView(removeButtons.get(i));

                            // Remove both the note and remove button from their respective lists
                            skills.remove(i);
                            removeButtons.remove(removeButton);
                        }
                    });
                }
            }


            return myView;
        }

        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                Bundle params = getArguments();
                profile = (Profile) params.getSerializable("profile");
                type = params.getString("searchType");
            }

            // Set main title
            getActivity().setTitle("Notes Search");

        }
    }
