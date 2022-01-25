package com.diamondTierHuggers.hugMeCampus.matchmaking;

import java.util.PriorityQueue;


public class MatchMakingQueue {

    private PriorityQueue<HugMeUser> mQueue = new PriorityQueue<HugMeUser>(20, new HugMeUserComparator());


}
