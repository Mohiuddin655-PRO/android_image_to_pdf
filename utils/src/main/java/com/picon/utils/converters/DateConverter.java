package com.picon.utils.converters;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import androidx.annotation.NonNull;

import com.picon.utils.formats.DefaultFormat;

import java.util.Calendar;
import java.util.Locale;

public class DateConverter extends DefaultFormat {

    public static boolean isToday(long timeMills) {
        return DateUtils.isToday(timeMills);
    }

    public static long toTimeInMillis(int year, int month, int day) {
        return toTimeInMillis(year, month, day, 0, 0, 0);
    }

    public static long toTimeInMillis(int year, int month, int day, int hour, int minute, int second) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, day,
                hour, minute, second);

        return calendar.getTimeInMillis();
    }

    public static String toBirthday(long timeMills, @NonNull String format) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timeMills);
        return (String) DateFormat.format(format, calendar);
    }

    public static String toBirthday(int year, int month, int day, @NonNull String format) {
        if (year > 0 && month > 0 && day > 0) {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(DateConverter.toTimeInMillis(year, month, day));
            return String.valueOf(DateFormat.format(format, calendar));
        } else {
            return "";
        }
    }

    public static String toBirthday(long timeMills) {
        return toBirthday(timeMills, DefaultFormat.DATE_DM_Y);
    }

    public static String toBirthday(int year, int month, int day) {
        return toBirthday(year, month, day, DefaultFormat.DATE_DM_Y);
    }

    public static String toNameOfTime(long timeMills, @NonNull String format) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timeMills);
        return (String) DateFormat.format(format, calendar);
    }

    public static String toNameOfTime(int year, int month, int day, @NonNull String format) {
        if (year > 0 && month > 0 && day > 0) {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(DateConverter.toTimeInMillis(year, month, day));
            return String.valueOf(DateFormat.format(format, calendar));
        } else {
            return "";
        }
    }

    public static String toPublishTime(long timeMills, @NonNull String timeFormat, @NonNull String dateFormat) {

        final long currentTimeMillis = System.currentTimeMillis();
        final long normalTimeMills = currentTimeMillis - timeMills;

        long initTime;
        final int day = 86400000;
        final int hour = 3600000;
        final int minute = 60000;

        if (normalTimeMills < minute) {
            return "Now";
        } else if (normalTimeMills < hour) {
            initTime = normalTimeMills / minute;
            return initTime + " minute ago";
        } else if (normalTimeMills < day) {
            return String.format("Today - %s", toDate(timeMills, timeFormat));
        } else {
            initTime = normalTimeMills / day;
            String currentTime = toDate(currentTimeMillis, dateFormat);
            String oldTime = toDate(currentTimeMillis, dateFormat);
            if (initTime <= 1 && !currentTime.equals(oldTime)) {
                return String.format("Yesterday - %s", toDate(timeMills, timeFormat));
            } else {
                return String.format("%s - %s", toDate(currentTimeMillis, dateFormat), toDate(timeMills, timeFormat));
            }
        }
    }

    public static String toPublishTime(long timeMills) {
        return toPublishTime(timeMills, DefaultFormat.TIME_HM, DefaultFormat.DATE_DM_Y);
    }

    public static String toLiveTime(long timeMills, @NonNull String format) {

        final long currentTimeMillis = System.currentTimeMillis();
        final long normalTimeMills = currentTimeMillis - timeMills;

        long initTime;
        final int day = 86400000;
        final int hour = 3600000;
        final int minute = 60000;
        final int second = 1000;

        if (normalTimeMills < second) {
            return "Now";
        } else if (normalTimeMills < minute) {
            initTime = normalTimeMills / second;
            return initTime + " second ago";
        } else if (normalTimeMills < hour) {
            initTime = normalTimeMills / minute;
            return initTime + " minute ago";
        } else if (normalTimeMills < day) {
            initTime = normalTimeMills / hour;
            return initTime + " hour ago";
        } else {
            return toDate(timeMills, format);
        }
    }

    public static String toLiveTime(long timeMills) {
        return toLiveTime(timeMills, DefaultFormat.DATE_DM_Y);
    }

    public static String toDate(long timeMills, @NonNull String format) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timeMills);
        return (String) DateFormat.format(format, calendar);
    }

    public static String toActiveTime(long timeMillis) {

        final long currentTimeMillis = System.currentTimeMillis();
        final long normalTimeMills = currentTimeMillis - timeMillis;

        long initTime;
        final int day = 86400000;
        final int hour = 3600000;
        final int minute = 60000;

        if (normalTimeMills < minute) {
            return "Now";
        } else if (normalTimeMills < hour) {
            initTime = normalTimeMills / minute;
            return initTime + " minute ago";
        } else if (normalTimeMills < day) {
            initTime = normalTimeMills / hour;
            return initTime + " hour ago";
        } else {
            return "";
        }
    }
}
