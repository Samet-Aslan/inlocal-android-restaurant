package com.inlocal.restaurantapp.util.storage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class PrefStringStore extends PrefStore<String> {

    public PrefStringStore(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String load() {
        return prefs.getString(getField(), null);
    }

    @Override
    public void store(@Nullable String value) {
        prefs.edit()
                .putString(getField(), value)
                .apply();
    }

    @Override
    public void remove() {
        prefs.edit()
                .remove(getField())
                .apply();
    }

    @NonNull
    protected abstract String getField();

}
