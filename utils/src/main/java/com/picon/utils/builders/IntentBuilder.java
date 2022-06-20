package com.picon.utils.builders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.converters.Converter;
import com.picon.utils.models.DataType;
import com.picon.utils.models.KeyValue;
import com.picon.utils.validators.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class IntentBuilder extends Intent {

    private static final String TAG = IntentBuilder.class.getSimpleName();

    private IntentBuilder(@Nullable Intent intent) {
        super(intent != null ? intent : new Intent());
    }

    @NonNull
    public static IntentBuilder getInstance() {
        return new IntentBuilder(null);
    }

    @NonNull
    public static IntentBuilder getInstance(@NonNull Intent intent) {
        return new IntentBuilder(intent);
    }

    @NonNull
    public static IntentBuilder getInstance(@NonNull String action) {
        return new IntentBuilder(new Intent(action));
    }

    @NonNull
    public static IntentBuilder getInstance(@NonNull String action, @NonNull Uri uri) {
        return new IntentBuilder(new Intent(action, uri));
    }

    @NonNull
    public static IntentBuilder getInstance(@NonNull Context context, @NonNull Class<?> cls) {
        return new IntentBuilder(new Intent(context, cls));
    }

    @NonNull
    public static IntentBuilder getInstance(@NonNull Context context, @NonNull Class<?> cls, @NonNull String action, @NonNull Uri uri) {
        return new IntentBuilder(new Intent(action, uri, context, cls));
    }

    public IntentBuilder putExtra(@NonNull KeyValue keyValue) {

        try {

            final String key = keyValue.mKey;
            final Object value = keyValue.mValue;
            final DataType type = keyValue.mType;

            if (type == DataType.BOOLEAN) {
                this.putExtra(key, (boolean) value);
            } else if (type == DataType.BUNDLE) {
                this.putExtra(key, (Bundle) value);
            } else if (type == DataType.BYTE) {
                this.putExtra(key, (byte) value);
            } else if (type == DataType.CHARACTER) {
                this.putExtra(key, (char) value);
            } else if (type == DataType.DOUBLE) {
                this.putExtra(key, (double) value);
            } else if (type == DataType.FLOAT) {
                this.putExtra(key, (float) value);
            } else if (type == DataType.INTEGER) {
                this.putExtra(key, (int) value);
            } else if (type == DataType.LONG) {
                this.putExtra(key, (long) value);
            } else if (type == DataType.SHORT) {
                this.putExtra(key, (short) value);
            } else if (type == DataType.STRING) {
                this.putExtra(key, (String) value);
            } else if (type == DataType.PARCELABLE) {
                this.putExtra(key, (Parcelable) value);
            } else if (type == DataType.SERIALIZABLE) {
                this.putExtra(key, (Serializable) value);
            } else if (type == DataType.BOOLEAN_ARRAY) {
                this.putExtra(key, (boolean[]) value);
            } else if (type == DataType.BYTE_ARRAY) {
                this.putExtra(key, (byte[]) value);
            } else if (type == DataType.CHARACTER_ARRAY) {
                this.putExtra(key, (char[]) value);
            } else if (type == DataType.DOUBLE_ARRAY) {
                this.putExtra(key, (double[]) value);
            } else if (type == DataType.FLOAT_ARRAY) {
                this.putExtra(key, (float[]) value);
            } else if (type == DataType.INTEGER_ARRAY) {
                this.putExtra(key, (int[]) value);
            } else if (type == DataType.LONG_ARRAY) {
                this.putExtra(key, (long[]) value);
            } else if (type == DataType.SHORT_ARRAY) {
                this.putExtra(key, (short[]) value);
            } else if (type == DataType.STRING_ARRAY) {
                this.putExtra(key, (String[]) value);
            } else if (type == DataType.PARCELABLE_ARRAY) {
                this.putExtra(key, (Parcelable[]) value);
            }

            if (type == DataType.INTEGER_ARRAY_LIST) {
                this.putIntegerArrayListExtra(key, Converter.toArrayList(Integer.class, (ArrayList<?>) value));
            } else if (type == DataType.STRING_ARRAY_LIST) {
                this.putStringArrayListExtra(key, Converter.toArrayList(String.class, (ArrayList<?>) value));
            } else if (type == DataType.PARCELABLE_ARRAY_LIST) {
                this.putParcelableArrayListExtra(key, Converter.toArrayList(Parcelable.class, (ArrayList<?>) value));
            }

            if (type == DataType.SOURCE_FROM_BUNDLE) {
                this.putExtras((Bundle) value);
            } else if (type == DataType.SOURCE_FROM_INTENT) {
                this.putExtras((Intent) value);
            }

        } catch (Exception e) {
            Log.e(TAG, "setValue: " + e.getMessage(), e);
        }

        return this;
    }

    public IntentBuilder putExtras(@NonNull KeyValue... keyValues) {
        return putExtras(new ArrayList<>(Arrays.asList(keyValues)));
    }

    public IntentBuilder putExtras(@NonNull ArrayList<KeyValue> keyValues) {
        for (KeyValue keyValue : keyValues) {
            putExtra(keyValue);
        }
        return this;
    }

    public <T> T getParcelableExtra(@NonNull String key, @NonNull Class<T> cls) {
        Object data = this.getParcelableExtra(key);
        if (cls.isInstance(data)) {
            return cls.cast(data);
        } else {
            return null;
        }
    }

    public <T> T getSerializableExtra(@NonNull String key, @NonNull Class<T> cls) {
        Object data = this.getSerializableExtra(key);
        if (cls.isInstance(data)) {
            return cls.cast(data);
        } else {
            return null;
        }
    }

    public <T> ArrayList<T> getParcelableArrayExtra(@NonNull String key, @NonNull Class<T> cls) {
        ArrayList<T> ts = Converter.toArrayList(cls, this.getParcelableArrayExtra(key));
        if (Validator.isValidList(ts)) {
            return ts;
        } else {
            return Converter.toArrayList(cls, this.getParcelableArrayListExtra(key));
        }
    }

    public Intent build() {
        return this;
    }

}
