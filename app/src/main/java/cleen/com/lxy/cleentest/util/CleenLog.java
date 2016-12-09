package cleen.com.lxy.cleentest.util;

import android.util.Log;

/**
 * Created by lxy on 16-12-9.
 */
public class CleenLog {

    private static final int DIVIDER_NUM = 100;

    public static void d(String text) {
        CleenLog.d("xinyu", text);
    }

    public static void d(String tag, String text) {
        int len = text.length();
        for (int i = 0; i <= len / DIVIDER_NUM; i++) {
            int left = i * DIVIDER_NUM;
            int right = Math.min(i * DIVIDER_NUM + DIVIDER_NUM, len);
            Log.d(tag, text.substring(left, right));
        }
    }
}
