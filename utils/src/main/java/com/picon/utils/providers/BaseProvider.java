package com.picon.utils.providers;

import androidx.annotation.NonNull;

import com.picon.utils.validators.Validator;

import java.util.ArrayList;

public class BaseProvider {

    public static <T> int getSuggestedPosition(@NonNull T query, @NonNull ArrayList<T> list) {

        int index = 0;

        if (Validator.isValidList(list)) {
            for (index = 0; index < list.size(); index++) {
                if (query.equals(list.get(index))) {
                    return index;
                }
            }
        }

        return index;
    }

}
