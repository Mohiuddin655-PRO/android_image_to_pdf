package com.picon.utils.constains;

import androidx.annotation.StringDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@Documented
@StringDef({
        DataSourceType.EMAIL,
        DataSourceType.HTML_TEXT,
        DataSourceType.MULTIPLE_EMAIL,
        DataSourceType.MULTIPLE_PHOTO,
        DataSourceType.MULTIPLE_VIDEO,
        DataSourceType.SINGLE_PHOTO,
        DataSourceType.SINGLE_VIDEO,
        DataSourceType.PLAIN_TEXT,
        DataSourceType.TEXT_WITH_PHOTO,
        DataSourceType.TEXT_WITH_VIDEO,
})
public @interface DataSourceType {
    String EMAIL = "email";
    String HTML_TEXT = "html";
    String MULTIPLE_EMAIL = "multiple_email";
    String MULTIPLE_PHOTO = "multiple_photo";
    String MULTIPLE_VIDEO = "multiple_video";
    String SINGLE_PHOTO = "single_photo";
    String SINGLE_VIDEO = "single_video";
    String PLAIN_TEXT = "text";
    String TEXT_WITH_PHOTO = "text_with_photo";
    String TEXT_WITH_VIDEO = "text_with_video";
}