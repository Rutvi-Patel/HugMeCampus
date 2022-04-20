package com.diamondTierHuggers.hugMeCampus.location;
import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentLocationItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocationData> mValues = new ArrayList<>();
    private OnItemListener mOnItemListener;
    private int position = 0;

    public LocationData getItem(int i) {
        return mValues.get(i);
    }

    public void addItem(LocationData l) {
        mValues.add(l);
        LocationAdapter.super.notifyDataSetChanged();
    }

//    public void readData() {
//        database.getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot loc: snapshot.getChildren()){
//                    System.out.println(loc.getValue());
//                    LocationData ld= new LocationData(loc.child("url").getValue().toString(),
//                            loc.child("name").getValue().toString(), loc.child("coord").getValue().toString());
//                    addItem(ld);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
        public void readData() {
            List<List<String>> location= Arrays.asList(Arrays.asList("Library", "coord", "img"),
                    Arrays.asList("Horn Center", "33.78329304963111,-118.1143422024218", "img"),
                    Arrays.asList("Pyramid", "33.78751955186969,-118.1143634232833", "img"),
                    Arrays.asList("Bookstore", "33.77990681600415,-118.11433539967797", "img"),
                    Arrays.asList("Recreation Center", "33.78540258998671,-118.10833434295633", "img"),
                    Arrays.asList("Peterson Hall", "33.7789103347117,-118.11208171591433", "img"),
                    Arrays.asList("Hall of Science", "33.7799348325781,-118.11248888707836", "img"),
                    Arrays.asList("Fine Arts", "33.77731816652847,-118.11244580242203", "img"),
                    Arrays.asList("Vivian Engineering Center", "33.782898394374676,-118.10996078158416", "img"),
                    Arrays.asList("Outpost", "33.78230542378748,-118.1100073096946", "img"),
                    Arrays.asList("University Student Union", "33.781350858495934,-118.11235321060803", "img")
                    );
            for(List<String> x: location){
                LocationData ld = new LocationData(appUser.getAppUser().getUid(),x.get(0),x.get(1), x.get(2));
                addItem(ld);
            }
        }


    public LocationAdapter(OnItemListener onItemListener){
        readData();
        System.out.println(mValues);
        mOnItemListener = onItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLocationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationData ld = mValues.get(position);
        holder.mlocationname.setText(ld.name);
//        holder.mlocationPic.setImageAlpha(ld.url);
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mlocationname;
        public LocationAdapter.OnItemListener onItemListener;
        ImageView mlocationPic;

        public ViewHolder(@NonNull FragmentLocationItemBinding binding, LocationAdapter.OnItemListener onItemListener) {
            super(binding.getRoot());
            this.onItemListener = onItemListener;
            this.mlocationname = binding.getRoot().findViewById(R.id.location_textView);
            this.mlocationPic = binding.getRoot().findViewById(R.id.location_image);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}