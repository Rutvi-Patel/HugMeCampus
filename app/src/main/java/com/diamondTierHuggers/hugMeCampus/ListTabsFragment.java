package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListTabsFragment extends Fragment {

        // When requested, this adapter returns a DemoObjectFragment,
        // representing an object in the collection.
        ListTabsCollectionPagerAdapter listTabsCollectionPagerAdapter;
        ViewPager viewPager;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_list_tabs, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            listTabsCollectionPagerAdapter = new ListTabsCollectionPagerAdapter(getChildFragmentManager());
            viewPager = view.findViewById(R.id.pager);
            viewPager.setAdapter(listTabsCollectionPagerAdapter);
        }
    }


