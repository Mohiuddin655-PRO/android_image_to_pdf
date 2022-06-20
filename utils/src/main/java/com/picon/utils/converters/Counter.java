package com.picon.utils.converters;

import androidx.annotation.NonNull;

public class Counter {

    @NonNull
    public static String toKCount(long value) {

        long counter;

        if (value >= 1000) {
            counter = value / 1000;
            return counter + "K";
        }

        return String.valueOf(value);
    }

    @NonNull
    public static String toKMCount(long value) {

        long counter;

        if (value >= 1000 && value < 1000000) {
            counter = value / 1000;
            return counter + "K";
        }
        if (value >= 1000000) {
            counter = value / 1000000;
            return counter + "M";
        }

        return String.valueOf(value);
    }

    @NonNull
    public static String toKMBCount(long value) {

        long counter;

        if (value >= 1000 && value < 1000000) {
            counter = value / 1000;
            return counter + "K";
        }
        if (value >= 1000000 && value < 1000000000) {
            counter = value / 1000000;
            return counter + "M";
        }

        if (value >= 1000000000) {
            counter = value / 1000000000;
            return counter + "B";
        }

        return String.valueOf(value);
    }

}
