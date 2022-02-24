package com.diamondTierHuggers.hugMeCampus.entity;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

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
    public HashMap<String, Boolean> request_list;
    public HashMap<String, Boolean> pending_list;

    private int requestPending = 0;
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

    public boolean isFriend() {
        return friend_list.containsKey(appUser.getAppUser().getUid());
    }

    public boolean isFriend(String uid) {
        return friend_list.containsKey(uid);
    }

    public int getRequestPending() {
        return requestPending;
    }

    public void setRequestPending(int requestPending) {
        this.requestPending = requestPending;
    }

    @Override
    public String toString() {
        return "HugMeUser{" +
                "uid='" + uid + '\'' +
                ", age=" + age +
                ", bio='" + bio + '\'' +
                ", first_name='" + first_name + '\'' +
                ", hug_count=" + hug_count +
                ", last_name='" + last_name + '\'' +
                ", hug_preferences=" + hug_preferences +
                ", pictures=" + pictures +
                ", gender=" + gender +
                ", rejected_list=" + rejected_list +
                ", accepted_list=" + accepted_list +
                ", blocked_list=" + blocked_list +
                ", friend_list=" + friend_list +
                ", matchScore=" + matchScore +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
