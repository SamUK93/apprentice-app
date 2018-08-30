package com.mobdev.sam.apprenticeapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.models.Category;
import com.mobdev.sam.apprenticeapp.models.Skill;

import java.util.List;

/**
 * Skill Spinner Adapter, a custom spinner adapter for displaying skills in spinners
 */

public class SkillSpinnerAdapter extends ArrayAdapter<Skill> {
    private Context context;
    private List<Skill> skills;

    public SkillSpinnerAdapter(Context context, int textViewResourceId,
                               List<Skill> skills) {
        super(context, textViewResourceId, skills);
        this.context = context;
        this.skills = skills;
    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public Skill getItem(int position) {
        return skills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Skill text view
        TextView skill = new TextView(context);
        skill.setTextColor(Color.BLACK);

        // Set the text view to the skill name
        skill.setText(skills.get(position).getName());

        return skill;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // Skill text view
        TextView skill = new TextView(context);
        skill.setTextColor(Color.BLACK);

        // Set the text view to the skill name
        skill.setText(skills.get(position).getName());

        return skill;
    }
}
