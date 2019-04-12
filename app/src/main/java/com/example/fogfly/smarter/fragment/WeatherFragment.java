package com.example.fogfly.smarter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.entity.Weather;
import com.example.fogfly.smarter.entity.Weather_item2;
import com.example.fogfly.smarter.entity.Weather_item3;
import com.example.fogfly.smarter.entity.Weather_item5;
import com.example.fogfly.smarter.ui.WeatherCitySelectActivity;
import com.example.fogfly.smarter.utils.WeatherPic;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/24 16:35
 * @description
 */
public class WeatherFragment extends Fragment {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingtoolbar;
    private NestedScrollView mImageView;
    private TextView temp;
    private TextView wind;
    private TextView level;
    private TextView humidity;
    private TextView time;
    private ImageView icon;
    private List<Weather_item3> mList = new ArrayList<>();
    private String mContent = "广州";
    private Weather_item2 weather_item2;
    private TextView cloth_brief;
    private TextView cloth_txt;
    private TextView sport_brief;
    private TextView travel_brief;
    private TextView flu_brief;
    private Weather weather_item;
    private Weather_item5 weather_item5;
    private TextView week5;
    private TextView temp5;
    private TextView weather5;
    private TextView wind5;
    private TextView time5;
    private ImageView pic5;
    private TextView weather_item3_week0;
    private TextView weather_item3_temp0;
    private TextView weather_item3_weather0;
    private TextView weather_item3_wind0;

    private TextView weather_item3_week1;
    private TextView weather_item3_temp1;
    private TextView weather_item3_weather1;
    private TextView weather_item3_wind1;

    private TextView weather_item3_week2;
    private TextView weather_item3_temp2;
    private TextView weather_item3_weather2;
    private TextView weather_item3_wind2;

    private TextView weather_item3_week3;
    private TextView weather_item3_temp3;
    private TextView weather_item3_weather3;
    private TextView weather_item3_wind3;

    private TextView weather_item3_week4;
    private TextView weather_item3_temp4;
    private TextView weather_item3_weather4;
    private TextView weather_item3_wind4;

    private TextView weather_item3_week5;
    private TextView weather_item3_temp5;
    private TextView weather_item3_weather5;
    private TextView weather_item3_wind5;
    private String mCity;
    private ImageView weather_item3_pic0;
    private ImageView weather_item3_pic1;
    private ImageView weather_item3_pic2;
    private ImageView weather_item3_pic3;
    private ImageView weather_item3_pic4;
    private ImageView weather_item3_pic5;

