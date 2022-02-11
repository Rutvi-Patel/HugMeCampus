package com.diamondTierHuggers.hugMeCampus;

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
    public Fragment getItem(int i) {
//        Fragment fragment = new DemoObjectFragment();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
//        return fragment;
        return new ListItemFragment();
//        if (i == 0) {
            // then matches list so populate listItemFragment with matches
//        }
//        else {
//          // then friends list so populate listItemFragment with friends
//        }
//        return listTabsFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
