package com.picon.utils.dialogs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

public class BaseBottomSheetDialog extends LoadingDialog {

    public BaseBottomSheetDialog(@NonNull Context mContext, @StyleRes int mTheme) {
        super(mContext, mTheme);
    }

}
