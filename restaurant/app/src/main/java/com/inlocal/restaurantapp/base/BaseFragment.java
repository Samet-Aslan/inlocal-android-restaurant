package com.inlocal.restaurantapp.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.inlocal.restaurantapp.custom.LoadingDialog;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<V extends ViewDataBinding> extends DaggerFragment {

    private AppCompatActivity activity;
    protected V binding;
    private Dialog mLoadingDialog;

    @LayoutRes
    protected abstract int layoutRes();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false);
        mLoadingDialog = new LoadingDialog(getActivity());
        return binding.getRoot();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
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

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}