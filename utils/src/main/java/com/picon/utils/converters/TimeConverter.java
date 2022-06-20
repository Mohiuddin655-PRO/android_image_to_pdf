package com.picon.utils.converters;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

public class TimeConverter {

    public static long toMillis(int time, @NonNull TimeUnit fromUnit) {
        return fromUnit.toMillis(time);
    }

    public static long toMillisFromSecond(int second) {
        return toMillis(second, TimeUnit.SECONDS);
    }

    public static long toMillisFromMinute(int minute) {
        return toMillis(minute, TimeUnit.MINUTES);
    }

    public static long toMillisFromHour(int hour) {
        return toMillis(hour, TimeUnit.HOURS);
    }

    public static long toMillisFromDay(int day) {
        return toMillis(day, TimeUnit.DAYS);
    }

    public static long toNanos(long duration) {
        return TimeUnit.MILLISECONDS.toNanos(duration);
    }

    public static long toMicros(long duration) {
        return TimeUnit.MILLISECONDS.toMicros(duration);
    }

    public static long toDays(long duration) {
        return TimeUnit.MILLISECONDS.toDays(duration);
    }

    public static long toHours(long duration) {
        return TimeUnit.MILLISECONDS.toHours(duration);
    }

    public static long toHoursFromDays(long duration) {
        return toHours(toDays(duration));
    }

    public static long toMinutes(long duration) {
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static long toMinutesFromHours(long duration) {
        return toMinutes(toHours(duration));
    }

    public static long toSeconds(long duration) {
        return TimeUnit.MILLISECONDS.toSeconds(duration);
    }

    public static long toSecondsFromMinutes(long duration) {
        return toSeconds(toMinutes(duration));
    }
}
