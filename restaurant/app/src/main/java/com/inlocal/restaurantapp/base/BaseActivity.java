package com.inlocal.restaurantapp.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.inlocal.restaurantapp.custom.LoadingDialog;
import com.inlocal.restaurantapp.util.Constants;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<V extends ViewDataBinding> extends DaggerAppCompatActivity {
    protected V binding;
    private Dialog mLoadingDialog;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.publicContext = this;
        binding = DataBindingUtil.setContentView(this, layoutRes());
        mLoadingDialog = new LoadingDialog(this);
    }

    public void showLoading() {
        if (mLoadingDialog != null) {
            if (!mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            }
        }
    }

    public void showSnackbar(String msg) {
        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG)
                .show();
    }
}
