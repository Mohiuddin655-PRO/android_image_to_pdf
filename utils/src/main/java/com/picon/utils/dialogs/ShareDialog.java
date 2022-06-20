package com.picon.utils.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.picon.utils.constains.ShareType;
import com.picon.utils.models.SharableDataSource;

public class ShareDialog extends AlertDialog {

    public ShareDialog(@NonNull Context mContext, @StyleRes int theme) {
        super(mContext, theme);
    }

    public void share(@NonNull Activity activity, @NonNull SharableDataSource source) {

        try {

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType(source.getType());
            intent.putExtra(Intent.EXTRA_EMAIL, source.getTo_addresses());
            intent.putExtra(Intent.EXTRA_BCC, source.getBcc_addresses());
            intent.putExtra(Intent.EXTRA_CC, source.getCc_addresses());
            intent.putExtra(Intent.EXTRA_SUBJECT, source.getSubject());
            intent.putExtra(Intent.EXTRA_TEXT, source.getText());
            intent.putExtra(Intent.EXTRA_UID, source.getUid());

            if (source.getType().equals(ShareType.ANY)) {
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, source.getStreams());
            } else {
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, source.getStream());
                if (source.getType().equals(ShareType.HTML_TEXT)) {
                    intent.putExtra(Intent.EXTRA_HTML_TEXT, source.getHtml_text());
                }
            }

            activity.startActivity(Intent.createChooser(intent, source.getChooser_title()));

        } catch (Exception e) {
            Log.e(ShareDialog.class.getSimpleName(), "share: " + e.getMessage(), e);
        }

    }

}
