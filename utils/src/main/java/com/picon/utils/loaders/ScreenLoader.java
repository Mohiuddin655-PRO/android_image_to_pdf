package com.picon.utils.loaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.R;
import com.picon.utils.builders.IntentBuilder;
import com.picon.utils.models.KeyValue;

import java.util.ArrayList;

public class ScreenLoader {

    public static final int TASK_CLEAR = Intent.FLAG_ACTIVITY_CLEAR_TASK;
    public static final int TASK_NEW = Intent.FLAG_ACTIVITY_NEW_TASK;
    public static final int TASK_CLEAR_AND_NEW = Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK;
    public static final String BUNDLE = "bundle";
    public static final String DATA_1 = "data_1";
    public static final String DATA_2 = "data_2";
    public static final String DATA_3 = "data_3";
    public static final String DATA_4 = "data_4";
    public static final String DATA_5 = "data_5";
    public static final String SOURCE_DATA_1 = "source_data_1";
    public static final String SOURCE_DATA_2 = "source_data_2";
    public static final String SOURCE_DATA_3 = "source_data_3";
    public static final String SOURCE_DATA_4 = "source_data_4";
    public static final String SOURCE_DATA_5 = "source_data_5";
    public static final String CURRENT_DOCUMENT_ID = "id";
    public static final String PARENT_DOCUMENT_ID = "id_2";
    public static final String ID_3 = "id_3";
    public static final String UID = "uid";
    public static final String NEXT = "next";
    public static final String POSITION = "position";
    public static final String REFERENCE = "reference";
    public static final String SCREEN = "screen";
    public static final String TYPE = "type";
    public static final String USER = "user";
    public static final String PARENT_DOCUMENT = "parent_document";
    public static final String CURRENT_DOCUMENT = "current_document";

    public static void load(@NonNull Context context, @NonNull Intent intent) {
        load(context, intent, Animation.SLIDE_LEFT);
    }

