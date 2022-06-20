package com.inlocal.restaurantapp.ui.restauruntinfo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.inlocal.restaurantapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private List<String> mImagesList;

    public SlidingImageAdapter(List<String> mImagesList) {
        this.mImagesList = mImagesList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImagesList.size();
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.slidingimages_layout, view, false);
        assert imageLayout != null;
        ImageView imageView = imageLayout.findViewById(R.id.image);
        Glide.with(view.getContext())
                .load((mImagesList.get(position) != null && !mImagesList.get(position).equalsIgnoreCase("")) ? mImagesList.get(position) : "")
                .placeholder(R.drawable.ic_restaurunt_rectangle)
                .into(imageView);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, @NotNull Object object) {
        return view.equals(object);
    }


}