package com.picon.utils.constains;

import android.graphics.Typeface;

import androidx.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@Documented
@IntDef({
        FontStyle.NORMAL,
        FontStyle.BOLD,
        FontStyle.ITALIC,
        FontStyle.BOLD_ITALIC,
})
public @interface FontStyle {
    int NORMAL = Typeface.NORMAL;
    int BOLD = Typeface.BOLD;
    int ITALIC = Typeface.ITALIC;
    int BOLD_ITALIC = Typeface.BOLD_ITALIC;
}