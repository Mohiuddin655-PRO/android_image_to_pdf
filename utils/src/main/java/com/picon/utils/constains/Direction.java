package com.picon.utils.constains;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.ItemTouchHelper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@Documented
@IntDef({Direction.NO_DIRS, Direction.DYNAMIC_DIRS, Direction.UP, Direction.DOWN, Direction.VERTICAL_DIRS, Direction.LEFT, Direction.RIGHT, Direction.HORIZONTAL_DIRS})
public @interface Direction {
    int NO_DIRS = 0;
    int DYNAMIC_DIRS = -1;
    int UP = ItemTouchHelper.UP;
    int DOWN = ItemTouchHelper.DOWN;
    int VERTICAL_DIRS = Direction.UP | Direction.DOWN;
    int LEFT = ItemTouchHelper.LEFT | ItemTouchHelper.START;
    int RIGHT = ItemTouchHelper.RIGHT | ItemTouchHelper.END;
    int START = LEFT;
    int END = Direction.RIGHT;
    int HORIZONTAL_DIRS = Direction.LEFT | Direction.RIGHT;
}