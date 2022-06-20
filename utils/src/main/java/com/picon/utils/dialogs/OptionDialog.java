package com.picon.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.picon.utils.adapters.OptionAdapter;
import com.picon.utils.databinding.DialogOptionsBinding;
import com.picon.utils.validators.Validator;

public class OptionDialog extends ShareDialog {

    public OptionDialog(@NonNull Context mContext, @StyleRes int theme) {
        super(mContext, theme);
    }

    @NonNull
    public static OptionDialog getInstance(@NonNull Context context, @StyleRes int theme) {
        return new OptionDialog(context, theme);
    }

    public void options(@NonNull OnDataChangeListener<String> listener, @NonNull String... options) {

        if (Validator.isValidList(options)) {

            final DialogOptionsBinding binding = DialogOptionsBinding.inflate(LayoutInflater.from(mContext));
            final Dialog dialog = new Dialog(mContext);

            binding.actionCancel.setOnClickListener(v -> dialog.dismiss());
            binding.recyclerView.setAdapter(new OptionAdapter(options) {
                @Override
                public void onSelect(String item) {
                    listener.onChange(item);
                    dialog.dismiss();
                }
            });

            final Window window = dialog.getWindow();

            if (window != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            dialog.setContentView(binding.getRoot());
            dialog.getContext().setTheme(mTheme);
            dialog.show();
        }
    }

}
