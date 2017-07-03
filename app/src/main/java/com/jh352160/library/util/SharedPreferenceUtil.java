package com.jh352160.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jh352160 on 2017/7/3.
 */

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil instance;

    private SharedPreferences.Editor editor;

    private SharedPreferences settings;

    public static SharedPreferenceUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceUtil(context.getApplicationContext());
        }
        return instance;
    }

    private SharedPreferenceUtil(Context context) {
        settings = context.getSharedPreferences("config", 0);
        editor = settings.edit();
    }

    /**
     * 保存数据.
     */
    public void saveTempData(HashMap<String, String> param) {
        Iterator<Map.Entry<String, String>> iter = param.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();

            String value = "";

            if (entry.getValue() != null) {
                value = entry.getValue().toString();
            }

            editor.putString(entry.getKey().toString(), value);
        }
        editor.commit();
    }

    /**
     * 保存数据.
     */
    public void saveTempData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取数据.
     */
    public String readTempData(String key) {
        return settings.getString(key, "");
    }

}
