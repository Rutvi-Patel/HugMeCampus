package com.diamondTierHuggers.hugMeCampus.entity;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@IgnoreExtraProperties
public class HugMeUser implements Serializable {

    private String uid;
    public int age;
    public String token;
    public String bio;
    public String first_name;
    public String email;
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
    public HashMap<String, String> message_list;

    private int matchScore = 0;

    private final String[] genderArray = {"male", "female", "nonbinary"};

    public HugMeUser() {
    }

    public String getGenderString() {
        return genderArray[gender];
    }

    public void calculateMatchScore(HashMap<String, Boolean> appUserPreferences) {
        if (appUserPreferences.get(genderArray[this.gender]) && this.hug_preferences.get(genderArray[appUser.getAppUser().getGender()])) {
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

    public int getFriendRequestPending() {
        // 0 = friends
        // 1 = pending
        // 2 = request
        // 3 = other
        if (appUser.getAppUser().friend_list.containsKey(uid)) {
            return 0;
        }
        else if (appUser.getAppUser().pending_list.containsKey(uid)) {
            return 1;
        }
        else if (appUser.getAppUser().request_list.containsKey(uid)) {
            return 2;
        }
        else {
            return 3;
        }
    }


    public String getToken() {
        return token;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getBio() {
        return bio;
    }

    public String getEmail(){return email;}

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getHug_count() {
        return hug_count;
    }

    public void setHug_count(int hug_count) {
        this.hug_count = hug_count;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public HashMap<String, Boolean> getHug_preferences() {
        return hug_preferences;
    }

    public void setHug_preferences(HashMap<String, Boolean> hug_preferences) {
        this.hug_preferences = hug_preferences;
    }

    public UserPictures getPictures() {
        return pictures;
    }

    public void setPictures(UserPictures pictures) {
        this.pictures = pictures;
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

    public HashMap<String, Boolean> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(HashMap<String, Boolean> friend_list) {
        this.friend_list = friend_list;
    }

    public HashMap<String, Boolean> getRequest_list() {
        return request_list;
    }

    public void setRequest_list(HashMap<String, Boolean> request_list) {
        this.request_list = request_list;
    }

    public HashMap<String, Boolean> getPending_list() {
        return pending_list;
    }

    public void setPending_list(HashMap<String, Boolean> pending_list) {
        this.pending_list = pending_list;
    }

    public HashMap<String, String> getMessage_list() {
        return message_list;
    }



    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public String[] getGenderArray() {
        return genderArray;
    }

    @Override
    public String toString() {
        return "HugMeUser{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
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

}
