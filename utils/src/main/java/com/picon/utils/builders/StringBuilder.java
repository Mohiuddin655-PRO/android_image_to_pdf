package com.picon.utils.builders;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.picon.utils.constains.FontStyle;

public class StringBuilder {

    private final SpannableStringBuilder builder = new SpannableStringBuilder("");
    private int mFlags = Spannable.SPAN_INCLUSIVE_EXCLUSIVE;

    @NonNull
    public static StringBuilder getInstance() {
        return new StringBuilder();
    }

    @NonNull
    public SpannableStringBuilder getBuilder() {
        return builder;
    }

    public StringBuilder setBuilder(@NonNull SpanBuilder spanBuilder) {
        builder.append(spanBuilder.getBuilder());
        return this;
    }

    public StringBuilder setBackground(@NonNull String text, @ColorInt int color) {
        return setSpan(text, new BackgroundColorSpan(color));
    }

    public StringBuilder setListener(@NonNull String text, @NonNull ClickableSpan listener) {
        return setSpan(text, listener);
    }

    public StringBuilder setFlags(int flags) {
        mFlags = flags;
        return this;
    }

    public StringBuilder setColor(@NonNull String text, @ColorInt int color) {
        return setSpan(text, new ForegroundColorSpan(color));
    }

    public StringBuilder setHighlight(@NonNull String text) {
        int start = builder.length();
        int end = start + text.length();
        builder.append(text).append(" ");
        builder.setSpan(new StyleSpan(FontStyle.BOLD), start, end, mFlags);
        return this;
    }

    public StringBuilder setHighlight(@NonNull String text, @ColorInt int color) {
        int start = builder.length();
        int end = start + text.length();
        builder.append(text).append(" ");
        builder.setSpan(new StyleSpan(FontStyle.BOLD), start, end, mFlags);
        builder.setSpan(new ForegroundColorSpan(color), start, end, mFlags);
        return this;
    }

    public StringBuilder setPlainText(@NonNull String text) {
        builder.append(text).append(" ");
        return this;
    }

    public StringBuilder setSize(@NonNull String text, float size) {
        return setSpan(text, new RelativeSizeSpan(size));
    }

    public StringBuilder setStyle(@NonNull String text, @FontStyle int style) {
        return setSpan(text, new StyleSpan(style));
    }

    public StringBuilder setSpan(@NonNull String text, @NonNull Object what) {
        int start = builder.length();
        int end = start + text.length();
        return setSpan(text, what, start, end);
    }

    public StringBuilder setSpan(@NonNull String text, @NonNull Object what, int flags) {
        int start = builder.length();
        int end = start + text.length();
        return setSpan(text, what, start, end, flags);
    }

    public StringBuilder setSpan(@NonNull String text, @NonNull Object what, int start, int end) {
        return setSpan(text, what, start, end, mFlags);
    }

    public StringBuilder setSpan(@NonNull String text, @NonNull Object what, int start, int end, int flags) {
        builder.append(text).append(" ");
        builder.setSpan(what, start, end, flags);
        return this;
    }

    public StringBuilder setUnderline(@NonNull String text) {
        return setSpan(text, new UnderlineSpan());
    }

    public void build(@NonNull TextView view) {
        view.setText(builder);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

}

