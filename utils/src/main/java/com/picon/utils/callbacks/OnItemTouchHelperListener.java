package com.picon.utils.callbacks;

public interface OnItemTouchHelperListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);
}