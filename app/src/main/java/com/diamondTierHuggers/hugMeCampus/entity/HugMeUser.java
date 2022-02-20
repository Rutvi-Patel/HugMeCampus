package com.diamondTierHuggers.hugMeCampus.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

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
    public HashMap<String, Boolean> rejected_list;
    public HashMap<String, Boolean> accepted_list;
    public HashMap<String, Boolean> blocked_list;

    private int matchScore = 0;

    public HugMeUser() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isCompleted_setup() {
        return completed_setup;
    }

    public void setCompleted_setup(boolean completed_setup) {
        this.completed_setup = completed_setup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHug_count() {
        return hug_count;
    }

    public void setHug_count(int hug_count) {
        this.hug_count = hug_count;
    }

    public String getHug_tier() {
        return hug_tier;
    }

    public void setHug_tier(String hug_tier) {
        this.hug_tier = hug_tier;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getVerified_email_date() {
        return verified_email_date;
    }

    public void setVerified_email_date(String verified_email_date) {
        this.verified_email_date = verified_email_date;
    }

    public HugPreferences getHug_preferences() {
        return hug_preferences;
    }

    public void setHug_preferences(HugPreferences hug_preferences) {
        this.hug_preferences = hug_preferences;
    }

    public UserPictures getPictures() {
        return pictures;
    }

    public void setPictures(UserPictures pictures) {
        this.pictures = pictures;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public HashMap<String, Boolean> getRejected_list() {
        return rejected_list;
    }

    public void setRejected_list(HashMap<String, Boolean> rejected_list) {
        this.rejected_list = rejected_list;
    }

    public HashMap<String, Boolean> getAccepted_list() {
        return accepted_list;
    }

    public void setAccepted_list(HashMap<String, Boolean> accepted_list) {
        this.accepted_list = accepted_list;
    }

    public HashMap<String, Boolean> getBlocked_list() {
        return blocked_list;
    }

    public void setBlocked_list(HashMap<String, Boolean> blocked_list) {
        this.blocked_list = blocked_list;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("first_name", first_name);
        result.put("last_name", last_name);

        return result;
    }


}
