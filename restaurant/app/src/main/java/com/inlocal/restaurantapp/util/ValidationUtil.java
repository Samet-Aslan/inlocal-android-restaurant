package com.inlocal.restaurantapp.util;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean isFieldEmpty(String text) {
        if (text == null) {
            return false;
        }
        return TextUtils.isEmpty(text);
    }

    public static boolean isZipCodeValid(String zipcode) {
        return zipcode != null && zipcode.trim().length() == 5;
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null && phone.trim().length() < 10;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() < 6;
    }

    public static boolean isPasswordPatternValid(String password) {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isOTPValid(String OTP) {
        return OTP != null && OTP.trim().length() < 4;
    }
}
