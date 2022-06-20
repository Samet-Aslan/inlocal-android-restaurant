package com.inlocal.restaurantapp.util;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConveter {

    private static String isoFormat = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private static String dateformat = "yyyy-MM-dd";
    private static String timeformat = "hh:mm a";
    private static String dateformat1 = "yyyy-MM-dd HH:mm:ss";
    private static String dateformat2 = "MMM dd, yyyy";
    private static String dateformat3 = "dd MMM, yyyy";
    private static String dateformat4 = "dd/MM/yyyy HH:mm:ss";
    private static String dateformat5 = "dd MMM, yyyy HH:mm:ss";
    private static String dateformat6 = "dd MMM-yy";
    private static String dateformat7 = "dd-MM-yyyy hh:mm a";
    private static String dateformat8 = "dd MMM yy";
    private static String dateformat9 = "dd MMM-yy HH:mm:ss";
    private static String dateformat10 = "dd MMM, yyyy HH:mm a";
    private static String dateformat11 = "dd MMM yyyy";
    private static String dateformat12 = "dd.MM.yyyy";
    private static String dateformat13 = "yyyy-MM-dd HH:mm:ss";
    private static String timeFormat1 = "HH:mm";
    private static String timeFormat2 = "hh:mm aa";
    private static String dateTimeFormat1 = "hh:mm aa, dd.MM.yyyy";

    public static String getCurrentZoneTime(String UTCdate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String pattern = timeformat;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(UTCdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getTwoMonthOldDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-2);
        Date c = cal.getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = df.format(c);
        Log.e("current_date", formattedDate);
        return formattedDate;
    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = df.format(c);
        Log.e("current_date", formattedDate);
        return formattedDate;
    }

    public static String getCurrentZoneDateTripFormat(String UTCdate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat2);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(UTCdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getCurrentZoneTimeTripFormat(String UTCdate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(timeformat);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(UTCdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getEntryDate(String myDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateformat6);
            Date date = format.parse(myDate);
            return new SimpleDateFormat(dateformat7).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
        //return new SimpleDateFormat(dateformat7).format(myDate);
    }

    public static String getInOutDate() {
        String date = "";
        try {
            Calendar c = Calendar.getInstance();
            DateFormat originalFormat = new SimpleDateFormat("dd MMM-yy", Locale.ENGLISH);
            // DateFormat targetFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
            Date date1 = originalFormat.parse(originalFormat.format(c.getTime()));
            c.add(Calendar.DATE, 1);
            Date date2 = originalFormat.parse(originalFormat.format(c.getTime()));
            /*startDate = targetFormat.format(date1);
            endDate = targetFormat.format(date2);*/
            //date = targetFormat.format(date1) + "&&" + targetFormat.format(date2);
            date = originalFormat.format(date1) + "&&" + originalFormat.format(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertIosToNormal(String myDate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter = new SimpleDateFormat(dateformat8);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);

        //return new SimpleDateFormat(dateformat7).format(myDate);
    }

    public static String convertToBookingDateTime(String myDate) {
        DateFormat sdf = new SimpleDateFormat(dateformat13);

        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat1);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);

        //return new SimpleDateFormat(dateformat7).format(myDate);
    }

    public static String convert3To6(String myDate) {
        DateFormat sdf = new SimpleDateFormat(dateformat3);

        SimpleDateFormat formatter = new SimpleDateFormat(dateformat6);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);

        //return new SimpleDateFormat(dateformat7).format(myDate);
    }

    /*

     * */

    public static String getDob(String myDate) {
        /*SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat4);
        formatter.setTimeZone(TimeZone.getDefault());
        String dateString = formatter.format(mydate);*/
        Log.e("sdate", myDate + " 00:00:00");
        //return getUTCDateISO(myDate + " 00:00:00");
        return simpleISOFormat(myDate + " 00:00:00");
    }

    public static String getddMMMyyyToIso(String myDate) {
        /*SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat4);
        formatter.setTimeZone(TimeZone.getDefault());
        String dateString = formatter.format(mydate);*/
        Log.e("sdate", myDate + " 00:00:00");
        return getNormalToIso(myDate + " 00:00:00");
    }

    public static String getStartDayTime() {
        Date mydate = Calendar.getInstance(TimeZone.getDefault()).getTime();
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat);
        formatter.setTimeZone(TimeZone.getDefault());
        String dateString = formatter.format(mydate);
        Log.e("sdate", dateString + " 00:00:00");
        return getUTCDateISO(dateString + " 00:00:00");
    }

    public static String getEndDayTime() {
        Date mydate = Calendar.getInstance(TimeZone.getDefault()).getTime();
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat);
        formatter.setTimeZone(TimeZone.getDefault());
        String dateString = formatter.format(mydate);
        Log.e("sdate", dateString + " 23:59:59");
        return getUTCDateISO(dateString + " 23:59:59");
    }

    public static String simpleISOFormat(String mydate) {
        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(dateformat5);
            SimpleDateFormat outputFormat = new SimpleDateFormat(isoFormat);
            Date date = null;

            date = inputFormat.parse(mydate);

            formattedDate = outputFormat.format(date);
            System.out.println(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String getUTCDateISO(String mydate) {
        DateFormat sdf = new SimpleDateFormat(dateformat5);
        sdf.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(isoFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(mydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getNormalToIso(String mydate) {
        DateFormat sdf = new SimpleDateFormat(dateformat9);
        sdf.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(isoFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(mydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getDateddMMMyyyyformat(String utcDate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat3);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getNewDateformat(String utcDate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat11);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    public static String getDateddMMMyyyyformatWithTime(String utcDate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateformat10, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());

        Date UTCDate = null;
        try {
            UTCDate = sdf.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(UTCDate);
    }

    /*public static boolean cancelShow(String checkInDate) {

        DateFormat sdf = new SimpleDateFormat(isoFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter = new SimpleDateFormat(dateformat4);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(checkInDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        checkInDate = formatter.format(Date);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        String currentDate = formatter.format(c);

        try {
            Date cr = formatter.parse(currentDate);
            Date chkIn = formatter.parse(checkInDate);

            if (chkIn.before(cr)) {
                return false;
            } else
                return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }*/


    public static boolean cancelShow(String checkInDate) {

        DateFormat sdf = new SimpleDateFormat(isoFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter = new SimpleDateFormat(dateformat7, Locale.ENGLISH);
        try {
            Date Date = sdf.parse(checkInDate);
            checkInDate = formatter.format(Date);
            Date c = Calendar.getInstance().getTime();

            String currentDate = formatter.format(c);
            Log.e("//////", "currentDate-" + currentDate);
            Log.e("//////", "checkInDate-" + checkInDate);
            Date cr = formatter.parse(currentDate);
            Date chkIn = formatter.parse(checkInDate);
            Log.e("//////", "cr-" + cr);
            Log.e("//////", "chkIn-" + chkIn);
            if (currentDate.equals(checkInDate)) {
                return true;
            } else {
                if (chkIn.before(cr)) {
                    return false;
                } else
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

    public static String getFormatedDateTime(String time) {

        SimpleDateFormat fmt = new SimpleDateFormat(timeFormat1, Locale.ENGLISH);
        Date date = null;
        try {
            date = fmt.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat(timeFormat2, Locale.ENGLISH);
        return fmtOut.format(date);
    }

    public static String convertNotificationDateToTime(String rcvDate) {

        DateFormat sdf = new SimpleDateFormat(isoFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat2, Locale.ENGLISH);
        Date Date = null;
        try {
            Date = sdf.parse(rcvDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);
    }

    public static String convertDateInDotedFormate(String rcvDate) {
        DateFormat sdf = new SimpleDateFormat(isoFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter = new SimpleDateFormat(dateformat12, Locale.ENGLISH);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(rcvDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);
    }

    public static String convertDateTimeFormat1(String myDate) {

        DateFormat sdf = new SimpleDateFormat(isoFormat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat1, Locale.ENGLISH);
        //formatter.setTimeZone(TimeZone.getDefault());

        Date Date = null;
        try {
            Date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(Date);
    }

    public static String getTimeFormat2ToFormat1(String time) {

        SimpleDateFormat fmt = new SimpleDateFormat(timeFormat2, Locale.ENGLISH);
        Date date = null;
        try {
            date = fmt.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat(timeFormat1, Locale.ENGLISH);
        return fmtOut.format(date);
    }

    public static boolean compaireStartEndTime(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat2);
            Date inTime = null;
            inTime = sdf.parse(startTime);
            Date outTime = sdf.parse(endTime);
            assert inTime != null;
            if (inTime.compareTo(outTime) > 0) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
