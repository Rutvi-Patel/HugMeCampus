package com.diamondTierHuggers.hugMeCampus.entity;

import java.util.List;

public class HugPreferences {

    public List<Boolean> gender; // [male, female]
    public List<Boolean> duration; // [short, medium, long]
    public List<Boolean> mood; // [talkative, quiet, emotional, celebratory, happy, sad]

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
