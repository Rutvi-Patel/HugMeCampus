package com.diamondTierHuggers.hugMeCampus.matchmaking;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HugMeUser {

    public String mUid;
    public boolean[] gender;
    public boolean[] duration;
    public boolean[] mood;
    private int matchScore = 0;

    public HugMeUser() {
        //required for DataSnapshot.getValue(HugMeUser.class)
    }

    public HugMeUser(String mUid, boolean[] gender, boolean[] duration, boolean[] mood) {
        this.mUid = mUid;
        this.gender = gender;
        this.duration = duration;
        this.mood = mood;
    }

    public int getMatchScore() {
        return matchScore;
    }
}
