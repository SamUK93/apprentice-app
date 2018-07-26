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
 * Created by Sam on 25/07/2018.
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
    public int getCount(){
        return skills.size();
    }

    @Override
    public Skill getItem(int position){
        return skills.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(skills.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(skills.get(position).getName());

        return label;
    }
}
