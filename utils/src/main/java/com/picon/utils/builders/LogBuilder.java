package com.picon.utils.builders;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LogBuilder {

    private final String mTag;
    private final java.lang.StringBuilder mBuilder;
    private String mAttachingSeparator, mPuttingSeparator;
    private String mStartingSign;
    private String mLineBreak;

    private LogBuilder(@NonNull String tag) {
        this.mTag = tag;
        this.mLineBreak = "\n";
        this.mBuilder = new java.lang.StringBuilder();
        this.mBuilder.append(tag).append("\n {").append(mLineBreak);
        this.mAttachingSeparator = " = ";
        this.mPuttingSeparator = " : ";
        this.mStartingSign = "  -> ";
    }

    @NonNull
    public static LogBuilder getInstance(@NonNull String tag) {
        return new LogBuilder(tag);
    }

    public LogBuilder attach(@NonNull String name, @Nullable Object value) {
        mBuilder.append(name).append(mAttachingSeparator).append(value).append(", ");
        return this;
    }

    public LogBuilder attachEnd(@NonNull String name, @Nullable Object value) {
        mBuilder.append(name).append(mAttachingSeparator).append(value).append(" ]").append(mLineBreak);
        return this;
    }

    public LogBuilder attachStart(@NonNull String name) {
        mBuilder.append(mStartingSign).append(name).append(mPuttingSeparator).append("[ ");
        return this;
    }

    public LogBuilder setAttachSeparator(@NonNull String separator) {
        this.mAttachingSeparator = separator;
        return this;
    }

    public LogBuilder put(@NonNull String name, @Nullable List<?> data) {
        if (data != null && data.size() > 0) {
            mBuilder.append(mStartingSign).append(name).append(mPuttingSeparator);
            if (data.size() > 3) {
                mBuilder.append("[ ")
                        .append(data.get(0)).append(", ")
                        .append(data.get(1)).append(", ")
                        .append(data.get(2)).append("... ")
                        .append(data.size())
                        .append(" ]");
            } else {
                mBuilder.append(data);
            }
            mBuilder.append(mLineBreak);
            return this;
        } else {
            return put(name, "null");
        }

    }

    public LogBuilder put(@NonNull String name, @Nullable Object value) {
        if (value instanceof List<?>) {
            return put(name, (List<?>) value);
        } else {
            mBuilder.append(mStartingSign).append(name).append(mPuttingSeparator).append(value).append(mLineBreak);
        }
        return this;
    }

    public LogBuilder setPutSeparator(@NonNull String separator) {
        this.mPuttingSeparator = separator;
        return this;
    }

    public LogBuilder setStartingSign(@NonNull String sign) {
        this.mStartingSign = sign;
        return this;
    }

    public LogBuilder setLineBreak(@NonNull String lineBreak) {
        this.mLineBreak = lineBreak;
        return this;
    }

    public void build(@NonNull Type type) {

        mBuilder.append("}\n\n");

        String msg = mBuilder.toString();

        switch (type) {
            case E:
                Log.e(mTag, msg);
                break;
            case I:
                Log.i(mTag, msg);
                break;
            case V:
                Log.v(mTag, msg);
                break;
            case W:
                Log.w(mTag, msg);
                break;
            default:
                Log.d(mTag, msg);
        }
    }

    public void build() {
        build(Type.D);
    }

    public enum Type {
        D, I, E, V, W
    }

}