    private Context mContext;
    private String city;
    private TextView weather_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //        TextView textView = new TextView(getActivity());
        //        textView.setText("天气");
        //        textView.setTextSize(100);
        //        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        final View view = inflater.inflate(R.layout.fragment_weather, null);
        initFloatingButton(view);
        initGet(view);
        return view;
    }


    /**
     * 解析接口并获取数据
     *
     * @param view
     */
    private void initGet(final View view) {

        if (TextUtils.isEmpty(city)) {
            city = "中山";
        }
        String s = getURLEncoderString(city.trim());
        String url = "http://v.juhe.cn/weather/index?cityname=" + s + "&type=&format=&key=bbff775b08a439f89146091d7efb03eb";

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                System.out.println("json:" + t);
                try {
                    parseJson(t);    //解析json数据
                    // 要跑到主线程中去更新UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            initView(view);
                            initView2(view);
                            initview5(view);
                            initview3(view);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }
        });
    }


    /**
     * java url 编码
     *
     * @param str
     * @return
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析json
     *
     * @param t
     */
    private void parseJson(String t) throws JSONException {
        //1、声明JSONObject 对象
        JSONObject jsonObject = new JSONObject(t);
        //2、获取JSONObject 数据
        JSONObject result = jsonObject.getJSONObject("result");

        JSONObject sk = result.getJSONObject("sk");

        weather_item = new Weather();
        String temp = sk.getString("temp");
        String wind_direction = sk.getString("wind_direction");
        String wind_strength = sk.getString("wind_strength");
        String humidity = sk.getString("humidity");
        String time = sk.getString("time");
        weather_item.setTemp(temp);
        weather_item.setWind_direction(wind_direction);
        weather_item.setWind_strength(wind_strength);
        weather_item.setHumidity(humidity);
        weather_item.setTime(time);
        weather_item2 = new Weather_item2();
        JSONObject today = result.getJSONObject("today");

        String temperature5 = today.getString("temperature");
        String weather5 = today.getString("weather");
        String wind5 = today.getString("wind");
        String week5 = today.getString("week");
        mCity = today.getString("city");
        String date_y5 = today.getString("date_y");
        String dressing_index = today.getString("dressing_index");
        String dressing_advice = today.getString("dressing_advice");
        String wash_index = today.getString("wash_index");
        String travel_index = today.getString("travel_index");
        String exercise_index = today.getString("exercise_index");
        weather_item2.setDressing_index(dressing_index);
        weather_item2.setDressing_advice(dressing_advice);
        weather_item2.setWash_index(wash_index);
        weather_item2.setTravel_index(travel_index);
        weather_item2.setExercise_index(exercise_index);
        weather_item5 = new Weather_item5();
        weather_item5.setTemp5(temperature5);
        weather_item5.setTime5(date_y5);
        weather_item5.setWeather5(weather5);
        weather_item5.setWeek5(week5);
        weather_item5.setWind5(wind5);


        JSONObject future = result.getJSONObject("future");

        //result.getJSONArray("future");
        JSONArray names = future.names();

        System.out.println("future+" + future);
        for (int i = 0; i < names.length(); i++) {
            JSONObject day = future.getJSONObject((String) names.get(i));
            String week3 = day.getString("week");
            String wind3 = day.getString("wind");
            String weather3 = day.getString("weather");
            String temperature3 = day.getString("temperature");
            Weather_item3 weather_item3 = new Weather_item3();
            weather_item3.setWeek3(week3);
            weather_item3.setWeather3(weather3);
            weather_item3.setTemp3(temperature3);
            weather_item3.setWind3(wind3);
            mList.add(weather_item3);
        }

    }

    /**
     * 初始化weather_item
     *
     * @param view
     */
    private void initView(View view) {
        //toolbar = (Toolbar) view.findViewById(R.id.weather_toolbar);
       // collapsingtoolbar = (CollapsingToolbarLayout) view.findViewById(R.id.weather_collapsing_toolbar);
        mImageView = (NestedScrollView) view.findViewById(R.id.weather_imageview);
        weather_title = (TextView) view.findViewById(R.id.weather_title);
        weather_title.setText(mCity);
        // collapsingtoolbar.setTitle(mCity);
        mImageView.setBackgroundResource(WeatherPic.dailypic(weather_item5.getWeather5()));
        icon = (ImageView) view.findViewById(R.id.weather_item1_icon);
        icon.setBackgroundResource(WeatherPic.typepic(weather_item5.getWeather5()));
        temp = (TextView) view.findViewById(R.id.weather_item1_temp);
        wind = (TextView) view.findViewById(R.id.weather_item1_wind);
        level = (TextView) view.findViewById(R.id.weather_item1_level);
        humidity = (TextView) view.findViewById(R.id.weather_item1_humidity);
        time = (TextView) view.findViewById(R.id.weather_item1_time);
        temp.setText(weather_item.getTemp() + "℃");
        wind.setText(weather_item.getWind_direction());
        level.setText(weather_item.getWind_strength());
        humidity.setText("湿度：" + weather_item.getHumidity());
        time.setText(weather_item.getTime());


    }

    /**
     * 选择城市后返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0) {
            // 取出Intent里的数据
            if(!TextUtils.isEmpty(data.getStringExtra("city"))){
                city = data.getStringExtra("city");
            }
        }
        initGet(getView());
    }

    /**
     * 初始化悬浮那妞
     * @param view
     */
    private void initFloatingButton(View view) {
        //悬浮按钮初始化
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.weather_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建需要对应于目标Activity的Intent
                Intent intent = new Intent(getContext(), WeatherCitySelectActivity.class);
                // 启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
                startActivityForResult(intent, 0);
               // Toast.makeText(getActivity(),"FAB clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * weather item2 初始化
     *
     * @param view
     */
    private void initView2(View view) {
        cloth_brief = (TextView) view.findViewById(R.id.cloth_brief);
        cloth_txt = (TextView) view.findViewById(R.id.cloth_txt);
        sport_brief = (TextView) view.findViewById(R.id.sport_brief);
        travel_brief = (TextView) view.findViewById(R.id.travel_brief);
        flu_brief = (TextView) view.findViewById(R.id.flu_brief);
        cloth_brief.setText("穿衣指数---" + weather_item2.getDressing_index());
        cloth_txt.setText("运动指数---" + weather_item2.getDressing_advice());
        sport_brief.setText("旅游指数---" + weather_item2.getExercise_index());
        travel_brief.setText("旅游指数---" + weather_item2.getTravel_index());
        flu_brief.setText("感冒指数---" + weather_item2.getWash_index());
    }

    /**
     * weather_item5 初始化
     *
     * @param view
     */
    private void initview5(View view) {
        week5 = (TextView) view.findViewById(R.id.weather_item5_week);
        temp5 = (TextView) view.findViewById(R.id.weather_item5_temp);
        weather5 = (TextView) view.findViewById(R.id.weather_item5_wea);
        wind5 = (TextView) view.findViewById(R.id.weather_item5_wind);
        time5 = (TextView) view.findViewById(R.id.weather_item5_time);
        week5.setText(weather_item5.getWeek5());
        temp5.setText(weather_item5.getTemp5());
        weather5.setText("天气：" + weather_item5.getWeather5());
        wind5.setText("风向：" + weather_item5.getWind5());
        time5.setText(weather_item5.getTime5());
        pic5 = (ImageView) view.findViewById(R.id.weather_item5_pic);
        pic5.setBackgroundResource(WeatherPic.typepic(weather_item5.getWeather5()));
    }

    /**
     * weather_item3 初始化
     *
     * @param view
     */
    private void initview3(View view) {
        weather_item3_week0 = (TextView) view.findViewById(R.id.weather_item3_week0);
        weather_item3_temp0 = (TextView) view.findViewById(R.id.weather_item3_temp0);
        weather_item3_weather0 = (TextView) view.findViewById(R.id.weather_item3_weather0);
        weather_item3_wind0 = (TextView) view.findViewById(R.id.weather_item3_wind0);

        weather_item3_week1 = (TextView) view.findViewById(R.id.weather_item3_week1);
        weather_item3_temp1 = (TextView) view.findViewById(R.id.weather_item3_temp1);
        weather_item3_weather1 = (TextView) view.findViewById(R.id.weather_item3_weather1);
        weather_item3_wind1 = (TextView) view.findViewById(R.id.weather_item3_wind1);

        weather_item3_week2 = (TextView) view.findViewById(R.id.weather_item3_week2);
        weather_item3_temp2 = (TextView) view.findViewById(R.id.weather_item3_temp2);
        weather_item3_weather2 = (TextView) view.findViewById(R.id.weather_item3_weather2);
        weather_item3_wind2 = (TextView) view.findViewById(R.id.weather_item3_wind2);

        weather_item3_week3 = (TextView) view.findViewById(R.id.weather_item3_week3);
        weather_item3_temp3 = (TextView) view.findViewById(R.id.weather_item3_temp3);
        weather_item3_weather3 = (TextView) view.findViewById(R.id.weather_item3_weather3);
        weather_item3_wind3 = (TextView) view.findViewById(R.id.weather_item3_wind3);

        weather_item3_week4 = (TextView) view.findViewById(R.id.weather_item3_week4);
        weather_item3_temp4 = (TextView) view.findViewById(R.id.weather_item3_temp4);
        weather_item3_weather4 = (TextView) view.findViewById(R.id.weather_item3_weather4);
        weather_item3_wind4 = (TextView) view.findViewById(R.id.weather_item3_wind4);

        weather_item3_week5 = (TextView) view.findViewById(R.id.weather_item3_week5);
        weather_item3_temp5 = (TextView) view.findViewById(R.id.weather_item3_temp5);
        weather_item3_weather5 = (TextView) view.findViewById(R.id.weather_item3_weather5);
        weather_item3_wind5 = (TextView) view.findViewById(R.id.weather_item3_wind5);

        weather_item3_week0.setText(mList.get(1).getWeek3());
        weather_item3_week1.setText(mList.get(2).getWeek3());
        weather_item3_week2.setText(mList.get(3).getWeek3());
        weather_item3_week3.setText(mList.get(4).getWeek3());
        weather_item3_week4.setText(mList.get(5).getWeek3());
        weather_item3_week5.setText(mList.get(6).getWeek3());

        weather_item3_temp0.setText(mList.get(1).getTemp3());
        weather_item3_temp1.setText(mList.get(2).getTemp3());
        weather_item3_temp2.setText(mList.get(3).getTemp3());
        weather_item3_temp3.setText(mList.get(4).getTemp3());
        weather_item3_temp4.setText(mList.get(5).getTemp3());
        weather_item3_temp5.setText(mList.get(6).getTemp3());

        weather_item3_weather0.setText(mList.get(1).getWeather3());
        weather_item3_weather1.setText(mList.get(2).getWeather3());
        weather_item3_weather2.setText(mList.get(3).getWeather3());
        weather_item3_weather3.setText(mList.get(4).getWeather3());
        weather_item3_weather4.setText(mList.get(5).getWeather3());
        weather_item3_weather5.setText(mList.get(6).getWeather3());

        weather_item3_wind0.setText(mList.get(1).getWind3());
        weather_item3_wind1.setText(mList.get(2).getWind3());
        weather_item3_wind2.setText(mList.get(3).getWind3());
        weather_item3_wind3.setText(mList.get(4).getWind3());
        weather_item3_wind4.setText(mList.get(5).getWind3());
        weather_item3_wind5.setText(mList.get(6).getWind3());

        weather_item3_pic0 = (ImageView) view.findViewById(R.id.weather_item3_pic0);
        weather_item3_pic1 = (ImageView) view.findViewById(R.id.weather_item3_pic1);
        weather_item3_pic2 = (ImageView) view.findViewById(R.id.weather_item3_pic2);
        weather_item3_pic3 = (ImageView) view.findViewById(R.id.weather_item3_pic3);
        weather_item3_pic4 = (ImageView) view.findViewById(R.id.weather_item3_pic4);
        weather_item3_pic5 = (ImageView) view.findViewById(R.id.weather_item3_pic5);
        weather_item3_pic0.setBackgroundResource(WeatherPic.typepic(mList.get(0).getWeather3()));
        weather_item3_pic1.setBackgroundResource(WeatherPic.typepic(mList.get(1).getWeather3()));
        weather_item3_pic2.setBackgroundResource(WeatherPic.typepic(mList.get(2).getWeather3()));
        weather_item3_pic3.setBackgroundResource(WeatherPic.typepic(mList.get(3).getWeather3()));
        weather_item3_pic4.setBackgroundResource(WeatherPic.typepic(mList.get(4).getWeather3()));
        weather_item3_pic5.setBackgroundResource(WeatherPic.typepic(mList.get(5).getWeather3()));

    }
}
