package com.example.fogfly.smarter.utils;

import android.content.Context;

/**
 * @author Zaki Xue
 * @time 2019/1/25 14:08
 * @description 网络缓存工具类
 */
public class CacheUtils {
    /**
     * 以url为key，以json为value,保存在本地
     * @param url
     * @param json
     */
    public static void setCache(String url, String json, Context cxt){
        ShareUtils.putString(cxt,url,json);

        //也可以用文件缓存：以MD5(url)为文件名，以json为文件内容

    }

    /**
     * 获取缓存
     * @param url
     * @param cxt
     * @return
     */
    public static String getCache(String url, Context cxt){
        return ShareUtils.getString(cxt,url,null);

        // 文件缓存：查找有没有一个文件叫做MD5(url)的，有的话，说明有缓存
    }
}
