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
    public String first_name;
    public int hug_count;
    public String last_name;
    public HashMap<String, Boolean> hug_preferences;
    public UserPictures pictures;
    public int gender;
    public HashMap<String, Boolean> rejected_list;
    public HashMap<String, Boolean> accepted_list;
    public HashMap<String, Boolean> blocked_list;
    public HashMap<String, Boolean> friend_list;
    public HashMap<String, Boolean> request_list;
    public HashMap<String, Boolean> pending_list;

    private int requestPending = 0;
    private int matchScore = 0;

    private final String[] genderArray = {"male", "female", "nonbinary"};

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

    public void calculateMatchScore(HashMap<String, Boolean> appUserPreferences) {
        if (appUserPreferences.get(genderArray[this.gender])) {
            matchScore += 5;
        }
        else {
            matchScore -= 2;
        }

        if (appUserPreferences.get("short") == hug_preferences.get("short")) {
            matchScore += 2;
        }
        if (appUserPreferences.get("medium") == hug_preferences.get("medium")) {
            matchScore += 2;
        }
        if (appUserPreferences.get("long") == hug_preferences.get("long")) {
            matchScore += 2;
        }

        if (appUserPreferences.get("celebratory") == hug_preferences.get("celebratory")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }
        if (appUserPreferences.get("emotional") == hug_preferences.get("emotional")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }
        if (appUserPreferences.get("happy") == hug_preferences.get("happy")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }
        if (appUserPreferences.get("quiet") == hug_preferences.get("quiet")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }
        if (appUserPreferences.get("sad") == hug_preferences.get("sad")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }
        if (appUserPreferences.get("talkative") == hug_preferences.get("talkative")) {
            matchScore += 2;
        }
        else {
            matchScore -= 1;
        }

    }

    public boolean isFriend() {
        return friend_list.containsKey(appUser.getAppUser().getUid());
    }

    public boolean isFriend(String uid) {
        return friend_list.containsKey(uid);
    }

    public int getRequestPending() {
        // 0 = friends
        // 1 = request
        // 2 = pending
        // 3 = search
        return requestPending;
    }

    public void setRequestPending(int requestPending) {
        // 0 = friends
        // 1 = request
        // 2 = pending
        // 3 = search
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
