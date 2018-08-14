package com.mobdev.sam.apprenticeapp.fragments.profile;

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
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.CategorySpinnerAdapter;
import com.mobdev.sam.apprenticeapp.fragments.SkillSpinnerAdapter;
import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Event;
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
    Event event;
    String type;
    int numSkillsRemoved = 0;
    int newSkillsNum = 0;
    Long newSkillCategoryId;
    Long filterCategoryId;
    Skill newExistingSkill;
    List<Skill> originalSkills = new ArrayList<>();
    final List<TextView> skills = new ArrayList<>();
    final List<Button> removeButtons = new ArrayList<>();

    // UI Elements
    private EditText newSkillText;
    private Spinner categorySpinner;
    private Button addSkillButton;
    private Spinner categoryFilterSpinner;
    private Spinner skillSpinner;
    private Button addExistingSkillButton;
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
                addSkill(skill, type);
            }
        } else if (type == "interests") {
            for (Skill interest : profile.getInterests()) {
                addSkill(interest, type);
            }
        } else if (type == "event") {
            for (Skill skill : event.getRelatedSkills()) {
                addSkill(skill,type);
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
                Log.i("EXISTING DEBUG INFO:: ", "CHANGED! 1");
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
                boolean alreadyHasSkill = false;
                boolean skillAlreadyExists = false;
                Skill existingSkill = null;

                // Add skill button clicked

                //TODO: MAKE THIS WORK FOR EVENTS!
                // Get a list of all the exiting unique skills
                List<Skill> allExistingSkills = dbHelper.getAllSkillsInterestsUnique();
                // Get the current skills and interests of the user or event
                List<Skill> existingSkills = new ArrayList<>();
                if (type == "skills" || type == "interests") {
                    existingSkills = dbHelper.getAllSkillsAndInterestsForProfile(profile.getId());
                }
                else if (type == "event") {
                    existingSkills = dbHelper.getAllSkillsForEvent(event.getEventId());
                }

                // Get the skill name
                String skillName = newSkillText.getText().toString();

                // Check if the user or event has the skill already
                for (Skill skill : existingSkills) {
                    if (skillName.toUpperCase().equals(skill.getName().toUpperCase())) {
                        alreadyHasSkill = true;
                        break;
                    }
                }

                // Check if the skill already exists in the database
                for (Skill skill : allExistingSkills) {
                    if (skillName.toUpperCase().equals(skill.getName().toUpperCase())) {
                        skillAlreadyExists = true;
                        existingSkill = skill;
                        if (type == "skills" || type == "interests") {
                            existingSkill.setEventId(null);
                            existingSkill.setProfileId(profile.getId());
                        } else if (type == "event") {
                            existingSkill.setProfileId(null);
                            existingSkill.setEventId(event.getEventId());
                        }

                        break;
                    }
                }

                // If the user doesn't already have the skill
                Skill skill = null;
                if (!alreadyHasSkill) {
                    if (type == "skills" || type == "interests") {
                        skill = new Skill(skillName, newSkillCategoryId, profile.getId(), null);
                    } else if (type == "event") {
                        skill = new Skill(skillName, newSkillCategoryId, null, event.getEventId());
                    }


                    if (skillAlreadyExists) {
                        // Skill already exists in database, so use that skill rather than creating a new one
                        Toast.makeText(getActivity(), "Skill with same name already exists, added with category - " + existingSkill.getCategoryId(), Toast.LENGTH_LONG).show();
                        skill = existingSkill;
                    }
                    newSkillsNum++;

                    if (type == "skills") {
                        // Add the skill to the profile and update database
                        profile.addSkill(skill);
                        dbHelper.updateSkillsProfile(profile.getId(), profile.getSkills());
                    }
                    else if (type == "interests") {
                        // Add the interest to the profile and update database
                        profile.addInterest(skill);
                        dbHelper.updateInterests(profile.getId(), profile.getInterests());
                    }
                    else if (type == "event") {
                        // Add the skill to the event and update the database
                        event.addRelatedSkill(skill);
                        dbHelper.updateSkillsEvent(event.getEventId(), event.getRelatedSkills());
                    }
                    // Add the skill to the UI
                    addSkill(skill, type);
                }
                else {
                    if (type == "event") {
                        Toast.makeText(getActivity(), "The event already has that related skill!", Toast.LENGTH_LONG).show();
                    } else if (type == "skills" || type == "interests") {
                        Toast.makeText(getActivity(), "You already have that skill or interest!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



        // NEW SKILL CATEGORY FILTER SPINNER
        categoryFilterSpinner = myView.findViewById(R.id.categoryFilterSpinner);

        //final CategorySpinnerAdapter categoryFilterSpinnerAdapter = new CategorySpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        categoryFilterSpinner.setAdapter(spinnerAdapter);
        //TODO: REMOVE
        /*if (visitedPlace.getAssociatedHolidayId() != null) {
            for (Holiday holiday : holidays) {
                if (holiday.getId().equals(visitedPlace.getAssociatedHolidayId())) {
                    associatedHoliday.setSelection(holidays.indexOf(holiday));
                }
            }*/

        categoryFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("EXISTING DEBUG INFO:: ", "CHANGED! 2");
                Category category = spinnerAdapter.getItem(i);
                filterCategoryId = category.getId();
                List<Skill> filteredSkills = dbHelper.getAllSkillsInterestsUniqueInCategory(filterCategoryId);
                final SkillSpinnerAdapter skillSpinnerAdapter = new SkillSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, filteredSkills);
                skillSpinner.setAdapter(skillSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // EXISTING SKILL SPINNER
        // Get all skills
        List<Skill> existingSkills = dbHelper.getAllSkillsInterestsUnique();

        skillSpinner = myView.findViewById(R.id.skillSpinner);

        final SkillSpinnerAdapter skillSpinnerAdapter = new SkillSpinnerAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, existingSkills);
        skillSpinner.setAdapter(skillSpinnerAdapter);
        //TODO: REMOVE
        /*if (visitedPlace.getAssociatedHolidayId() != null) {
            for (Holiday holiday : holidays) {
                if (holiday.getId().equals(visitedPlace.getAssociatedHolidayId())) {
                    associatedHoliday.setSelection(holidays.indexOf(holiday));
                }
            }*/

        skillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Skill skill = skillSpinnerAdapter.getItem(i);
                Log.i("EXISTING DEBUG INFO:: ", "CHANGED! 3");
                newExistingSkill = skill;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addExistingSkillButton = myView.findViewById(R.id.addExistingSkillButton);
        addExistingSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add skill button clicked
                boolean alreadyHasSkill = false;
                Skill skill = (Skill)skillSpinner.getSelectedItem();

                // Get the current skills and interests of the user
                List<Skill> existingSkills = new ArrayList<>();
                if (type == "event") {
                    existingSkills = dbHelper.getAllSkillsForEvent(event.getEventId());
                } else {
                    existingSkills = dbHelper.getAllSkillsAndInterestsForProfile(profile.getId());
                }


                // Check if the user has the skill already
                for (Skill existingSkill : existingSkills) {
                    Log.i("EXISTING DEBUG INFO:: ", "User skill - " + existingSkill.getName());
                    if (skill.getName().toUpperCase().equals(existingSkill.getName().toUpperCase())) {
                        alreadyHasSkill = true;
                        break;
                    }
                }

                // If the user doesn't already have the skill
                if(!alreadyHasSkill) {
                    newSkillsNum++;

                    if (type == "event") {
                        skill.setEventId(event.getEventId());
                        skill.setProfileId(null);
                    } else {
                        skill.setProfileId(profile.getId());
                        skill.setEventId(null);
                    }

                    if (type == "skills") {
                        // Add the skill to the profile and update database
                        profile.addSkill(skill);
                        dbHelper.updateSkillsProfile(profile.getId(), profile.getSkills());
                    }
                    else if (type == "interests") {
                        // Add the interest to the profile and update database
                        profile.addInterest(skill);
                        dbHelper.updateInterests(profile.getId(), profile.getInterests());
                    }
                    else if (type == "event") {
                        // Add the skill to the event and update database
                        event.addRelatedSkill(skill);
                        dbHelper.updateSkillsEvent(event.getEventId(), event.getRelatedSkills());
                    }

                    addSkill(skill, type);
                }
                else {
                    Toast.makeText(getActivity(), "You already have that skill or interest! - ", Toast.LENGTH_LONG).show();
                }
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
                    dbHelper.updateSkillsProfile(profile.getId(), originalSkills);
                } else if (type == "interests") {
                    profile.setAllInterests(originalSkills);
                    dbHelper.updateInterests(profile.getId(), originalSkills);
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
            event = (Event) params.getSerializable("event");
            type = params.getString("searchType");

            if (type == "skills") {
                originalSkills = profile.getSkills();
            } else if (type == "interests") {
                originalSkills = profile.getInterests();
            }
            else if (type == "event") {
                originalSkills = event.getRelatedSkills();
            }
        }

        // Set main title
        getActivity().setTitle("Notes Search");

        dbHelper = new DBHelper(getContext());
    }


    /**
     * Adds a new skill name and remove button to the view
     *
     * @param skill the skill to add
     * @param type  the type of skill (skill or interest)
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
                    dbHelper.updateSkillsProfile(profile.getId(), profile.getSkills());
                }
            });
        } else if (type == "interests") {
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
                    dbHelper.updateInterests(profile.getId(), profile.getInterests());
                }
            });
        } else if (type == "event") {
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

                    // Remove the interest from the profile
                    event.removeRelatedSkillByName(skills.get(i).getText().toString());

                    // Remove both the note and the remove button from the view
                    skillNameSection.removeView(skills.get(i));
                    skillButtonSection.removeView(removeButtons.get(i));

                    // Remove both the note and remove button from their respective lists
                    skills.remove(i);
                    removeButtons.remove(removeButton);

                    numSkillsRemoved++;
                    dbHelper.updateSkillsEvent(event.getEventId(), event.getRelatedSkills());
                }
            });
        }
    }
}
