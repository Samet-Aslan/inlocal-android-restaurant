package com.inlocal.restaurantapp.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlideCircleImageView extends CircleImageView {

    public GlideCircleImageView(Context context) {
        this(context, null);
    }

    public GlideCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null)
            return;
        try {
            GlideApp.with(getContext())
                    .load(imageUrl)
                    .timeout(30000)
                    .error(R.drawable.profile)
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setImageFile(File file) {
        GlideApp.with(getContext())
                .load(file)
                .into(this);
    }


}
