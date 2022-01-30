package com.diamondTierHuggers.hugMeCampus.matchmaking;

import java.util.List;

public class HugPreferences {

    public List<Boolean> gender;
    public List<Boolean> duration;
    public List<Boolean> mood;

    public HugPreferences() {
    }

    @Override
    public String toString() {
        return "HugPreferences{" +
                "gender=" + gender +
                ", duration=" + duration +
                ", mood=" + mood +
                '}';
    }
}
