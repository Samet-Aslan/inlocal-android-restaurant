package com.inlocal.restaurantapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.inlocal.restaurantapp.commonmodel.NotificationSettings;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;

public class SharedPrefUtils {

    private static final String PREF_APP = "InLocalRestaurant";

    private SharedPrefUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Gets boolean data.
     *
     * @param context the context
     * @param key     the key
     * @return the boolean data
     */
    static public boolean getBooleanData(Context context, String key) {

        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    /**
     * Gets int data.
     *
     * @param context the context
     * @param key     the key
     * @return the int data
     */
    static public int getIntData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, 0);
    }

    /**
     * Gets string data.
     *
     * @param context the context
     * @param key     the key
     * @return the string data
     */
    // Get Data
    static public String getStringData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, "");
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    // Save Data
    static public void saveData(Context context, String key, String val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key, val).apply();
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, Integer val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, val).apply();
    }

    static public void saveNotificationData(Context context, NotificationSettings settings) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_POST, settings.getPost()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_STORIES, settings.getStories()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_COMMENT, settings.getComments()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_FOLLOWER, settings.getFollowers()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_ORDER, settings.getStories()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_BOOKINGS, settings.getBookings()).apply();
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(Constants.SharePref.NOTI_PAYMENT, settings.getStories()).apply();

    }

    static NotificationSettings getNotificationData(Context context, NotificationSettings settings) {
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setPost(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_POST, "0"));
        notificationSettings.setStories(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_STORIES, "0"));
        notificationSettings.setComments(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_COMMENT, "0"));
        notificationSettings.setFollowers(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_FOLLOWER, "0"));
        notificationSettings.setOrders(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_ORDER, "0"));
        notificationSettings.setBookings(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_BOOKINGS, "0"));
        notificationSettings.setPayment(context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(Constants.SharePref.NOTI_PAYMENT, "0"));
        return notificationSettings;
    }

    /**
     * Save data.
     *
     * @param context the context
     * @param key     the key
     * @param val     the val
     */
    static public void saveData(Context context, String key, boolean val) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, val)
                .apply();
    }

    static public SharedPreferences.Editor getSharedPrefEditor(Context context, String pref) {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit();
    }

    static public void saveData(SharedPreferences.Editor editor) {
        editor.apply();
    }

    static public void clearAll(Context context) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
