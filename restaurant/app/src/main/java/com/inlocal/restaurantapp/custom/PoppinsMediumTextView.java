package com.inlocal.restaurantapp.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class PoppinsMediumTextView extends AppCompatTextView {
    public PoppinsMediumTextView(@NonNull Context context) {
        super(context);
    }

    public PoppinsMediumTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PoppinsMediumTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SFProText-Medium.ttf");
        super.setTypeface(tf);
    }

}
