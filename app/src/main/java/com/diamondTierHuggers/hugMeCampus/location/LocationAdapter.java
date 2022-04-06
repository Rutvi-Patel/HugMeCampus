package com.diamondTierHuggers.hugMeCampus.location;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentLocationItemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    public void readData() {
        database.getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot loc: snapshot.getChildren()){
                    System.out.println(loc.getValue());
                    LocationData ld= new LocationData(loc.child("url").getValue().toString(),
                            loc.child("name").getValue().toString(), loc.child("coord").getValue().toString());
                    addItem(ld);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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