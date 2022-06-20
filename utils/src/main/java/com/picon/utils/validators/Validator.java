package com.picon.utils.validators;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.converters.Converter;
import com.picon.utils.converters.StringConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isMatched(Object matcher, Object value) {
        return Validator.isValidObject(matcher) && matcher.equals(value);
    }

    public static boolean isMatched(@Nullable ArrayList<String> matchers, @Nullable ArrayList<String> values) {
        final ArrayList<Boolean> booleans = new ArrayList<>();
        if (matchers == null) matchers = new ArrayList<>();
        if (values == null) values = new ArrayList<>();
        int matcher_size = matchers.size();
        int value_size = values.size();
        if (matcher_size > 0 && matcher_size == value_size) {
            for (int index = 0; index < values.size(); index++) {
                if (Validator.isMatched(matchers.get(index), values.get(index))) {
                    booleans.add(true);
                }
            }
        }
        return booleans.size() == value_size;
    }

    public static boolean isChecked(String checker, ArrayList<String> list) {
        return Validator.isValidString(checker) && Validator.isValidList(list) && list.contains(checker);
    }

    public static boolean isChecked(Object checker, ArrayList<?> list) {
        return Validator.isValidObject(checker) && Validator.isValidList(list) && list.contains(checker);
    }

    public static boolean isDigit(String value) {
        if (value != null && value.length() > 0) {
            for (char character : value.toCharArray()) {
                if (!Character.isDigit(character)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidDay(String day) {
        return isValidDigit(day) && isValidDay(Converter.toInt(day));
    }

    public static boolean isValidMonth(String month) {
        return isValidDigit(month) && isValidMonth(Converter.toInt(month));
    }

    public static boolean isValidYear(String year, int requireAge) {
        return isValidDigit(year) && isValidYear(Converter.toInt(year), requireAge);
    }

    public static boolean isValidDay(int day) {
        return (day >= 1) && (day <= 31);
    }

    public static boolean isValidMonth(int month) {
        return (month >= 1) && (month <= 12);
    }

    public static boolean isValidYear(int year, int requireAge) {
        final int currentYear = Calendar.getInstance(Locale.ENGLISH).get(Calendar.YEAR);
        return (year > 1900) && (year < currentYear) && ((currentYear - year) >= requireAge);
    }

    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public static boolean isValidPassword(String password, int minLength) {
        return !TextUtils.isEmpty(password) && password.length() >= minLength;
    }

    public static boolean isValidRetypePassword(String password, String retypePassword) {
        return retypePassword.equals(password);
    }

    public static boolean isValidDigit(String value) {
        return isValidString(value) && value.equals(StringConverter.toDigit(value));
    }

    public static boolean isValidDigitWithLetter(String value) {
        return isValidString(value) && value.equals(StringConverter.toDigitWithLetter(value));
    }

    public static boolean isValidDigitWithPlus(String value) {
        return isValidString(value) && value.equals(StringConverter.toDigitWithPlus(value));
    }

    public static boolean isValidLetter(String value) {
        return isValidString(value) && value.equals(StringConverter.toLetter(value));
    }

    @SafeVarargs
    public static <T> boolean isValidList(T... list) {
        return list != null && list.length > 0;
    }

    public static <T> boolean isValidList(Set<String> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValidList(ArrayList<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValidObject(Object value) {
        return value != null;
    }

    public static <T> boolean isValidObject(@NonNull Class<T> cls, Object value) {
        return isValidObject(value) && cls.isInstance(value);
    }

    public static boolean isValidString(String value) {
        return !TextUtils.isEmpty(value) && !value.toLowerCase().equals("null");
    }

    public static boolean isValidStrings(String... values) {
        ArrayList<Boolean> list = new ArrayList<>();
        for (String value : values) {
            if (Validator.isValidString(value)) {
                list.add(true);
            }
        }
        return list.size() == values.length;
    }

    public static boolean isValidString(String value, int maxLength) {
        return maxLength <= 0 ? isValidString(value) : isValidString(value) && value.length() <= maxLength;
    }

    public static boolean isValidString(String value, @NonNull Pattern pattern) {
        return isValidString(value) && pattern.matcher(value).matches();
    }

    public static boolean isValidString(String value, @NonNull Pattern pattern, int maxLength) {
        return isValidString(value, maxLength) && pattern.matcher(value).matches();
    }

    public static boolean isValidVerificationCode(String code) {
        return !TextUtils.isEmpty(code) && code.length() == 6;
    }

    public static boolean isValidWebURL(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

    public static boolean isStar(float rating, float start_rate) {
        return rating >= start_rate;
    }
}
