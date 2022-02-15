package com.diamondTierHuggers.hugMeCampus;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a list of Items.
 */
public class ListFriendFragment extends Fragment implements MyListItemRecyclerViewAdapter.OnItemListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyListItemRecyclerViewAdapter mMyListItemRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFriendFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListFriendFragment newInstance(int columnCount) {
        ListFriendFragment fragment = new ListFriendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mMyListItemRecyclerViewAdapter = new MyListItemRecyclerViewAdapter(this, true);
            recyclerView.setAdapter(mMyListItemRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onItemClick(int position) {
        // TODO create intent and view profile activity or transition to new fragment
        System.out.println(position);
//        Intent i = new Intent(this.getContext(), new DisplayUserProfile(mMyListItemRecyclerViewAdapter.getItem(position)).getClass());
//        startActivity(i);
        DisplayUserProfile displayUserProfile = new DisplayUserProfile(mMyListItemRecyclerViewAdapter.getItem(position));
//        NavHostFragment.findNavController(ListFriendFragment.this).nav(displayUserProfile);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tabs, displayUserProfile);
        fragmentTransaction.commit();


    }
}