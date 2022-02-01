package com.diamondTierHuggers.hugMeCampus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HugMeUser {

    private String uid;
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
    public HugPreferences hug_preferences;
    public UserPictures pictures;
    public boolean online;
    public int gender;

    private int matchScore = 0;

    public HugMeUser() {
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }



    public int getMatchScore() {
        return matchScore;
    }

    public void calculateMatchScore(HugPreferences appUserPreferences) {
        if (appUserPreferences.gender.get(this.gender)) {
            matchScore += 5;
        }
        else {
            matchScore -= 2;
        }

        for (int i = 0; i < appUserPreferences.duration.size(); i++) {
            if (this.hug_preferences.duration.get(i) == appUserPreferences.duration.get(i) && appUserPreferences.duration.get(i) == true) {
                matchScore += 2;
            }
        }

        for (int i = 0; i < appUserPreferences.mood.size(); i++) {
            if (this.hug_preferences.mood.get(i) == appUserPreferences.mood.get(i) && appUserPreferences.mood.get(i) == true) {
                matchScore += 3;
            }
            else if (this.hug_preferences.mood.get(i) != appUserPreferences.mood.get(i)) {
                matchScore -= 1;
            }
        }
    }


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
                ", hug_preferances=" + hug_preferences +
                ", pictures=" + pictures +
                ", online=" + online +
                ", matchScore=" + matchScore +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
