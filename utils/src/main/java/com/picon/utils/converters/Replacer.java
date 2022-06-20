package com.picon.utils.converters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.validators.Validator;

import java.util.Arrays;
import java.util.List;

public class Replacer {

    public static String toReplace(@NonNull String value) {
        for (int index = 0; index < value.length(); index++) {
            for (String regex : Arrays.asList("!", "@", "#", "$", "^", "*", "+", "=", "{", "}", "[", "]", "\\", "|", ":", ";", "<", ">", "?", "/", "%", "(", ")", ".")) {
                value = value.replace(regex, "");
            }
            for (String s : Arrays.asList(" ", "\"", "'", ",", "-", "&")) {
                value = value.replace(s, "_");
            }
        }
        return value;
    }

    public static String toReplace(@NonNull String value, @NonNull String replacement, @Nullable String... regexList) {
        if (regexList != null && regexList.length > 0) {
            for (String regex : regexList) {
                value = value.replaceAll(regex, replacement);
            }
        }
        return value;
    }

    public static String toReplace(@NonNull String value, @NonNull List<String> replacements, @NonNull List<String> regexList) {
        if (regexList.size() > 0 && Validator.isMatched(regexList.size(), replacements.size())) {
            for (int index = 0; index < value.length(); index++) {
                value = value.replaceAll(regexList.get(index), replacements.get(index));
            }
        }
        return value;
    }

}
