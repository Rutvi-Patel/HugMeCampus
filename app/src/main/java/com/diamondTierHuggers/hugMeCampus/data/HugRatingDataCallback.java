package com.diamondTierHuggers.hugMeCampus.data;

import com.diamondTierHuggers.hugMeCampus.entity.HugRating;

import java.util.ArrayList;

public interface HugRatingDataCallback {
    void GetHugRating(HugRating rating);
    void GetRatingList(ArrayList<HugRating> ratingsList);
}
