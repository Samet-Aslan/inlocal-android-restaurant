package com.inlocal.restaurantapp.util;

import android.content.Context;

public class Constants {

    public static int DEFAULT_PAGE_SIZE = 10;

    public static final boolean isDebug = true;
    public static String SERVER_URL = "http://ec2-3-0-166-170.ap-southeast-1.compute.amazonaws.com/inlocal-restaurant-nfc-app/api/";
    public static String AGENT_LOGGED_IN = "agent_logged_in";
    public static String RESTAURANT_ID = "restaurant_id";
    public static String ACCESS_TOKEN = "access_token";
    public static Context publicContext;


    public interface IntentData {
        String DATA = "data";
        String FROM = "from";
        String MENU_NAME = "menuName";
        String MENU_IMAGE = "menuImage";
        String FEED_WALL_DATA = "feedwall";
        String STORY_ITEM = "story_item";
        String RESTAURUNT_DETAILS = "rest_details";
        String RESTAURUNT_ID = "rest_id";
        String OPENING_HOURS = "opening_hours";
        String CUSTOMER_TYPE = "customer_type";
        String OTHER_USER_ID = "other_user_id";
        String ORDER_ID = "order_id";
        String OTHER_USER_TYPE = "other_user_type";
        String DELIVER_DATA = "deliverData";
        String CUSTOMER_DATA = "customerData";
        String CUSTOMER_ID = "customerId";
        String MENU_ID = "menuId";
        String CATEGORY_DATA = "categoryData";
        String POST_ID = "post_id";
    }

    public interface OrderStatus {
        String PENDING = "PENDING";
        String ACCEPTED = "ACCEPTED";
        String REJECTED = "REJECTED";
        String IN_PROGRESS = "IN-PROCESS";
        String DELIVERED = "DELIVERED";
        String FAILED = "FAILED";
        String CANCELED = "CANCELLED";
        String DONE = "DONE";
    }

    public interface BookingStatus {
        String PENDING = "Pending";
        String ACCEPTED = "Accepted";
        String DECLINED = "Declined";
        String CONFIRM = "Confirmed";
        String CANCEL = "Cancel";
    }

    public interface OrderType {
        String DELIVERY = "DELIVERY";
        String DINE_IN = "DINE IN";
        String STORE_PICKUP = "STORE PICKUP";
    }

    public interface TimeSpam {
        String TODAY = "today";
        String THIS_WEEK = "this_week";
        String THIS_MONTH = "this_month";
        String ALL_TIME = "all_time";
    }

    public interface SharePref {
        String USER_NAME = "name";
        String USER_EMAIL = "email";
        String PROFILE_PIC = "PROFILE_PIC";
        String NOTI_POST = "NOTI_POST";
        String NOTI_STORIES = "NOTI_STORIES";
        String NOTI_COMMENT = "NOTI_COMMENT";
        String NOTI_FOLLOWER = "NOTI_FOLLOWER";
        String NOTI_ORDER = "NOTI_ORDER";
        String NOTI_BOOKINGS = "NOTI_BOOKING";
        String NOTI_PAYMENT = "NOTI_PAYMENT";
    }

    public interface IntentKey {
        String RedirectionTarget = "RedirectionTarget";
        String RedirectionExtra = "RedirectionExtra";
    }

    public interface ListShowType {
        Integer POST = 1;
        Integer INSIGHT = 2;
    }
}
