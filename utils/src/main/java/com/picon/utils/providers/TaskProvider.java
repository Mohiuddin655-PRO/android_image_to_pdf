package com.picon.utils.providers;

import androidx.annotation.NonNull;

import com.google.firebase.storage.UploadTask;

public class TaskProvider {

    public static boolean isComplete(int initialSize, int progressingSize) {
        return initialSize == progressingSize;
    }

    public static double getProgress(@NonNull UploadTask.TaskSnapshot uploadTaskSnapshot) {
        return (100.0 * uploadTaskSnapshot.getBytesTransferred() / uploadTaskSnapshot.getTotalByteCount());
    }
}
