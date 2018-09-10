package com.mobdev.sam.apprenticeapp.models;

/**
 * Model for a day rating
 */

public class DayRating {

    // Fields
    private String date;
    private String positive;
    private String negative;
    private String additional;
    private Long rating;


    // Constructor
    public DayRating(String date, String positive, String negative, String additional, Long rating) {
        setDate(date);
        setPositive(positive);
        setNegative(negative);
        setAdditional(additional);
        setRating(rating);
    }


    // Get
    public String getDate() {
        return this.date;
    }

    public String getPositive() {
        return this.positive;
    }

    public String getNegative() {
        return this.negative;
    }

    public String getAdditional() {
        return this.additional;
    }

    public Long getRating() {
        return this.rating;
    }


    // Set
    public DayRating setDate(String date) {
        this.date = date;
        return this;
    }

    public DayRating setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public DayRating setNegative(String negative) {
        this.negative = negative;
        return this;
    }

    public DayRating setAdditional(String additional) {
        this.additional = additional;
        return this;
    }

    public DayRating setRating(Long rating) {
        this.rating = rating;
        return this;
    }
}
