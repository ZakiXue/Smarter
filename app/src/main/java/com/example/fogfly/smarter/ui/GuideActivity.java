package com.example.fogfly.smarter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fogfly.smarter.MainActivity;
import com.example.fogfly.smarter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/3 17:30
 * @description  指导页面（更新时指导）
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private ViewPager gu_viewpager;
    private View view1;
    private View view2;
    private View view3;
    private TextView gu_textview1;
    private TextView gu_textview2;
    private TextView gu_textview3;
    private List<View> mlist  = new ArrayList<>();
    private ImageView gu_point1;
    private ImageView gu_point2;
    private ImageView gu_point3;
    private Button gu_enter;
    private ImageButton gu_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initview();
        setting(true,false,false);
        mviewpager();
        gu_skip.setOnClickListener(this);
        gu_enter.setOnClickListener(this);
    }
    /**
     * viewpager的设置
     */
    private void mviewpager() {
        gu_skip.setVisibility(View.VISIBLE);
        gu_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                setting(true,false,false);
                        gu_skip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setting(false,true,false);
                        gu_skip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setting(false,false,true);
                        gu_skip.setVisibility(View.GONE);
                        break;
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mlist.add(view1);
        mlist.add(view2);
        mlist.add(view3);

        gu_viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mlist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view== object;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(mlist.get(position));
                return mlist.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(mlist.get(position));
                // super.destroyItem(container, position, object);
            }
        });
    }

    /**
     * 设置圆点的颜色
     * @param b
     * @param b1
     * @param b2
     */
    private void setting(boolean b, boolean b1, boolean b2) {
        if (b) {
            gu_point1.setBackgroundResource(R.drawable.point_on);
        } else {
            gu_point1.setBackgroundResource(R.drawable.point_off);
        }
        if (b1) {
            gu_point2.setBackgroundResource(R.drawable.point_on);
        } else {
            gu_point2.setBackgroundResource(R.drawable.point_off);
        }
        if (b2) {
            gu_point3.setBackgroundResource(R.drawable.point_on);
        } else {
            gu_point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    /**
     * 初始化界面
     */
    private void initview() {
        gu_viewpager = (ViewPager) findViewById(R.id.gu_viewpager);
        view1 = View.inflate(this, R.layout.guide_one, null);
        view2 = View.inflate(this, R.layout.guide_two, null);
        view3 = View.inflate(this, R.layout.guide_three, null);
        gu_enter = (Button) view3.findViewById(R.id.gu_enter);
        gu_skip = (ImageButton) findViewById(R.id.gu_skip);
        gu_textview1 = (TextView) findViewById(R.id.gu_textview1);
        gu_textview2 = (TextView) findViewById(R.id.gu_textview2);
        gu_textview3 = (TextView) findViewById(R.id.gu_textview3);
        //设置圆点
        gu_point1 = (ImageView) findViewById(R.id.gu_point1);
        gu_point2 = (ImageView) findViewById(R.id.gu_point2);
        gu_point3 = (ImageView) findViewById(R.id.gu_point3);
    }

    /**
     * 点击事件监听
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gu_enter:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
              //  Toast.makeText(this,"enter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gu_skip:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
              //  Toast.makeText(this,"skip", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
