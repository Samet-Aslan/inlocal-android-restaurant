package com.inlocal.restaurantapp.custom;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class GlideImageView extends AppCompatImageView {

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null)
            return;

        try {
            GlideApp.with(getContext())
                    .load(imageUrl)
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
    public void setImageUrlWithPlaceHolder(String imageUrl) {
        if (imageUrl == null)
            return;
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        circularProgressDrawable.start();
        try {
            GlideApp.with(getContext())
                    .load(imageUrl)
                    .placeholder(circularProgressDrawable)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            circularProgressDrawable.stop();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            circularProgressDrawable.stop();
                            return false;
                        }
                    }).timeout(60000)
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void setClipToOutline(boolean clipToOutline) {
        super.setClipToOutline(clipToOutline);
    }


    @BindingAdapter({"set_image_file", "link"})
    public static void coverImage(GlideImageView view, File imageUrl, String link) {
//        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(Constants.SHARED_GROUP, MODE_PRIVATE);
        try {
            if (imageUrl == null) {
                view.setImageUrl(link);
            } else {
                view.setImageFile(imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
