package com.example.image2pdf.main;

import android.net.Uri;

import androidx.annotation.NonNull;

public interface OnItemResponseListener {

    void onItemCropRequest(@NonNull Uri uri, int position);

    void onItemEditRequest(@NonNull Uri uri, int position);

    void onItemRemoveRequest(@NonNull Uri uri, int position);

    void onItemChangeResponse(int itemCount);
}
