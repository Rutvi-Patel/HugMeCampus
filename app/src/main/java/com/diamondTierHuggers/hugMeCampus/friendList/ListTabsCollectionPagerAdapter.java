package com.diamondTierHuggers.hugMeCampus.friendList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class ListTabsCollectionPagerAdapter extends FragmentStatePagerAdapter {
    public ListTabsCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return (position == 0) ? new ListFriendFragment() : new ListFriendRequestFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "Friends" : "Friend Requests";
    }
}
