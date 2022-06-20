package com.inlocal.restaurantapp.service.network;

import android.content.Context;
import androidx.annotation.NonNull;

import com.inlocal.restaurantapp.util.storage.PrefStringStore;

public final class TokenStore extends PrefStringStore {

    public TokenStore(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String getField() {
        return "Token";
    }

}