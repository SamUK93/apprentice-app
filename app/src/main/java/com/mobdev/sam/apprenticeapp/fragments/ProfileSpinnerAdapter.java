package com.mobdev.sam.apprenticeapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.models.Profile;
import com.mobdev.sam.apprenticeapp.models.Skill;

import java.util.List;

/**
 * Profile Spinner Adapter, a custom spinner adapter for displaying profiles in spinners
 */

public class ProfileSpinnerAdapter extends ArrayAdapter<Profile> {
    private Context context;
    private List<Profile> profiles;

    public ProfileSpinnerAdapter(Context context, int textViewResourceId,
                                 List<Profile> profiles) {
        super(context, textViewResourceId, profiles);
        this.context = context;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Profile getItem(int position) {
        return profiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Profile Text View
        TextView profile = new TextView(context);
        profile.setTextColor(Color.BLACK);

        // Set the text view to the profile name
        profile.setText(profiles.get(position).getName());

        return profile;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // Profile Text View
        TextView profile = new TextView(context);
        profile.setTextColor(Color.BLACK);

        // Set the text view to the profile name
        profile.setText(profiles.get(position).getName());

        return profile;
    }
}
