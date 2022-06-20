package com.inlocal.restaurantapp.util;

import android.util.Log;

public class LogUtil {
    public static void LogE(String name, String message) {
        if (Constants.isDebug) {
            Log.e(name, message);
        }
    }

    public static void LogD(String name, String message) {
        if (Constants.isDebug) {
            Log.d(name, message);
        }
    }

    public static void LogI(String name, String message) {
        if (Constants.isDebug) {
            Log.i(name, message);
        }
    }

    public static void LogW(String name, String message) {
        if (Constants.isDebug) {
            Log.w(name, message);
        }
    }
}
