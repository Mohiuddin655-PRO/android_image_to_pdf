package com.picon.utils.providers;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.picon.utils.converters.TimeConverter;

import java.util.Locale;

public class CountDownProvider {

    public static final String TIME_FORMAT_S = "%02d - Seconds";
    public static final String TIME_FORMAT_M = "%02d - Minutes";
    public static final String TIME_FORMAT_H = "%02d - Hours";
    public static final String TIME_FORMAT_D = "%02d - Days";
    public static final String TIME_FORMAT_MS = "%02d : %02ds";
    public static final String TIME_FORMAT_HM = "%02d : %02dm";
    public static final String TIME_FORMAT_DH = "%02d : %02dh";
    public static final String TIME_FORMAT_HMS = "%02d : %02d : %02ds";
    public static final String TIME_FORMAT_DHM = "%02d : %02d : %02dm";
    public static final String TIME_FORMAT_DHMs = " %02d Days - %02d : %02d : %02ds";

    public static final long DEFAULT_COUNT_DOWN_INTERVAL = 1000;
    public static final String DEFAULT_TIME_FORMAT = TIME_FORMAT_MS;
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private CountDownTimer mCountDownTimer;

    public void run(@NonNull OnCountDownListener listener, int minute) {
        this.run(listener, TimeConverter.toMillisFromMinute(minute));
    }

    public void run(@NonNull OnCountDownListener listener, int minute, @NonNull String timeFormat) {
        this.run(listener, TimeConverter.toMillisFromMinute(minute), DEFAULT_COUNT_DOWN_INTERVAL, timeFormat, DEFAULT_LOCALE);
    }

    public void run(@NonNull OnCountDownListener listener, int minute, @NonNull String timeFormat, @NonNull Locale locale) {
        this.run(listener, TimeConverter.toMillisFromMinute(minute), DEFAULT_COUNT_DOWN_INTERVAL, timeFormat, locale);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis) {
        this.run(listener, timeMillis, DEFAULT_COUNT_DOWN_INTERVAL, DEFAULT_TIME_FORMAT, DEFAULT_LOCALE);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis, long countDownInterval) {
        this.run(listener, timeMillis, countDownInterval, DEFAULT_TIME_FORMAT, DEFAULT_LOCALE);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis, long countDownInterval, @NonNull String timeFormat) {
        this.run(listener, timeMillis, countDownInterval, timeFormat, DEFAULT_LOCALE);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis, @NonNull String timeFormat) {
        this.run(listener, timeMillis, DEFAULT_COUNT_DOWN_INTERVAL, timeFormat, DEFAULT_LOCALE);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis, @NonNull String timeFormat, @NonNull Locale locale) {
        this.run(listener, timeMillis, DEFAULT_COUNT_DOWN_INTERVAL, timeFormat, locale);
    }

    public void run(@NonNull OnCountDownListener listener, long timeMillis, long countDownInterval, @NonNull String timeFormat, @NonNull Locale locale) {

        this.mCountDownTimer = new CountDownTimer(timeMillis, countDownInterval) {
            @Override
            public void onTick(long millis) {
                final String time = chooseTime(millis, timeFormat, locale);
                listener.onTick(millis, time);
            }

            @Override
            public void onFinish() {
                listener.onFinish();
            }
        }.start();
    }

    private String chooseTime(long duration, @NonNull String format, @NonNull Locale locale) {
        switch (format) {
            case TIME_FORMAT_S:
                return getRemainingTimeForS(getRemainingSeconds(duration), locale);
            case TIME_FORMAT_M:
                return getRemainingTimeForM(getRemainingMinutes(duration), locale);
            case TIME_FORMAT_H:
                return getRemainingTimeForH(getRemainingHours(duration), locale);
            case TIME_FORMAT_D:
                return getRemainingTimeForD(getRemainingDays(duration), locale);
            case TIME_FORMAT_MS:
                return getRemainingTimeForMS(getRemainingMinutes(duration), getRemainingSeconds(duration), locale);
            case TIME_FORMAT_HM:
                return getRemainingTimeForHM(getRemainingHours(duration), getRemainingMinutes(duration), locale);
            case TIME_FORMAT_DH:
                return getRemainingTimeForDH(getRemainingDays(duration), getRemainingHours(duration), locale);
            case TIME_FORMAT_HMS:
                return getRemainingTimeForHMS(getRemainingHours(duration), getRemainingMinutes(duration), getRemainingSeconds(duration), locale);
            case TIME_FORMAT_DHM:
                return getRemainingTimeForDHM(getRemainingDays(duration), getRemainingHours(duration), getRemainingMinutes(duration), locale);
            case TIME_FORMAT_DHMs:
                return getRemainingTimeForDHMs(getRemainingDays(duration), getRemainingHours(duration), getRemainingMinutes(duration), getRemainingSeconds(duration), locale);
            default:
                return "";
        }
    }

    public void stop() {
        if (this.mCountDownTimer != null) this.mCountDownTimer.cancel();
    }

    public long getRemainingSeconds(long duration) {
        final long timeA = TimeConverter.toSeconds(duration);
        final long timeA_from_timeB = TimeConverter.toSecondsFromMinutes(duration);
        return timeA - timeA_from_timeB;
    }

    public long getRemainingMinutes(long duration) {
        final long timeA = TimeConverter.toMinutes(duration);
        final long timeA_from_timeB = TimeConverter.toMinutesFromHours(duration);
        return timeA - timeA_from_timeB;
    }

    public long getRemainingHours(long duration) {
        final long timeA = TimeConverter.toHours(duration);
        final long timeA_from_timeB = TimeConverter.toHoursFromDays(duration);
        return timeA - timeA_from_timeB;
    }

    public long getRemainingDays(long duration) {
        return TimeConverter.toDays(duration);
    }

    public String getRemainingTimeForS(long seconds, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_S, seconds);
    }

    public String getRemainingTimeForM(long minutes, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_M, minutes);
    }

    public String getRemainingTimeForH(long hours, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_H, hours);
    }

    public String getRemainingTimeForD(long days, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_D, days);
    }

    public String getRemainingTimeForMS(long minutes, long seconds, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_MS, minutes, seconds);
    }

    public String getRemainingTimeForHM(long hours, long minutes, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_HM, hours, minutes);
    }

    public String getRemainingTimeForDH(long days, long hours, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_DH, days, hours);
    }

    public String getRemainingTimeForHMS(long hours, long minutes, long seconds, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_HMS, hours, minutes, seconds);
    }

    public String getRemainingTimeForDHM(long days, long hours, long minutes, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_DHM, days, hours, minutes);
    }

    public String getRemainingTimeForDHMs(long days, long hours, long minutes, long seconds, @NonNull Locale locale) {
        return String.format(locale, TIME_FORMAT_DHMs, days, hours, minutes, seconds);
    }

    public interface OnCountDownListener {

        void onTick(long timeMills, @NonNull String time);

        void onFinish();
    }
}
