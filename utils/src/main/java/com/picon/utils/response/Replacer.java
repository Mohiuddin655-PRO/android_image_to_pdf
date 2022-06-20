package com.picon.utils.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.constains.ReplacerType;
import com.picon.utils.converters.Converter;
import com.picon.utils.validators.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Replacer {

    private final String key;
    @ReplacerType
    private final int type;

    private final Object result;
    private final ArrayList<Object> results;
    private final Map<String, Object> resultMap;

    private final int position;

    public Replacer(@NonNull String key, @ReplacerType int type) {
        this(key, type, 0);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @Nullable Object result) {
        this(key, type, result, 0);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @NonNull ArrayList<Object> results) {
        this(key, type, results, 0);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @NonNull Map<String, Object> resultMap) {
        this(key, type, resultMap, 0);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, int position) {
        this(key, type, null, null, null, position);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @Nullable Object result, int position) {
        this(key, type, result, null, null, position);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @NonNull ArrayList<Object> results, int position) {
        this(key, type, null, results, null, position);
    }

    public Replacer(@NonNull String key, @ReplacerType int type, @NonNull Map<String, Object> resultMap, int position) {
        this(key, type, null, null, resultMap, position);
    }

    private Replacer(@NonNull String key, @ReplacerType int type, @Nullable Object result, @Nullable ArrayList<Object> results, @Nullable Map<String, Object> resultMap, int position) {
        this.key = key;
        this.type = type;
        this.result = result;
        this.results = results;
        this.resultMap = resultMap;
        this.position = position;
    }

    public String getKey() {
        return key;
    }

    @ReplacerType
    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public <T> T getResult(@NonNull Class<T> cls) {
        if (cls.isInstance(result)) {
            return cls.cast(result);
        } else {
            try {
                return cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public <T> T getResult(@NonNull Class<T> cls, int itemPosition) {
        ArrayList<?> list = getResults(cls);
        if (Validator.isValidList(list) && itemPosition < list.size()) {
            return getResults(cls).get(itemPosition);
        } else {
            return null;
        }
    }

    public <T> T getResult(@NonNull Class<T> cls, @NonNull String mappableKey) {
        Object data = getMap().get(mappableKey);
        if (data != null && cls.isInstance(data)) {
            return cls.cast(data);
        } else {
            return null;
        }
    }

    public <T> ArrayList<T> getResults(@NonNull Class<T> cls) {
        if (cls.isInstance(result)) {
            return Converter.toArrayList(cls, results);
        } else {
            return new ArrayList<>();
        }
    }

    public Map<String, Object> getMap() {
        return resultMap != null ? resultMap : new HashMap<>();
    }

}
