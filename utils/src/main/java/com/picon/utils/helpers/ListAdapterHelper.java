package com.picon.utils.helpers;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.picon.utils.constains.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class ListAdapterHelper<T, VH extends RecyclerView.ViewHolder, Options extends Void> extends ListAdapter<T, VH> {

    private static final String TAG_ADAPTER_HELPER = ListAdapterHelper.class.getSimpleName();
    private final ArrayList<T> mAddedList = new ArrayList<>();
    private final ArrayList<T> mRemovedList = new ArrayList<>();
    private final ArrayList<T> mUpdatedList = new ArrayList<>();

    public Callback mCallback = new Callback(Direction.NO_DIRS, Direction.NO_DIRS, null);

    private Options options;

    public ListAdapterHelper(@NonNull DiffUtil.ItemCallback<T> callback) {
        super(callback);
    }

    public ListAdapterHelper(@NonNull DiffUtil.ItemCallback<T> callback, @NonNull Options options) {
        this(callback);
        this.options = options;
    }

    public ListAdapterHelper(@NonNull DiffUtil.ItemCallback<T> callback, @NonNull Options options, @NonNull ArrayList<T> list) {
        this(callback, options);
        this.submitList(list);
    }

    public void clear() {
        getAddedList().clear();
        getCurrentList().clear();
        getRemovedList().clear();
        getUpdatedList().clear();
    }

    public void refresh() {
        clear();
        setList(new ArrayList<>());
    }

    public ArrayList<T> getCurrentList(int startAt) {
        ArrayList<T> currentList = new ArrayList<>();
        if (isValidPosition(startAt)) {
            for (int index = startAt; startAt < getCurrentList().size(); startAt++) {
                currentList.add(getCurrentList().get(index));
            }
        }
        return currentList;
    }

    public List<T> getCurrentList(int startAt, int max) {
        List<T> currentList = new ArrayList<>();
        max = startAt + max;
        if (isValidPosition(startAt)) {
            for (int index = startAt; index < max && isValidPosition(index); index++) {
                currentList.add(getCurrentList().get(index));
            }
        }
        return currentList;
    }

    @NonNull
    public ArrayList<T> getAddedList() {
        return mAddedList;
    }

    @NonNull
    public ArrayList<T> getRemovedList() {
        return mRemovedList;
    }

    @NonNull
    public ArrayList<T> getUpdatedList() {
        return mUpdatedList;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(@NonNull Options options) {
        this.options = options;
    }

    public T getItem(int position) {
        return isValidPosition(position) ? getCurrentList().get(position) : null;
    }

    public T getLastItem() {
        return isValidPosition(getItemCount() - 1) ? getCurrentList().get(getItemCount() - 1) : null;
    }

    public T getLastItem(int position) {
        return isValidPosition(position) ? getCurrentList().get(position) : null;
    }

    public boolean isValidPosition(int position) {
        return getCurrentList().size() > position && position > -1;
    }

    public boolean isNotMatched(@NonNull T matcher) {
        return !getCurrentList().contains(matcher);
    }

    public boolean isNotMatched(@NonNull Collection<T> list) {
        return !getCurrentList().containsAll(list);
    }

    public boolean isNotMatched(@NonNull Object matcher, @NonNull ArrayList<?> list) {
        return !list.contains(matcher);
    }

    public int getItemPosition(@NonNull T item) {
        return getCurrentList().indexOf(item);
    }

    public int getSuggestedPosition(int position) {
        return isValidPosition(position) ? position : getItemCount();
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(@NonNull ArrayList<T> mList) {
        this.submitList(mList);
        this.notifyDataSetChanged();
    }

    public void setItem(@NonNull T item) {
        try {
            getCurrentList().add(item);
            this.mAddedList.add(0, item);
            this.notifyItemInserted(getItemCount());
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItem: " + e.getMessage(), e);
        }
    }

    public void setItem(@NonNull T item, int position) {
        try {
            this.getCurrentList().add(getSuggestedPosition(position), item);
            this.mAddedList.add(0, item);
            this.notifyItemInserted(getSuggestedPosition(position));
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItem: " + e.getMessage(), e);
        }
    }

    public void setItems(@NonNull ArrayList<T> list) {
        this.getCurrentList();
        try {
            this.getCurrentList().addAll(list);
            this.mAddedList.addAll(0, list);
            this.notifyItemRangeInserted(getItemCount(), list.size());
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItems: " + e.getMessage(), e);
        }
    }

    @SafeVarargs
    public final void setItems(@NonNull T... items) {
        try {
            this.getCurrentList().addAll(Arrays.asList(items));
            this.mAddedList.addAll(0, Arrays.asList(items));
            this.notifyItemRangeInserted(getItemCount(), items.length);
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "setItems: " + e.getMessage(), e);
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        try {
            final T item = this.getItem(fromPosition);
            if (isValidPosition(fromPosition)) {
                this.getCurrentList().remove(item);
                this.getCurrentList().add(toPosition, item);
                this.notifyItemMoved(fromPosition, toPosition);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "moveItem: " + e.getMessage(), e);
        }
    }

    public void removeItem(int position) {
        try {
            if (isValidPosition(position)) {
                this.notifyItemRemoved(position);
                this.getCurrentList().remove(position);
                this.mRemovedList.add(0, getItem(position));
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "removeItem: " + e.getMessage(), e);
        }
    }

    public void removeItem(@NonNull T item) {
        try {
            if (getCurrentList().size() > 0 && getCurrentList().contains(item)) {
                this.notifyItemRemoved(getItemPosition(item));
                this.getCurrentList().remove(item);
                this.mRemovedList.add(0, item);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "removeItem: " + e.getMessage(), e);
        }
    }

    public void removeItems(@NonNull ArrayList<T> items) {
        try {
            for (T item : items) {
                removeItem(item);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "removeItem: " + e.getMessage(), e);
        }
    }

    @SafeVarargs
    public final void removeItems(@NonNull T... items) {
        try {
            for (T item : items) {
                removeItem(item);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "removeItem: " + e.getMessage(), e);
        }
    }

    public void updateValue(@NonNull T item, int index) {
        try {
            if (index < getCurrentList().size()) {
                this.getCurrentList().remove(index);
                this.getCurrentList().add(index, item);
                this.mUpdatedList.add(0, item);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "updateItem: " + e.getMessage(), e);
        }
    }

    public void updateItem(@NonNull T newItem, @NonNull T oldItem) {
        try {
            if (getCurrentList().size() > 0 && getCurrentList().contains(oldItem)) {
                int position = getItemPosition(oldItem);
                this.notifyItemChanged(position);
                this.getCurrentList().remove(position);
                this.getCurrentList().add(position, newItem);
                this.mUpdatedList.add(0, newItem);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "updateItem: " + e.getMessage(), e);
        }
    }

    public void updateItem(@NonNull T newItem, @NonNull T oldItem, int position) {
        try {
            if (getCurrentList().size() > 0 && getCurrentList().contains(oldItem)) {
                this.notifyItemChanged(position);
                this.getCurrentList().remove(position);
                this.getCurrentList().add(position, newItem);
                this.mUpdatedList.add(0, newItem);
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "updateItem: " + e.getMessage(), e);
        }
    }

    public void updateItems(@NonNull ArrayList<T> newItems, @NonNull ArrayList<T> oldItems) {
        try {
            for (int index = 0; index < newItems.size() && index < oldItems.size(); index++) {
                updateItem(newItems.get(index), oldItems.get(index));
            }
        } catch (Exception e) {
            Log.e(TAG_ADAPTER_HELPER, "updateItem: " + e.getMessage(), e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, getItem(position));
    }

    public abstract void onBindViewHolder(@NonNull VH holder, int position, T t);

    public void setCallback(@NonNull RecyclerView recyclerView) {
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public void setCallback(@NonNull RecyclerView recyclerView, @NonNull Callback mCallback) {
        this.mCallback = mCallback;
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public void setCallback(@NonNull RecyclerView recyclerView, @NonNull OnCallbackListener mListener) {
        this.mCallback.setListener(mListener);
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public void setCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @Direction int swipeDirs) {
        this.mCallback.setDefaultDragDirs(dragDirs);
        this.mCallback.setDefaultSwipeDirs(swipeDirs);
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public void setCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @NonNull OnCallbackListener mListener) {
        this.mCallback.setListener(mListener);
        this.mCallback.setDefaultDragDirs(dragDirs);
        this.mCallback.setDefaultSwipeDirs(Direction.NO_DIRS);
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public void setCallback(@NonNull RecyclerView recyclerView, @Direction int dragDirs, @Direction int swipeDirs, @NonNull OnCallbackListener mListener) {
        this.mCallback.setListener(mListener);
        this.mCallback.setDefaultDragDirs(dragDirs);
        this.mCallback.setDefaultSwipeDirs(swipeDirs);
        new ItemTouchHelper(this.mCallback).attachToRecyclerView(recyclerView);
    }

    public interface OnCallbackListener {

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

    public static class Callback extends ItemTouchHelper.SimpleCallback {

        private OnCallbackListener mListener;

        public Callback(@Direction int dragDirs, @Direction int swipeDirs, @Nullable OnCallbackListener mListener) {
            super(dragDirs, swipeDirs);
            this.mListener = mListener;
        }

        public void setListener(@NonNull OnCallbackListener mListener) {
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
