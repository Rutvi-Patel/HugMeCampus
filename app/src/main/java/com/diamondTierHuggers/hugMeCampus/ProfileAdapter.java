package com.diamondTierHuggers.hugMeCampus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentDisplayUserProfileBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ItemCustomFixedSizeLayout3Binding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;

import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    private FragmentDisplayUserProfileBinding binding;
    private final Object mLock = new Object();
    private ArrayList<HugMeUser> mOriginalValues;

    private ArrayList<HugMeUser> mObjects;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"poop", "coal", "bronze", "silver", "gold", "platinum", "diamond"};
    private boolean mNotifyOnChange = true;

    public ProfileAdapter(Context context, ArrayList<HugMeUser> objects) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, parent, position);
    }

    public void add(HugMeUser object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            } else {
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void remove(int object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.remove(object);
            } else {
                mObjects.remove(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public HugMeUser getItem(int position) {
        return mObjects.get(position);
    }

    public int getPosition(HugMeUser item) {
        return mObjects.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View createViewFromResource(LayoutInflater inflater, ViewGroup parent, int position) {

        binding = FragmentDisplayUserProfileBinding.inflate(inflater, parent, false);
        View view = binding.getRoot();

        final HugMeUser item = getItem(position);

        ((TextView) view.findViewById(R.id.name)).setText(item.first_name + " " + item.last_name);
        ((TextView) view.findViewById(R.id.info)).setText(item.age + ", " + gender[item.gender] + ", " + emoji[item.hug_count/50]);
        ((TextView) view.findViewById(R.id.bio)).setText(item.bio);

        binding.carousel4.setCarouselListener((CarouselListener) (new CarouselListener() {
            @Override
            public ViewBinding onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
                return ItemCustomFixedSizeLayout3Binding.inflate(layoutInflater, parent, false);
            }

            @Override
            public void onBindViewHolder(ViewBinding binding, CarouselItem item, int position) {
                ItemCustomFixedSizeLayout3Binding currentBinding = (ItemCustomFixedSizeLayout3Binding) binding;
                currentBinding.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Utils.setImage(currentBinding.imageView, item, R.drawable.ic_wb_cloudy_with_padding);
            }

            @Override
            public void onClick(int position, CarouselItem carouselItem) {
                DefaultImpls.onClick(this, position, carouselItem);
            }

            @Override
            public void onLongClick(int position, CarouselItem carouselItem) {
                DefaultImpls.onLongClick(this, position, carouselItem);
            }
        }));

        // TODO set user pictures here
        List<CarouselItem> list = new ArrayList<>();
        list.add(
                new CarouselItem(
//                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.14.13%20PM.png?alt=media&token=ffc414fb-0524-404a-988a-61c5ede309f6"
                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.04.37%20PM.png?alt=media&token=d9303bfc-a962-4a6e-bd57-74b740a7bfd9"

//                        R.drawable.bryce_canyon
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.bryce_canyon
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.cathedral_rock
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.death_valley
                )
        );
        binding.carousel4.setData(list);

        binding.carousel4.setIndicator(binding.customIndicator);

        if (position == mObjects.size() - 1) {
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top));
        }

        return view;

    }

}