    public static void load(@NonNull Context context, @NonNull Intent intent, @NonNull Animation animation) {
        context.startActivity(intent);
        Animation.select((Activity) context, animation);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls) {
        load(context, cls, Animation.SLIDE_LEFT);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull Animation type) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        Animation.select((Activity) context, type);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, int flags) {
        load(context, cls, flags, Animation.SLIDE_LEFT);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, int flags, @NonNull Animation type) {
        Intent intent = IntentBuilder.getInstance(context, cls).setFlags(flags);
        context.startActivity(intent);
        Animation.select((Activity) context, type);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, int flags, @NonNull KeyValue... keyValues) {
        load(context, cls, flags, Animation.SLIDE_LEFT, keyValues);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, int flags, @NonNull Animation type, @NonNull KeyValue... keyValues) {
        Intent intent = IntentBuilder.getInstance(context, cls).putExtras(keyValues).setFlags(flags);
        context.startActivity(intent);
        Animation.select((Activity) context, type);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull Bundle options) {
        load(context, cls, options, Animation.SLIDE_LEFT);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull Bundle options, @NonNull Animation type) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent, options);
        Animation.select((Activity) context, type);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull ArrayList<KeyValue> keyValues) {
        load(context, cls, Animation.SLIDE_LEFT, keyValues);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull KeyValue... keyValues) {
        load(context, cls, Animation.SLIDE_LEFT, keyValues);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull Animation type, @NonNull ArrayList<KeyValue> keyValues) {
        Intent intent = IntentBuilder.getInstance(context, cls).putExtras(keyValues);
        context.startActivity(intent);
        Animation.select((Activity) context, type);
    }

    public static void load(@NonNull Context context, @NonNull Class<? extends Activity> cls, @NonNull Animation type, @NonNull KeyValue... keyValues) {
        Intent intent = IntentBuilder.getInstance(new Intent(context, cls)).putExtras(keyValues);
        context.startActivity(intent);
        Animation.select((Activity) context, type);
    }

    public static void request(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int requestCode) {
        request(activity, cls, requestCode, Animation.SWIPE_LEFT);
    }

    public static void request(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int requestCode, @NonNull Animation type) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
        Animation.select(activity, type);
    }

    public static void request(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int requestCode, @NonNull Bundle options) {
        request(activity, cls, requestCode, options, Animation.SWIPE_LEFT);
    }

    public static void request(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int requestCode, @NonNull Bundle options, @NonNull Animation type) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode, options);
        Animation.select(activity, type);
    }

    public static void request(@NonNull Activity activity, @Nullable Intent intent, int requestCode) {
        request(activity, intent, requestCode, Animation.SWIPE_LEFT);
    }

    public static void request(@NonNull Activity activity, @Nullable Intent intent, int requestCode, @NonNull Animation type) {
        activity.startActivityForResult(intent, requestCode);
        Animation.select(activity, type);
    }

    public static void send(@NonNull Activity activity) {
        activity.setResult(Activity.RESULT_OK);
    }

    public static void send(@NonNull Activity activity, @NonNull Intent intent) {
        activity.setResult(Activity.RESULT_OK, intent);
    }

    public static void send(@NonNull Activity activity, @NonNull KeyValue... keyValues) {
        Intent intent = IntentBuilder.getInstance().putExtras(keyValues);
        activity.setResult(Activity.RESULT_OK, intent);
    }

    public enum Animation {

        NONE, CARD, DIAGONAL, FADE, IN_AND_OUT, SHRINK, SPIN, SPLIT, SLIDE_LEFT, SLIDE_RIGHT, SLIDE_DOWN, SLIDE_UP, SWIPE_LEFT, SWIPE_RIGHT, WINDMILL, ZOOM;

        public static void none(@NonNull Activity activity) {
            activity.overridePendingTransition(0, 0);
        }

        public static void zoom(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_zoom_enter, R.anim.animate_zoom_exit);
        }

        public static void fade(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_fade_enter, R.anim.animate_fade_exit);
        }

        public static void windmill(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_windmill_enter, R.anim.animate_windmill_exit);
        }

        public static void spin(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_spin_enter, R.anim.animate_spin_exit);
        }

        public static void diagonal(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_diagonal_right_enter, R.anim.animate_diagonal_right_exit);
        }

        public static void split(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_split_enter, R.anim.animate_split_exit);
        }

        public static void shrink(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_shrink_enter, R.anim.animate_shrink_exit);
        }

        public static void card(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_card_enter, R.anim.animate_card_exit);
        }

        public static void inOut(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        }

        public static void swipeLeft(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
        }

        public static void swipeRight(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit);
        }

        public static void slideLeft(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit);
        }

        public static void slideRight(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_slide_in_left, R.anim.animate_slide_right_exit);
        }

        public static void slideUp(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
        }

        public static void slideDown(@NonNull Activity activity) {
            activity.overridePendingTransition(R.anim.animate_slide_down_enter, R.anim.animate_slide_down_exit);
        }

        public static void select(@NonNull Activity activity, @NonNull Animation type) {
            switch (type) {
                case CARD:
                    card(activity);
                    break;
                case DIAGONAL:
                    diagonal(activity);
                    break;
                case FADE:
                    fade(activity);
                    break;
                case IN_AND_OUT:
                    inOut(activity);
                    break;
                case SHRINK:
                    shrink(activity);
                    break;
                case SLIDE_UP:
                    slideUp(activity);
                    break;
                case SLIDE_DOWN:
                    slideDown(activity);
                    break;
                case SLIDE_LEFT:
                    slideLeft(activity);
                    break;
                case SLIDE_RIGHT:
                    slideRight(activity);
                    break;
                case SPIN:
                    spin(activity);
                    break;
                case SPLIT:
                    split(activity);
                    break;
                case SWIPE_LEFT:
                    swipeLeft(activity);
                    break;
                case SWIPE_RIGHT:
                    swipeRight(activity);
                    break;
                case WINDMILL:
                    windmill(activity);
                    break;
                case ZOOM:
                    zoom(activity);
                    break;
                default:
                    none(activity);
            }
        }
    }
}
