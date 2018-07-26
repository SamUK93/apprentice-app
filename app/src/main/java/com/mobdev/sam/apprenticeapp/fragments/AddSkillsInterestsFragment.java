package com.mobdev.sam.apprenticeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;
import com.mobdev.sam.apprenticeapp.tools.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 02/07/2018.
 */

public class AddSkillsInterestsFragment extends android.support.v4.app.Fragment {

    View myView;
    DBHelper dbHelper;
    Profile profile;
    String type;
    int numSkillsRemoved = 0;
    int newSkillsNum = 0;
    Long newSkillCategoryId;
    List<Skill> originalSkills = new ArrayList<>();
    final List<TextView> skills = new ArrayList<>();
    final List<Button> removeButtons = new ArrayList<>();

    // UI Elements
    private EditText newSkillText;
    private Spinner categorySpinner;
    private Button addSkillButton;
    private Button saveButton;
    private Button cancelButton;

    LinearLayout containerLayout;
    LinearLayout skillNameSection;
    LinearLayout skillButtonSection;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_remove_skills_layout, container, false);

        // LAYOUTS
        containerLayout = myView.findViewById(R.id.container);
        skillNameSection = myView.findViewById(R.id.skillNamesSection);
        skillButtonSection = myView.findViewById(R.id.skillButtonsSection);


        // ADD CURRENT SKILLS AND BUTTONS
        if (type == "skills") {
            for (Skill skill : profile.getSkills()) {
                addSkill(skill,type);
            }
        } else if (type == "interests") {
            for (Skill interest : profile.getInterests()) {
                addSkill(interest,type);
            }
        }


        ///////////////////////
        // NEW SKILL SECTION //
        ///////////////////////

        // NEW SKILL TEXT INPUT
        newSkillText = myView.findViewById(R.id.newSkillText);


        // CATEGORY SPINNER
        categorySpinner = myView.findViewById(R.id.categorySpinner);

        // Get all categories
        List<Category> categories = dbHelper.getAllCategories();

        final CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(spinnerAdapter);
        //TODO: REMOVE
        /*if (visitedPlace.getAssociatedHolidayId() != null) {
            for (Holiday holiday : holidays) {
                if (holiday.getId().equals(visitedPlace.getAssociatedHolidayId())) {
                    associatedHoliday.setSelection(holidays.indexOf(holiday));
                }
            }*/

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = spinnerAdapter.getItem(i);
                newSkillCategoryId = category.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addSkillButton = myView.findViewById(R.id.addSkillButton);
        addSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add skill button clicked
                newSkillsNum++;
                String skillName = newSkillText.getText().toString();
                Skill skill = new Skill(skillName,newSkillCategoryId,profile.getId());
                profile.addSkill(skill);
                dbHelper.updateSkills(profile.getId(),profile.getSkills());
                addSkill(skill,type);
            }
        });



        ///////////////////////////
        // SAVE / CANCEL BUTTONS //
        ///////////////////////////

        //TODO: FIX THIS AS NOT CURRENTLY WORKING (originalSkills seem to change as new skills)
        saveButton = myView.findViewById(R.id.saveNewSkillsButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save button clicked
                getFragmentManager().popBackStackImmediate();
            }
        });


        cancelButton = myView.findViewById(R.id.cancelNewSkillsButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel button clicked
                if (type == "skills") {
                    for (Skill s : originalSkills) {
                        Log.i("ORIGINAL SKILLS:: ", s.getName());
                    }
                    for (Skill s : profile.getSkills()) {
                        Log.i("NEW SKILLS:: ", s.getName());
                    }
                    profile.setAllSkills(originalSkills);
                    dbHelper.updateSkills(profile.getId(),originalSkills);
                }
                else if (type == "interests") {
                    profile.setAllInterests(originalSkills);
                    dbHelper.updateInterests(profile.getId(),originalSkills);
                }

                getFragmentManager().popBackStackImmediate();
            }
        });


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle params = getArguments();
            profile = (Profile) params.getSerializable("profile");
            type = params.getString("searchType");

            if (type == "skills") {
                originalSkills = profile.getSkills();
            }
            else if (type == "interests") {
                originalSkills = profile.getInterests();
            }
        }

        // Set main title
        getActivity().setTitle("Notes Search");

        dbHelper = new DBHelper(getContext());
    }


    /**
     * Adds a new skill name and remove button to the view
     * @param skill the skill to add
     * @param type the type of skill (skill or interest)
     */
    private void addSkill(Skill skill, String type) {
        if (type == "skills") {

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

                    // Remove the skill from the profile
                    profile.removeSkillByName(skills.get(i).getText().toString());

                    // Remove both the note and the remove button from the view
                    skillNameSection.removeView(skills.get(i));
                    skillButtonSection.removeView(removeButtons.get(i));

                    // Remove both the note and remove button from their respective lists
                    skills.remove(i);
                    removeButtons.remove(removeButton);

                    numSkillsRemoved++;
                    dbHelper.updateSkills(profile.getId(), profile.getSkills());
                }
            });
        }
        else if (type == "interests") {
            TextView interestNameRow = new TextView(getContext());
            interestNameRow.setText(skill.getName());
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

                    // Remove the interest from the profile
                    profile.removeInterestByName(skills.get(i).getText().toString());

                    // Remove both the note and the remove button from the view
                    skillNameSection.removeView(skills.get(i));
                    skillButtonSection.removeView(removeButtons.get(i));

                    // Remove both the note and remove button from their respective lists
                    skills.remove(i);
                    removeButtons.remove(removeButton);

                    numSkillsRemoved++;
                    dbHelper.updateInterests(profile.getId(),profile.getInterests());
                }
            });
        }
    }
}
