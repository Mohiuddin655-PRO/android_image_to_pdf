package com.picon.utils.helpers;

import static android.view.View.NO_ID;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.picon.utils.R;
import com.picon.utils.loaders.AnimationLoader;

public abstract class ViewHolderHelper<T, Options> extends RecyclerView.ViewHolder {

    public Options options;
    private T item;

    public ViewHolderHelper(@NonNull View view) {
        super(view);
    }

    public ViewHolderHelper(@NonNull View view, @NonNull Options options) {
        super(view);
        this.init(options);
    }

    public void addItem(@NonNull T t) {
        addItem(t, null);
    }

    public void addItem(@NonNull T t, @Nullable View view) {
    }

    public void removeItem(@NonNull T t) {
        removeItem(t, null);
    }

    public void removeItem(@NonNull T t, @Nullable View view) {
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(@NonNull Options options) {
        this.options = options;
    }

    public final <V extends View> V getView(@IdRes int id) {
        return id != NO_ID ? itemView.findViewById(id) : null;
    }

    public T getItem() {
        return item;
    }

    public void setItem(@NonNull T item) {
        this.item = item;
    }

    public void init(@NonNull Options options) {
        this.setOptions(options);
    }

    public void load(@NonNull T item) {
        this.setItem(item);
        this.onBind(this.item);
        this.onListener(this.item);
        this.onViewAnimation();
    }

    protected abstract void onBind(@NonNull T t);

    protected void onListener(@NonNull T t) {
    }

    protected void onViewAnimation() {
    }

    public void setAnimation() {
        setAnimation(R.anim.item_fall_down);
    }

    public void setAnimation(@AnimRes int anim) {
        setAnimation(itemView, anim);
    }

    public void setAnimation(@NonNull View view, @AnimRes int anim) {
        AnimationLoader.itemAnimation(view, anim);
    }

    public interface OnItemEventListener<T> {

        default void onItemClick(@NonNull View view, @NonNull T t, int position) {
        }

        default boolean onItemLongClick(@NonNull View view, @NonNull T t, int position) {
            return false;
        }

        default void onItemDoubleClick(@NonNull View view, @NonNull T t, int position) {
        }

        default void onItemChance(@NonNull T t, int position) {
        }

        default void onItemInsertRequest(@NonNull T t, int position) {
        }

        default void onItemFeedback(@NonNull T t, @NonNull View view) {
        }

        default void onItemRemoveRequest(@NonNull T t, int position) {
        }

        default void onItemCustomizeRequest(@NonNull T t, int position) {
        }

        default void onItemDeleteRequest(@NonNull T t, int position) {
        }

        default void onItemDownloadRequest(@NonNull T t, @NonNull View view) {
        }

        default void onItemFollowRequest(@NonNull T t, @NonNull View view, boolean isFollowing) {
        }

        default void onItemReportRequest(@NonNull T t, boolean isAlreadyReported) {
        }

        default void onItemShareRequest(@NonNull T t, @NonNull View view) {
        }

        default void onItemTranslateRequest(@NonNull T t, @NonNull TextView... textViews) {
        }

        default void onItemUpdateRequest(@NonNull T t, int position) {
        }

        default void onItemVisitProfileRequest(@NonNull T t, @NonNull View view) {
        }
    }

    public static class Options<T> {

        public final Activity mActivity;
        public final LifecycleOwner mLifecycleOwner;
        public final OnItemEventListener<T> mListener;

        public Options(@NonNull Activity mActivity, @NonNull OnItemEventListener<T> mListener) {
            this.mActivity = mActivity;
            this.mLifecycleOwner = (LifecycleOwner) mActivity;
            this.mListener = mListener;
        }
    }
}
