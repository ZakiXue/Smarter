package com.example.fogfly.smarter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Zaki Xue
 * @time 2019/1/3 23:33
 * @description SharedPreferences封装
 */
public class ShareUtils {
    public static final String NAME = "config";

    //String
    public static void putString(Context mcontext, String key, String value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context mcontext, String key, String value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    //Int
    public static void putInt(Context mcontext, String key, Integer value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context mcontext, String key, Integer value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    //boolean
    public static void putBoolean(Context mcontext, String key, boolean value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context mcontext, String key, boolean value) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void deleteShare(Context mcontext, String key) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    public static void deleteAllShare(Context mcontext, String key) {
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
