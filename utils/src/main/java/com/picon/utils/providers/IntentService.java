package com.picon.utils.providers;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.picon.utils.constains.DataSourceType;
import com.picon.utils.constains.ShareType;

import java.util.ArrayList;

public class IntentService {

    private final String mType;
    private final Intent mIntentData;
    private final OnIntentServiceListener listener;
    private final boolean validServiceData;
    private final String mUid;
    private String mSourceType;
    private boolean noServiceAvailable;

    private IntentService(@NonNull Intent intent, @NonNull OnIntentServiceListener listener) {
        String mAction = intent.getAction();
        this.mType = intent.getType();
        this.mIntentData = intent;
        this.listener = listener;
        this.mUid = mIntentData.getStringExtra(Intent.EXTRA_UID);
        this.validServiceData = mType != null && mAction != null && (mAction.equals(Intent.ACTION_SEND) || mAction.equals(Intent.ACTION_SEND_MULTIPLE));
        this.noServiceAvailable = !validServiceData;
    }

    @NonNull
    public static IntentService getInstance(@NonNull Intent intent, @NonNull OnIntentServiceListener listener) {
        return new IntentService(intent, listener);
    }

    public IntentService handleFor(@DataSourceType String type) {

        this.mSourceType = type;

        if (validServiceData && type != null) {

            if (type.equals(DataSourceType.PLAIN_TEXT) && mType.equals(ShareType.PLAIN_TEXT)) {
                String data = mIntentData.getStringExtra(Intent.EXTRA_TEXT);
                if (data != null) {
                    listener.onPlainTextService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.HTML_TEXT) && mType.equals(ShareType.HTML_TEXT)) {
                String data = mIntentData.getStringExtra(Intent.EXTRA_HTML_TEXT);
                if (data != null) {
                    listener.onHtmlTextService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.EMAIL) && mType.equals(ShareType.EMAIL)) {
                String data = mIntentData.getStringExtra(Intent.EXTRA_EMAIL);
                if (data != null) {
                    listener.onEmailService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.MULTIPLE_EMAIL) && mType.equals(ShareType.EMAIL)) {
                String[] data = mIntentData.getStringArrayExtra(Intent.EXTRA_EMAIL);
                if (data != null) {
                    listener.onEmailService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.SINGLE_PHOTO) && mType.startsWith(ShareType.IMAGE)) {
                Uri data = mIntentData.getParcelableExtra(Intent.EXTRA_STREAM);
                if (data != null) {
                    listener.onPhotoService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.MULTIPLE_PHOTO) && mType.equals(ShareType.IMAGE)) {
                ArrayList<Uri> data = mIntentData.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                if (data != null) {
                    listener.onPhotoService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.SINGLE_VIDEO) && mType.equals(ShareType.VIDEO)) {
                Uri uri = mIntentData.getParcelableExtra(Intent.EXTRA_STREAM);
                if (uri != null) {
                    listener.onVideoService(uri);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.MULTIPLE_VIDEO) && mType.equals(ShareType.VIDEO)) {
                ArrayList<Uri> data = mIntentData.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                if (data != null) {
                    listener.onVideoService(data);
                } else {
                    noServiceAvailable = true;
                }
            } else if (type.equals(DataSourceType.TEXT_WITH_PHOTO) && (mType.equals(ShareType.IMAGE) || mType.equals(ShareType.PLAIN_TEXT))) {

                ArrayList<Uri> uris = new ArrayList<>();

                String subject = mIntentData.getStringExtra(Intent.EXTRA_SUBJECT);
                String text = mIntentData.getStringExtra(Intent.EXTRA_TEXT);

                Uri uri = mIntentData.getParcelableExtra(Intent.EXTRA_STREAM);

                if (uri != null) {
                    uris.add(uri);
                } else {
                    uris = mIntentData.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                }

                if (text != null || uris != null) {
                    listener.onTextWithPhotoService(subject, text, uris);
                } else {
                    noServiceAvailable = true;
                }

            } else if (type.equals(DataSourceType.TEXT_WITH_VIDEO) && (mType.equals(ShareType.VIDEO) || mType.equals(ShareType.PLAIN_TEXT))) {

                ArrayList<Uri> uris = new ArrayList<>();

                String subject = mIntentData.getStringExtra(Intent.EXTRA_SUBJECT);
                String text = mIntentData.getStringExtra(Intent.EXTRA_TEXT);

                Uri uri = mIntentData.getParcelableExtra(Intent.EXTRA_STREAM);

                if (uri != null) {
                    uris.add(uri);
                } else {
                    uris = mIntentData.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                }

                if (text != null || uris != null) {
                    listener.onTextWithVideoService(subject, text, uris);
                } else {
                    noServiceAvailable = true;
                }

            } else {
                noServiceAvailable = true;
            }

        } else {
            noServiceAvailable = true;
        }

        return this;
    }

    public boolean isServiceNotAvailable() {
        return noServiceAvailable || mSourceType == null;
    }

    public boolean isCurrentUid(@NonNull String uid) {
        return uid.equals(mUid);
    }

    public String getUid() {
        return mUid;
    }

    public interface OnIntentServiceListener {

        default void onEmailService(@NonNull String data) {
        }

        default void onEmailService(@NonNull String[] data) {
        }

        default void onHtmlTextService(@NonNull String data) {
        }

        default void onPlainTextService(@NonNull String data) {
        }

        default void onPhotoService(@NonNull Uri data) {
        }

        default void onPhotoService(@NonNull ArrayList<Uri> data) {
        }

        default void onVideoService(@NonNull Uri data) {
        }

        default void onVideoService(@NonNull ArrayList<Uri> data) {
        }

        default void onTextWithPhotoService(String subject, String text, ArrayList<Uri> uris) {
        }

        default void onTextWithVideoService(String subject, String text, ArrayList<Uri> uris) {
        }

    }

}
