package com.picon.utils.providers;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class PathProvider {

    @NonNull
    public static String generatePath(@NonNull String collection_id, @NonNull String document_id) {
        return FirebaseFirestore.getInstance().collection(collection_id).document(document_id).getPath();
    }

    @NonNull
    public static String generatePath(@NonNull String collection_id, @NonNull String document_id, @NonNull FirebaseApp firebaseApp) {
        return FirebaseFirestore.getInstance(firebaseApp).collection(collection_id).document(document_id).getPath();
    }

    @NonNull
    public static String generatePath(@NonNull String collection_id, @NonNull String document_id, @NonNull String parent_path) {
        return parent_path + "/" + collection_id + "/" + document_id;
    }

}
