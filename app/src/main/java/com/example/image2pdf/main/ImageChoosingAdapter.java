package com.example.image2pdf.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.image2pdf.databinding.ItemChoosingPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageChoosingAdapter extends RecyclerView.Adapter<ImageChoosingVH> {

    private static final String TAG = ImageChoosingAdapter.class.getSimpleName();

    private final OnItemResponseListener mListener;

    private List<Uri> mItems = new ArrayList<>();

    public ImageChoosingAdapter(@NonNull OnItemResponseListener listener) {
        mListener = listener;
    }

    public void submit(@NonNull List<Uri> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    public List<Uri> getCurrentItems() {
        return mItems != null ? mItems : new ArrayList<>();
    }

    @NonNull
    @Override
    public ImageChoosingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChoosingPhotoBinding binding = ItemChoosingPhotoBinding.inflate(inflater, parent, false);
        return new ImageChoosingVH(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageChoosingVH holder, int position) {
        holder.init(getItemAt(position));
        holder.listener(getItemAt(position), position);
    }

    @NonNull
    public Uri getItemAt(int position) {
        return getCurrentItems().get(position);
    }

    public void insertAt(@NonNull Uri uri) {
        mItems.add(uri);
        notifyItemInserted(getItemCount());
        mListener.onItemChangeResponse(getItemCount());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void insertAt(@NonNull Uri uri, int position) {
        if (getItemCount() > position) {
            mItems.add(position, uri);
        } else {
            mItems.add(0, uri);
        }
        notifyDataSetChanged();
        mListener.onItemChangeResponse(getItemCount());
    }

    public void removeAt(@NonNull Uri uri) {
        if (getItemCount() > 0) {
            int position = mItems.indexOf(uri);
            mItems.remove(position);
            notifyItemRemoved(position);
            mListener.onItemChangeResponse(getItemCount());
        }
    }

    public void updateAt(@NonNull Uri oldUri, @NonNull Uri newUri) {
        if (mItems != null && mItems.contains(oldUri)) {
            try {
                int position = mItems.indexOf(oldUri);
                notifyItemChanged(position);
                mItems.remove(oldUri);
                mItems.add(position, newUri);
                mListener.onItemChangeResponse(getItemCount());
            } catch (Exception e) {
                Log.e(TAG, "updateAt: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

}
