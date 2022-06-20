package com.inlocal.restaurantapp.util.permissionmanager;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.lang.ref.WeakReference;

public final class ActivityPermissionManager implements PermissionManager {

//    private static final int REQUEST_CODE = 304;

    private static int REQUEST_CODE = 300;

    private OnPermissionListener listener;
    private WeakReference<Activity> activity;
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

    public ActivityPermissionManager(@NonNull Activity activity, @NonNull String... permissions) {
        this.activity = new WeakReference<>(activity);
        this.permissions = permissions;
        REQUEST_CODE++;
        if (REQUEST_CODE > 400)
            REQUEST_CODE = 300;
        checkPermissions();
    }

    public ActivityPermissionManager(@NonNull Activity activity, @NonNull OnPermissionListener listener, @NonNull String... permissions) {
        this(activity, permissions);
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

        if (activity.get() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity.get(), permissions, REQUEST_CODE);
        }
        return false;
    }

    @Override
    public void handleResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED &&
                        listener != null &&
                        ActivityCompat.shouldShowRequestPermissionRationale(activity.get(), permissions[i])) {
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
        if (activity.get() == null)
            return;

        isGranted = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String s : permissions)
                if (ActivityCompat.checkSelfPermission(activity.get(), s) != PackageManager.PERMISSION_GRANTED)
                    isGranted = false;
        }
    }
}
