package com.example.fogfly.smarter.utils;

import com.example.fogfly.smarter.R;

/**
 * @author Zaki Xue
 * @time 2019/2/24 17:02
 * @description
 */
public class WeatherPic {

    //阴转多云
    public static int dailypic(String text) {
        if (text.contains("云")){
            return R.mipmap.city_beijing_cloudy;
        }else if (text.contains("雨")){
            return R.mipmap.city_beijing_rainy;
        }else if (text.contains("阳")){
            return R.mipmap.city_beijing_sunny;
        }else {
            return R.mipmap.city_other_sunny;
        }
    }
    public static int typepic(String text){
        if (text.contains("云")&&text.contains("阳")){
            return R.mipmap.type_one_cloudytosunny;
        }else if (text.contains("雷")&&text.contains("雨")){
            return R.mipmap.type_one_thunder_rain;
        }else if (text.contains("雨")){
            return R.mipmap.type_one_heavy_rain;
        }else if (text.contains("阳")){
            return R.mipmap.type_one_sunny;
        }else if (text.contains("云")){
            return R.mipmap.type_one_cloudy;
        }else{
            return R.mipmap.type_one_snow;
        }
    }
}
