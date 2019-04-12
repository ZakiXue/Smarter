package com.example.fogfly.smarter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/2/24 19:24
 * @description
 */
public class WeatherCitySelectActivity extends AppCompatActivity {

    private TextView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        CityPicker.from(WeatherCitySelectActivity.this) //activity或者fragment
                .enableAnimation(true)	//启用动画效果，默认无
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)	//指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                Bundle data1 = new Bundle();
                                data1.putString("city", data.getName());
                                intent.putExtras(data1);
                                // 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
                                WeatherCitySelectActivity.this.setResult(0, intent);
                                // 结束SelectCityActivity
                                WeatherCitySelectActivity.this.finish();
                    }
                    @Override
                    public void onCancel(){
                        Intent intent = getIntent();
                        // 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
                        WeatherCitySelectActivity.this.setResult(0, intent);
                        // 结束SelectCityActivity
                        WeatherCitySelectActivity.this.finish();
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.from(WeatherCitySelectActivity.this)
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
//        setContentView(R.layout.activity_weather_city_select);
        //        String name = "广州";
        //        Intent intent = getIntent();
        //        Bundle data = new Bundle();
        //        data.putString("city", name);
        //        intent.putExtras(data);
        //        // 设置该SelectCityActivity结果码，并设置结束之后退回的Activity
        //        WeatherCitySelectActivity.this.setResult(0, intent);
        //        // 结束SelectCityActivity
        //        WeatherCitySelectActivity.this.finish();
    }
}
