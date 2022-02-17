package com.diamondTierHuggers.hugMeCampus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HugRating {
    public String desc;
    public String reviewer;
    public String reviewee;
    public int stars;

    public HugRating() {

    }

    public int getStars() {
        return stars;
    }

    public String getDesc() {
        return desc;
    }

    public String getReviewee() {
        return reviewee;
    }

    public String getReviewer() {
        return reviewer;
    }

}
