package com.inlocal.restaurantapp.util.permissionmanager;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public final class FragmentPermissionManager implements PermissionManager {

    //    private static final int REQUEST_CODE = 304;
    private static final int REQUEST_CODE = 304;

    private OnPermissionListener listener;
    private WeakReference<Fragment> fragment;
    private String[] permissions;
    private boolean isGranted = false;

    private final OnPermissionListener.Callback callback = new OnPermissionListener.Callback() {

        @Override
        public void onPositive() {
            askForPermissions();
        }

        @Override
        public void onNegative() {

        }

    };

    public FragmentPermissionManager(@NonNull Fragment fragment, @NonNull String... permissions) {
        this.fragment = new WeakReference<>(fragment);
        this.permissions = permissions;
        checkPermissions();
    }

    public FragmentPermissionManager(@NonNull Fragment fragment, @NonNull OnPermissionListener listener, @NonNull String... permissions) {
        this(fragment, permissions);
        this.listener = listener;
    }

    @Override
    public void setOnPermissionListener(OnPermissionListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean askForPermissions() {
        checkPermissions();

        if (isGranted())
            return true;

        if (fragment.get() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.get().requestPermissions(permissions, REQUEST_CODE);
        }
        return false;
    }

    @Override
    public void handleResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED &&
                        listener != null &&
                        ActivityCompat.shouldShowRequestPermissionRationale(fragment.get().requireActivity(), permissions[i])) {
                    listener.onAskPermissionRationale(permissions[i], callback);
                }
            }
            checkPermissions();
        }
    }

    @Override
    public boolean isGranted() {
        return isGranted;
    }

    @Override
    public void checkPermissions() {
        if (fragment.get() == null || fragment.get().getActivity() == null)
            return;

        isGranted = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String s : permissions)
                if (ActivityCompat.checkSelfPermission(fragment.get().requireActivity(), s) != PackageManager.PERMISSION_GRANTED)
                    isGranted = false;
        }
    }
}
