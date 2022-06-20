package com.inlocal.restaurantapp.util.storage;

import androidx.annotation.Nullable;

public interface Store<T> {

    @Nullable
    T load();

    void store(@Nullable T value);

    void remove();

}
