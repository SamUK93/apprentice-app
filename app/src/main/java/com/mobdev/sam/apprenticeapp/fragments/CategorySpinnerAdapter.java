package com.mobdev.sam.apprenticeapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobdev.sam.apprenticeapp.models.Category;

import java.util.List;

/**
 * Category Spinner Adapter, a custom spinner adapter for displaying skill categories in spinners
 */

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categories;

    public CategorySpinnerAdapter(Context context, int textViewResourceId,
                                  List<Category> categories) {
        super(context, textViewResourceId, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Category text view
        TextView category = new TextView(context);
        category.setTextColor(Color.BLACK);

        // Set the text view to the category name
        category.setText(categories.get(position).getName());

        return category;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // Category text view
        TextView category = new TextView(context);
        category.setTextColor(Color.BLACK);

        // Set the text view to the category name
        category.setText(categories.get(position).getName());

        return category;
    }
}
