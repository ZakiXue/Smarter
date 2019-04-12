package com.example.fogfly.smarter.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @author Zaki Xue
 * @time 2019/1/3 17:22
 * @description 工具类的封装
 */
public class UtilTools {
    /**
     * 设置字体
     * @param mcontext
     * @param textView
     */
    public static void setFonts(Context mcontext, TextView textView){
        Typeface typeface = Typeface.createFromAsset(mcontext.getAssets(), "fonts/zhanghaishan.ttf");
        textView.setTypeface(typeface);
    }
}
