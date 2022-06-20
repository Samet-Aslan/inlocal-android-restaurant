package com.inlocal.restaurantapp.util;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import com.inlocal.interfaces.OnDateClick;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomTimePicker {
    public static void openTimePicker(Context context, TextView textView, String format, OnDateClick onDateClick) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {
            mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
            mcurrentTime.set(Calendar.MINUTE, selectedMinute);
            SimpleDateFormat mFormat = new SimpleDateFormat(format, Locale.ENGLISH);

            textView.setText(mFormat.format(mcurrentTime.getTime()));
            onDateClick.onDateClick();
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
