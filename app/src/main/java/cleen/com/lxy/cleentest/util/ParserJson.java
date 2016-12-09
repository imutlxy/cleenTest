package cleen.com.lxy.cleentest.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by lxy on 16-12-9.
 */
public class ParserJson<T> {

    public String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

    /**
     * 解析列表
     *
     * @param data
     * @param c
     * @return
     */
    public ArrayList<T> getBeanList(String data, Class<T> c) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        ArrayList<T> list = new ArrayList<T>();

        if (data != null) {
            data = JSONTokener(data);
            try {
                JSONArray array = new JSONArray(data);
                Gson gson = new Gson();
                System.err.println("getBeanList" + data);
                for (int i = 0; i < array.length(); i++) {
                    T bean = gson.fromJson(array.getJSONObject(i).toString(), c);
                    list.add(bean);
                }
            } catch (Exception e) {
                Log.e("ParserJson", "解析json列表出错");
//                Log.e("ParserJson", e.toString());
            }
        }
        return list;
    }

}
