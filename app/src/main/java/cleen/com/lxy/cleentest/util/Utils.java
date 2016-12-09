package cleen.com.lxy.cleentest.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxy on 16-12-9.
 */
public class Utils {

    public static boolean isListNull(List list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static boolean isListNotNull(List list) {
        if (list == null || list.size() == 0)
            return false;
        return true;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

}
