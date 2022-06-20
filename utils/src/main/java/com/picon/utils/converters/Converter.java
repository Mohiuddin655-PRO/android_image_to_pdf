package com.picon.utils.converters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.validators.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Converter {

    public static Bitmap toBitmap(ImageView imageView) {
        if (imageView != null) {
            try {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                return bitmapDrawable.getBitmap();
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Bitmap toBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @NonNull
    public static String toCountingWithPlus(long size, int limit) {
        if (size > limit) {
            return limit + "+";
        } else {
            return size + "";
        }
    }

    public static double toDouble(String s) {
        return Validator.isValidDigit(s) ? Double.parseDouble(s) : 0;
    }

    public static float toFloat(String s) {
        return Validator.isValidDigit(s) ? Float.parseFloat(s) : 0;
    }

    public static int toInt(String s) {
        return Validator.isValidDigit(s) ? Integer.parseInt(s) : 0;
    }

    @Nullable
    public static String toMail(@NonNull String name, @NonNull String domain_name) {
        final String mail = String.format("%s@%s.com", name, domain_name);
        return Validator.isValidEmail(mail) ? mail : null;
    }

    @NonNull
    public static String toString(Object value) {
        return String.valueOf(value);
    }

    public static Uri toUri(@NonNull String url) {
        return Uri.parse(url);
    }

    public static Uri toUri(@NonNull Context context, @NonNull Bitmap bitmap) {
        return toUri(toUrl(context, bitmap));
    }

    public static String toUrl(@NonNull Context context, @NonNull Bitmap bitmap) {
        return toUrl(context, bitmap, null, null);
    }

    public static String toUrl(@NonNull Context context, @NonNull Bitmap bitmap, @Nullable String title, @Nullable String description) {
        return MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, title, description);
    }

    public static String toUserName(@NonNull String name, @NonNull String... regexList) {
        final String user_name = Replacer.toReplace(name).toLowerCase();
        return Replacer.toReplace(user_name, "", regexList);
    }

    public static String toUserName(@NonNull String name, @NonNull ArrayList<String> regexList, @NonNull ArrayList<String> replacements) {
        final String user_name = Replacer.toReplace(name).toLowerCase();
        return Replacer.toReplace(user_name, regexList, replacements);
    }

    @Nullable
    public static Set<String> toHashSet(Collection<String> strings) {
        if (Validator.isValidList(strings)) {
            return new HashSet<>(strings);
        } else {
            return null;
        }
    }

    @NonNull
    public static ArrayList<String> toPathSegments(@NonNull String path) {
        return toArrayList(path, "/");
    }

    @Nullable
    public static ArrayList<String> toArrayList(@NonNull Set<String> strings) {
        if (Validator.isValidList(strings)) {
            return new ArrayList<>(strings);
        } else {
            return null;
        }
    }

    @NonNull
    @SafeVarargs
    public static <T> ArrayList<T> toArrayList(@NonNull T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    @NonNull
    public static ArrayList<String> toArrayList(@NonNull String value) {
        return toArrayList(value, ",");
    }

    @NonNull
    public static ArrayList<String> toArrayList(@NonNull String value, @NonNull String regex) {
        return toArrayList(value, regex, -1);
    }

    @NonNull
    public static ArrayList<String> toArrayList(@NonNull String value, @NonNull String regex, int limit) {
        String[] strings = limit > 0 ? value.split(regex, limit) : value.split(regex);
        return toArrayList(String.class, strings);
    }

    @NonNull
    public static <T> ArrayList<T> toArrayList(@NonNull Class<T> cls, Object[] list) {
        final ArrayList<T> convertedList = new ArrayList<>();
        if (Validator.isValidList(list)) {
            for (Object data : list) {
                if (Validator.isValidObject(cls, data)) {
                    convertedList.add(cls.cast(data));
                }
            }
        }
        return convertedList;
    }

    @NonNull
    public static <T> ArrayList<T> toArrayList(@NonNull Class<T> cls, ArrayList<?> list) {
        final ArrayList<T> convertedList = new ArrayList<>();
        if (Validator.isValidList(list)) {
            for (Object data : list) {
                if (Validator.isValidObject(cls, data)) {
                    convertedList.add(cls.cast(data));
                }
            }
        }
        return convertedList;
    }

    @NonNull
    public static <T> ArrayList<T> toInverterArrayList(@NonNull ArrayList<T> mList) {
        final ArrayList<T> list = new ArrayList<>();
        if (Validator.isValidList(mList)) {
            for (T t : mList) {
                list.add(0, t);
            }
        }
        return list;
    }

    public static <T> long toCountingNumber(@NonNull ArrayList<T> list) {
        if (Validator.isValidList(list)) {
            return list.size();
        } else {
            return 0L;
        }
    }

    @NonNull
    public static <T> String toCountingText(@NonNull ArrayList<T> list) {
        return String.valueOf(toCountingNumber(list));
    }

    @NonNull
    public static String toCountingStateWithTotalSize(int current_state, int total_size) {
        return toCountingStateWithTotalSize(current_state, total_size, "/");
    }

    @NonNull
    public static String toCountingStateWithTotalSize(int current_state, int total_size, @NonNull String separate_sign) {
        return String.format("%s%s%s", current_state, separate_sign, total_size);
    }

    @NonNull
    public static <T> String toCountingNumberWithKMBLetter(@NonNull ArrayList<T> list) {
        return Counter.toKMBCount(toCountingNumber(list));
    }

    @NonNull
    public static <T> String toCountingNumberWithKMLetter(@NonNull ArrayList<T> list) {
        return Counter.toKMCount(toCountingNumber(list));
    }

    @NonNull
    public static <T> String toCountingNumberWithKLetter(@NonNull ArrayList<T> list) {
        return Counter.toKCount(toCountingNumber(list));
    }

    @NonNull
    public static String toCountingNumberWithKMBLetter(long counter, @NonNull String singular_name, @NonNull String plural_name) {
        if (counter > 1) {
            return String.format("%s %s", Counter.toKMBCount(counter), plural_name);
        } else {
            return String.format("%s %s", counter, singular_name);
        }
    }

    public static <T> T toValue(@NonNull Class<T> cls, Object value) {
        return value != null && cls.isInstance(value) ? cls.cast(value) : null;
    }
}
