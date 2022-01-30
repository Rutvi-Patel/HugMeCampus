package com.diamondTierHuggers.hugMeCampus.matchmaking;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HugMeUser {

//    public String mUid;
    public int age;
    public String bio;
    public boolean completed_setup;
    public String email;
    public String first_name;
    public int hug_count;
    public String hug_tier;
    public String last_name;
    public boolean verified_email;
    public String verified_email_date;
    public HugPreferences hug_preferances;
    public UserPictures pictures;

    private int matchScore = 0;

    public HugMeUser() {
    }

//    public int getMatchScore() {
//        return matchScore;
//    }


    @Override
    public String toString() {
        return "HugMeUser{" +
                "age=" + age +
                ", bio='" + bio + '\'' +
                ", completed_setup=" + completed_setup +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", hug_count=" + hug_count +
                ", hug_tier='" + hug_tier + '\'' +
                ", last_name='" + last_name + '\'' +
                ", verified_email=" + verified_email +
                ", verified_email_date='" + verified_email_date + '\'' +
                ", preference=" + hug_preferances +
                ", pictures=" + pictures +
                ", matchScore=" + matchScore +
                '}';
    }
}
