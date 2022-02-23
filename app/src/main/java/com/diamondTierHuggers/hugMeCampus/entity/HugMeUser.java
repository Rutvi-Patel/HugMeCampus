package com.diamondTierHuggers.hugMeCampus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties
public class HugMeUser implements Serializable {

    private String uid;
    public int age;
    public String bio;
//    public boolean completed_setup;
//    public String email;
    public String first_name;
    public int hug_count;
//    public String hug_tier;
    public String last_name;
//    public boolean verified_email;
//    public String verified_email_date;
    public HugPreferences hug_preferences;
    public UserPictures pictures;
//    public boolean online;
    public int gender;
    public HashMap<String, Boolean> rejected_list;
    public HashMap<String, Boolean> accepted_list;
    public HashMap<String, Boolean> blocked_list;
    public HashMap<String, Boolean> friend_list;

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

    public String getUid() {
        return uid;
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
                ", first_name='" + first_name + '\'' +
                ", hug_count=" + hug_count +
                ", last_name='" + last_name + '\'' +
                ", hug_preferances=" + hug_preferences +
                ", pictures=" + pictures +
                ", matchScore=" + matchScore +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
