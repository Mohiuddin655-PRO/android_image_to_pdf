package com.picon.utils.constains;

import androidx.annotation.StringDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@Documented
@StringDef({
        ShareType.ANY,
        ShareType.EMAIL,
        ShareType.HTML_TEXT,
        ShareType.IMAGE,
        ShareType.JSON,
        ShareType.SMS,
        ShareType.PLAIN_TEXT,
        ShareType.VIDEO
})
public @interface ShareType {
    String ANY = "*/*";
    String EMAIL = "message/rfc822";
    String HTML_TEXT = "text/html";
    String JSON = "text/json";
    String SMS = "vnd.android-dir/mms-sms";
    String PLAIN_TEXT = "text/plain";
    String IMAGE = "image/*"; // image/jpg, image/png, image/gif
    String VIDEO = "video/mp4"; // video/mp4, video/3gp
}