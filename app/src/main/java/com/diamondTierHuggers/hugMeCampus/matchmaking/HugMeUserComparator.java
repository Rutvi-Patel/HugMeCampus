package com.diamondTierHuggers.hugMeCampus.matchmaking;

import java.util.Comparator;

public class HugMeUserComparator implements Comparator<HugMeUser> {

    @Override
    public int compare(HugMeUser o1, HugMeUser o2) {
        if (o1.getMatchScore() < o2.getMatchScore())
            return 1;
        else if (o1.getMatchScore() > o2.getMatchScore())
            return -1;
        return 0;
    }
}
