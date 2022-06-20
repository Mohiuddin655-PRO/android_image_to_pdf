package com.picon.utils.builders;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.picon.utils.constains.FontStyle;

public class SpanBuilder {

    private final SpannableStringBuilder builder = new SpannableStringBuilder();
    private int mFlags = Spannable.SPAN_INCLUSIVE_EXCLUSIVE;

    public SpanBuilder(@NonNull String text) {
        this.builder.append(text);
    }

    public static SpanBuilder getInstance(@NonNull String text) {
        return new SpanBuilder(text);
    }

    @NonNull
    public SpannableStringBuilder getBuilder() {
        return builder;
    }

    public SpanBuilder attachBackground(@ColorInt int color) {
        return attachSpan(new BackgroundColorSpan(color));
    }

    public SpanBuilder attachListener(@NonNull ClickableSpan span) {
        return attachSpan(span);
    }

    public SpanBuilder attachColor(@ColorInt int color) {
        return attachSpan(new ForegroundColorSpan(color));
    }

    public SpanBuilder attachHighlight() {
        builder.setSpan(new StyleSpan(FontStyle.BOLD), 0, builder.length(), mFlags);
        return this;
    }

    public SpanBuilder attachHighlight(@ColorInt int color) {
        builder.setSpan(new StyleSpan(FontStyle.BOLD), 0, builder.length(), mFlags);
        builder.setSpan(new ForegroundColorSpan(color), 0, builder.length(), mFlags);
        return this;
    }

    public SpanBuilder attachSize(float size) {
        return attachSpan(new RelativeSizeSpan(size));
    }

    public SpanBuilder attachStyle(@FontStyle int style) {
        return attachSpan(new StyleSpan(style));
    }

    public SpanBuilder attachSpan(@NonNull Object what) {
        return attachSpan(what, 0, builder.length(), mFlags);
    }

    public SpanBuilder attachSpan(@NonNull Object what, int flags) {
        return attachSpan(what, 0, builder.length(), flags);
    }

    public SpanBuilder attachSpan(@NonNull Object what, int start, int end) {
        return attachSpan(what, start, end, mFlags);
    }

    public SpanBuilder attachSpan(@NonNull Object what, int start, int end, int flags) {
        builder.setSpan(what, start, end, flags);
        return this;
    }

    public SpanBuilder attachUnderline() {
        return attachSpan(new UnderlineSpan());
    }

    public SpanBuilder setFlags(int flags) {
        mFlags = flags;
        return this;
    }
}