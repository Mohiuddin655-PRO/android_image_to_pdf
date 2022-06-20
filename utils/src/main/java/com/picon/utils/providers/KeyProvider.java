package com.picon.utils.providers;

import androidx.annotation.NonNull;

import com.picon.utils.converters.Replacer;
import com.picon.utils.formats.KeyFormat;
import com.picon.utils.validators.Validator;

import java.util.Locale;

public class KeyProvider {

    public static String generateKey() {
        return generateKey(System.currentTimeMillis());
    }

    public static String generateKey(long time_mills) {
        return generateKey(time_mills, 5);
    }

    public static String generateKey(@NonNull String name) {
        return Validator.isValidString(name) ? Replacer.toReplace(name).toLowerCase(Locale.ENGLISH) : generateKey();
    }

    public static String generateKey(long time_mills, int extra_key_size) {
        return String.format("%s%s", time_mills, RandomProvider.getRandomString(extra_key_size, KeyFormat.ALLOWED_NUMBERS));
    }
}
