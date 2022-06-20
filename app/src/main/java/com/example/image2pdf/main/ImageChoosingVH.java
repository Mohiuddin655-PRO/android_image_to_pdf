package com.example.image2pdf.main;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.image2pdf.databinding.ItemChoosingPhotoBinding;

public class ImageChoosingVH extends RecyclerView.ViewHolder {

    private final ItemChoosingPhotoBinding mBinging;
    private final OnItemResponseListener mListener;

    public ImageChoosingVH(@NonNull ItemChoosingPhotoBinding binding, @NonNull OnItemResponseListener listener) {
        super(binding.getRoot());
        mBinging = binding;
        mListener = listener;
    }

    public void init(@NonNull Uri uri) {
        mBinging.ivPreview.setImageURI(uri);
    }

    public void listener(@NonNull Uri uri, int position) {
        mBinging.actionCrop.setOnClickListener(view -> mListener.onItemCropRequest(uri, position));
        mBinging.actionEdit.setOnClickListener(view -> mListener.onItemEditRequest(uri, position));
        mBinging.actionRemove.setOnClickListener(view -> mListener.onItemRemoveRequest(uri, position));
    }
}
