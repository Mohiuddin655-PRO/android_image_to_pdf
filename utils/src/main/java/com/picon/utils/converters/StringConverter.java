package com.picon.utils.converters;

import android.text.Editable;

import androidx.annotation.NonNull;

import com.picon.utils.validators.Validator;

import java.util.ArrayList;

public class StringConverter extends Converter {

    @NonNull
    public static String toDigit(String value) {

        StringBuilder buffer = new StringBuilder();

        if (value != null) {
            for (int i = 0, size = value.length(); i < size; i++) {
                char character = value.charAt(i);
                if (Character.isDigit(character)) {
                    buffer.append(character);
                }
            }
        }

        return buffer.toString();
    }

    @NonNull
    public static String toDigitWithPlus(String value) {

        StringBuilder buffer = new StringBuilder();

        if (value != null) {
            for (int i = 0, size = value.length(); i < size; i++) {
                char character = value.charAt(i);
                if (character == '+' || Character.isDigit(character)) {
                    buffer.append(character);
                }
            }
        }

        return buffer.toString();
    }

    @NonNull
    public static String toDigitWithLetter(String value) {

        StringBuilder buffer = new StringBuilder();

        if (value != null) {
            for (int i = 0, size = value.length(); i < size; i++) {
                char character = value.charAt(i);
                if (Character.isLetterOrDigit(character)) {
                    buffer.append(character);
                }
            }
        }

        return buffer.toString();
    }

    @NonNull
    public static String toLetter(String value) {

        StringBuilder buffer = new StringBuilder();

        if (value != null) {
            for (int i = 0, size = value.length(); i < size; i++) {
                char character = value.charAt(i);
                if (Character.isLetter(character)) {
                    buffer.append(character);
                }
            }
        }

        return buffer.toString();
    }

    @NonNull
    public static String toString(String value) {
        return value != null ? value : "";
    }

    @NonNull
    public static String toString(String... strings) {
        StringBuilder value = new StringBuilder("");
        if (Validator.isValidList(strings)) {
            for (String s : strings) {
                if (Validator.isValidString(s)) {
                    value.append(s);
                }
            }
        }
        return String.valueOf(value);
    }

    @NonNull
    public static String toString(Object value) {
        return toString(String.valueOf(value));
    }

    @NonNull
    public static String toString(@NonNull ArrayList<String> list) {
        final StringBuilder builder = new StringBuilder();
        if (Validator.isValidList(list)) {
            int size = list.size();
            int end = size - 1;
            int and = size - 2;
            for (int index = 0; index < size; index++) {
                if (index == and) {
                    builder.append(list.get(index)).append(" and ");
                } else if (index == end) {
                    builder.append(list.get(index));
                } else {
                    builder.append(list.get(index)).append(", ");
                }
            }
        }
        return toString(builder);
    }

    @NonNull
    public static String toString(CharSequence value) {
        return toString(String.valueOf(value));
    }

    @NonNull
    public static String toString(Editable value) {
        return toString(String.valueOf(value));
    }

    @NonNull
    public static String toString(@NonNull String format, Object... values) {
        return Validator.isValidString(format) && values.length > 0 ? String.format(format, values) : "";
    }

    @NonNull
    public static String toNumber(Integer value) {
        return Validator.isValidObject(value) ? toString(value) : "0";
    }

    @NonNull
    public static String toNumber(String value) {
        return Validator.isValidDigit(value) ? toString(Long.parseLong(value)) : "0";
    }
}
