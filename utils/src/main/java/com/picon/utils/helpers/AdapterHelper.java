package com.picon.utils.helpers;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.AnimRes;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.picon.utils.constains.Direction;
import com.picon.utils.loaders.AnimationLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AdapterHelper<T, VH extends RecyclerView.ViewHolder, Options> extends RecyclerView.Adapter<VH> implements ViewHolderHelper.OnItemEventListener<T> {

    private static final String TAG_ADAPTER_HELPER = AdapterHelper.class.getSimpleName();

    @NonNull
    private final ArrayList<T> mAddedList = new ArrayList<>();
    @NonNull
    private final ArrayList<T> mCurrentList = new ArrayList<>();
    @NonNull
    private final ArrayList<T> mRemovedList = new ArrayList<>();
    @NonNull
    private final ArrayList<T> mUpdatedList = new ArrayList<>();
    @NonNull
    public ItemTouchCallback mItemTouchCallback = new ItemTouchCallback(Direction.NO_DIRS, Direction.NO_DIRS, null);
    @NonNull
    public ViewHolderHelper.OnItemEventListener<T> mItemEventListener;

    public Options mOptions;

    public int mItemCount = 0;

    public AdapterHelper() {
        mItemEventListener = this;
    }

    public AdapterHelper(@NonNull Collection<T> collection) {
        this();
        setCollection(collection, false);
    }

    public AdapterHelper(@NonNull Options options) {
        this();
        mOptions = options;
    }

    public AdapterHelper(@NonNull Options options, @NonNull Collection<T> collection) {
        this(options);
        setCollection(collection, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @CallSuper
    public void clear() {
        mAddedList.clear();
        mCurrentList.clear();
        mRemovedList.clear();
        mUpdatedList.clear();
        notifyDataSetChanged();
    }

    public boolean isNotMatched(@NonNull T matcher) {
        return !mCurrentList.contains(matcher);
    }

    public boolean isNotMatched(@NonNull Collection<T> collection) {
        return !mCurrentList.containsAll(collection);
    }

    public boolean isNotMatched(@NonNull T matcher, @NonNull Collection<T> collection) {
        return !collection.contains(matcher);
    }

    public boolean isValidPosition(int position) {
        return mCurrentList.size() > position && position > -1;
    }

    @NonNull
    public ArrayList<T> getAddedList() {
        return mAddedList;
    }

    @NonNull
    public ArrayList<T> getCurrentList() {
        return mCurrentList;
    }

    @NonNull
    public ArrayList<T> getCurrentList(int startAt) {
        ArrayList<T> currentList = new ArrayList<>();
        if (isValidPosition(startAt)) {
            for (int index = startAt; startAt < mCurrentList.size(); startAt++) {
                currentList.add(mCurrentList.get(index));
            }
        }
        return currentList;
    }

    @NonNull
    public ArrayList<T> getRemovedList() {
        return mRemovedList;
    }

    @NonNull
    public ArrayList<T> getUpdatedList() {
        return mUpdatedList;
    }

    @NonNull
    public ArrayList<T> getCurrentList(int startAt, int max) {
        ArrayList<T> currentList = new ArrayList<>();
        max = startAt + max;
        if (isValidPosition(startAt)) {
            for (int index = startAt; index < max && isValidPosition(index); index++) {
                currentList.add(mCurrentList.get(index));
            }
        }
        return currentList;
    }

    public T getItem(int position) {
        return isValidPosition(position) ? mCurrentList.get(position) : null;
    }

    public T getLastItem() {
        return isValidPosition(getItemCount() - 1) ? mCurrentList.get(getItemCount() - 1) : null;
    }

    public T getLastItem(int position) {
        return isValidPosition(position) ? mCurrentList.get(position) : null;
    }

    public int getItemPosition(@NonNull T item) {
        return mCurrentList.indexOf(item);
    }

    public int getSuggestedPosition(int position) {
        return isValidPosition(position) ? position : getItemCount();
    }

    public Options getOptions() {
        return mOptions;
    }

    public void setOptions(@NonNull Options options) {
        this.mOptions = options;
    }

    @CallSuper
    @Override
    public int getItemCount() {
        if (mCurrentList.size() > mItemCount && mItemCount > 0) {
            return mItemCount;
        } else {
            return mCurrentList.size();
        }
    }

    public void setItemCount(int size) {
        mItemCount = size;
    }

    public void setCollection(@NonNull Collection<T> collection) {
        setCollection(collection, true);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCollection(@NonNull Collection<T> collection, boolean notify) {
        if (collection.size() > 0) {
            try {
                mCurrentList.clear();
                mCurrentList.addAll(collection);
                if (notify) {
                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.e(TAG_ADAPTER_HELPER, "setCollection: " + e.getMessage(), e);
            }
        }
    }

    public void setItem(@NonNull T item) {
        try {
            mAddedList.add(item);
            mCurrentList.add(item);
            notifyItemInserted(getItemCount());
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItem: " + e.getMessage(), e);
        }
    }

    public void setItem(@NonNull T item, int position) {
        try {
            int index = getSuggestedPosition(position);
            mAddedList.add(index, item);
            mCurrentList.add(index, item);
            notifyItemInserted(index);
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItem: " + e.getMessage(), e);
        }
    }

    public void setItems(@NonNull Collection<T> items) {
        if (items.size() > 0) {
            try {
                mAddedList.addAll(items);
                mCurrentList.addAll(items);
                notifyItemRangeInserted(getItemCount(), items.size());
            } catch (Exception e) {
                Log.e(TAG_ADAPTER_HELPER, "setItems: " + e.getMessage(), e);
            }
        }
    }

    @SafeVarargs
    public final void setItems(@NonNull T... items) {
        if (items.length > 0) {
            setItems(Arrays.asList(items));
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        if (isValidPosition(fromPosition)) {
            try {
                T item = getItem(fromPosition);
                mCurrentList.remove(item);
                mCurrentList.add(toPosition, item);
                notifyItemMoved(fromPosition, toPosition);
            } catch (Exception e) {
                Log.e(TAG_ADAPTER_HELPER, "moveItem: " + e.getMessage(), e);
            }
        }
    }

    public void removeItem(int position) {
        if (isValidPosition(position)) {
            removeItem(getItem(position));
        }
    }

    public void removeItem(@NonNull T item) {
        if (mCurrentList.size() > 0 && mCurrentList.contains(item)) {
            try {
                notifyItemRemoved(getItemPosition(item));
                mCurrentList.remove(item);
                mRemovedList.add(0, item);
            } catch (Exception e) {
                Log.e(TAG_ADAPTER_HELPER, "removeItem: " + e.getMessage(), e);
            }
        }
    }

    public void removeItems(@NonNull Collection<T> items) {
        if (items.size() > 0) {
            for (T item : items) {
                removeItem(item);
            }
        }
    }

    @SafeVarargs
    public final void removeItems(@NonNull T... items) {
        if (items.length > 0) {
            removeItems(Arrays.asList(items));
        }
    }

    public void updateValue(@NonNull T item, int index) {
        if (mCurrentList.size() > index) {
            mCurrentList.remove(index);
            mCurrentList.add(index, item);
            mUpdatedList.add(0, item);
        }
    }

    public void updateItem(@NonNull T newItem, @NonNull T oldItem) {
        updateItem(newItem, oldItem, getItemPosition(oldItem));
    }

    public void updateItem(@NonNull T newItem, @NonNull T oldItem, int position) {
        if (isValidPosition(position) && mCurrentList.contains(oldItem)) {
            try {
                notifyItemChanged(position);
                mCurrentList.remove(position);
                mCurrentList.add(position, newItem);
                mUpdatedList.add(0, newItem);
            } catch (Exception e) {
                Log.e(TAG_ADAPTER_HELPER, "updateItem: " + e.getMessage(), e);
            }
        }
    }

    public void updateItems(@NonNull List<T> newItems, @NonNull List<T> oldItems) {
        if (newItems.size() > 0 && newItems.size() == oldItems.size()) {
            for (int index = 0; index < newItems.size() && index < oldItems.size(); index++) {
                updateItem(newItems.get(index), oldItems.get(index));
            }
        }
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, getItem(position));
    }

    public abstract void onBindViewHolder(@NonNull VH holder, int position, T t);

    public void setAnimation(@NonNull RecyclerView view, @AnimRes int animation) {
        AnimationLoader.layoutAnimation(view, animation);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView) {
        new ItemTouchHelper(mItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView, @NonNull ItemTouchCallback mCallback) {
        mItemTouchCallback = mCallback;
        new ItemTouchHelper(mItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView, @NonNull OnItemTouchCallbackListener mListener) {
        mItemTouchCallback.setListener(mListener);
        new ItemTouchHelper(mItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @Direction int swipeDirs) {
        mItemTouchCallback.setDefaultDragDirs(dragDirs);
        mItemTouchCallback.setDefaultSwipeDirs(swipeDirs);
        new ItemTouchHelper(mItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @NonNull OnItemTouchCallbackListener mListener) {
        setItemTouchCallback(recyclerView, dragDirs, Direction.NO_DIRS, mListener);
    }

    public void setItemTouchCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @Direction int swipeDirs, @NonNull OnItemTouchCallbackListener mListener) {
        mItemTouchCallback.setListener(mListener);
        mItemTouchCallback.setDefaultDragDirs(dragDirs);
        mItemTouchCallback.setDefaultSwipeDirs(swipeDirs);
        new ItemTouchHelper(mItemTouchCallback).attachToRecyclerView(recyclerView);
    }

    public interface OnItemTouchCallbackListener {

        default boolean onItemMove(int fromPosition, int toPosition) {
            return false;
        }

        default void onItemSwipe(int position, @Direction int direction) {
        }

        default void onItemSwipeUp(int position) {
        }

        default void onItemSwipeDown(int position) {
        }

        default void onItemSwipeLeft(int position) {
        }

        default void onItemSwipeRight(int position) {
        }

        default void onItemSwipeHorizontal(int position) {
        }

        default void onItemSwipeVertical(int position) {
        }
    }

    public static class ItemTouchCallback extends ItemTouchHelper.SimpleCallback {

        private OnItemTouchCallbackListener mListener;

        public ItemTouchCallback(@Direction int dragDirs, @Direction int swipeDirs, @Nullable OnItemTouchCallbackListener mListener) {
            super(dragDirs, swipeDirs);
            this.mListener = mListener;
        }

        public void setListener(@NonNull OnItemTouchCallbackListener mListener) {
            this.mListener = mListener;
            onSwiped(null, Direction.NO_DIRS);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return mListener != null && mListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onSwiped(@Nullable RecyclerView.ViewHolder viewHolder, @Direction int direction) {
            if (mListener != null && viewHolder != null) {
                int position = viewHolder.getAdapterPosition();
                this.mListener.onItemSwipe(position, direction);
                switch (direction) {
                    case Direction.UP:
                        this.mListener.onItemSwipeUp(position);
                        this.mListener.onItemSwipeVertical(position);
                        break;
                    case Direction.DOWN:
                        this.mListener.onItemSwipeDown(position);
                        this.mListener.onItemSwipeVertical(position);
                        break;
                    case Direction.VERTICAL_DIRS:
                        this.mListener.onItemSwipeVertical(position);
                        break;
                    case Direction.LEFT:
                        this.mListener.onItemSwipeLeft(position);
                        this.mListener.onItemSwipeHorizontal(position);
                        break;
                    case Direction.RIGHT:
                        this.mListener.onItemSwipeRight(position);
                        this.mListener.onItemSwipeHorizontal(position);
                        break;
                    case Direction.HORIZONTAL_DIRS:
                        this.mListener.onItemSwipeHorizontal(position);
                        break;
                    case Direction.NO_DIRS:
                        break;
                    case Direction.DYNAMIC_DIRS:
                    default:
                        this.mListener.onItemSwipe(position, direction);
                }
            }
        }

    }

}
