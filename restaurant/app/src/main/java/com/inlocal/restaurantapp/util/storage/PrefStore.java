package com.inlocal.restaurantapp.util.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.inlocal.restaurantapp.R;

public abstract class PrefStore<T> implements Store<T> {

    @NonNull
    protected SharedPreferences prefs;

    public PrefStore(@NonNull Context context) {
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

}
