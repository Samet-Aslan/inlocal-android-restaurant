package com.inlocal.restaurantapp.util.permissionmanager;


import androidx.annotation.NonNull;

public interface PermissionManager {
    int REQUEST_CODE = 304;

    void setOnPermissionListener(OnPermissionListener listener);

    boolean askForPermissions();

    void handleResult(int requestCode, String[] permissions, int[] grantResults);

    boolean isGranted();

    void checkPermissions();

    interface OnPermissionListener {

        void onAskPermissionRationale(String permission, @NonNull Callback callback);

        interface Callback {

            void onPositive();

            void onNegative();

        }

    }


}
