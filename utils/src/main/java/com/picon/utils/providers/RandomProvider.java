package com.picon.utils.providers;

import androidx.annotation.NonNull;

import com.picon.utils.validators.Validator;

import java.util.ArrayList;
import java.util.Random;

public class RandomProvider {

    public static int getRandomInt(int max_size) {
        final Random random = new Random();
        return random.nextInt(max_size);
    }

    public static String getRandomString(int max_length, @NonNull String keyFormat) {
        final Random random = new Random();
        final StringBuilder value = new StringBuilder(max_length);
        for (int i = 0; i < max_length; ++i) {
            value.append(keyFormat.charAt(random.nextInt(keyFormat.length())));
        }
        return String.valueOf(value);
    }

    @SafeVarargs
    public static <T> T getRandomValue(@NonNull T... ts) {
        if (Validator.isValidList(ts)) {
            final Random random = new Random();
            return ts[random.nextInt(ts.length)];
        } else {
            return null;
        }
    }

    @SafeVarargs
    public static <T> ArrayList<T> getRandomList(int size, @NonNull T... ts) {
        if (Validator.isValidList(ts)) {
            final ArrayList<T> list = new ArrayList<>(size);
            for (int i = 0; i < size; ++i) {
                list.add(getRandomValue(ts));
            }
            return list;
        }
        return null;
    }

}
