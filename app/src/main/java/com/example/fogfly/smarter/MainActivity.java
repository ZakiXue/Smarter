package com.example.fogfly.smarter;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fogfly.smarter.adapter.Myadapter;
import com.example.fogfly.smarter.entity.Bmob.MyUser;
import com.example.fogfly.smarter.fragment.DiscoverFragment;
import com.example.fogfly.smarter.fragment.JournalFragment;
import com.example.fogfly.smarter.fragment.NewsFragment;
import com.example.fogfly.smarter.fragment.WeatherFragment;
import com.example.fogfly.smarter.view.MyImageView;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * 主屏幕 闪屏后出现的页面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout main_rg;
    private ViewPager main_vp;
    private LinearLayout main_news;
    private MyImageView main_iv1;
    private TextView main_rb1;
    private LinearLayout main_weather;
    private MyImageView main_iv2;
    private TextView main_rb2;
    private LinearLayout main_journal;
    private MyImageView main_iv3;
    private TextView main_rb3;
    private LinearLayout main_discover;
    private MyImageView main_iv4;
    private TextView main_rb4;
    private int mMTextNormalColor;
    private int mMTextSelectedColor;
    private ArrayList<Fragment> mFragments;
    private ArgbEvaluator mColorEvaluator;
    private Toolbar toolbar;
    public TextView toolbar_text;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        //        ActionBar actionBar = getSupportActionBar();
        //        if (actionBar != null) {
        //            actionBar.setDisplayHomeAsUpEnabled(true);
        //            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //        }
        View view1 = navView.getHeaderView(0);
        TextView username = (TextView) view1.findViewById(R.id.username);
        TextView mail = (TextView) view1.findViewById(R.id.mail);
        username.setText(user.getUsername());
        mail.setText(user.getEmail());
        Menu menu = navView.getMenu();
        menu.add(Menu.NONE, Menu.FIRST + 3, 1, "创建时间:" + user.getCreatedAt()).setIcon(R.drawable.nav_task);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        initTextColor();
        initview();
        initDate();
        initImageColor();

        Myadapter myAdapter = new Myadapter(getSupportFragmentManager(), mFragments);// 初始化adapter
        main_vp.setAdapter(myAdapter);
        main_news.setOnClickListener(this);
        main_weather.setOnClickListener(this);
        main_journal.setOnClickListener(this);
        main_discover.setOnClickListener(this);
        main_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTabTextColorAndImageView(position, positionOffset);// 更改text的颜色还有图片
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //
    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.toolbar, menu);
    //        return true;
    //    }

    /**
     * 更改图片和文本的颜色
     *
     * @param position
     * @param positionOffset
     */
    private void setTabTextColorAndImageView(int position, float positionOffset) {
        mColorEvaluator = new ArgbEvaluator();  // 根据偏移量 来得到
        int evaluateCurrent = (int) mColorEvaluator.evaluate(positionOffset, mMTextSelectedColor, mMTextNormalColor);//当前tab的颜色值
        int evaluateThe = (int) mColorEvaluator.evaluate(positionOffset, mMTextNormalColor, mMTextSelectedColor);// 将要到tab的颜色值
        switch (position) {
            case 0:
                main_rb1.setTextColor(evaluateCurrent);  //设置消息的字体颜色
                main_rb2.setTextColor(evaluateThe);  //设置通讯录的字体颜色
                main_iv1.transformPage(positionOffset);  //设置消息的图片
                main_iv2.transformPage(1 - positionOffset); //设置通讯录的图片
                break;
            case 1:
                main_rb2.setTextColor(evaluateCurrent);
                main_rb3.setTextColor(evaluateThe);
                main_iv2.transformPage(positionOffset);
                main_iv3.transformPage(1 - positionOffset);
                break;
            case 2:
                main_rb3.setTextColor(evaluateCurrent);
                main_rb4.setTextColor(evaluateThe);
                main_iv3.transformPage(positionOffset);
                main_iv4.transformPage(1 - positionOffset);
                break;
            case 3:
                main_rb4.setTextColor(evaluateCurrent);
                main_rb1.setTextColor(evaluateThe);
                main_iv4.transformPage(positionOffset);
                main_iv1.transformPage(1 - positionOffset);
                break;


        }
    }

    /**
     * 设置图片点击否的颜色
     */
    private void initImageColor() {
        main_iv1.setImages(R.drawable.news_normal, R.drawable.news_selected);
        main_iv2.setImages(R.drawable.weather_normal, R.drawable.weather_selected);
        main_iv3.setImages(R.drawable.journal_normal, R.drawable.journal_selected);
        main_iv4.setImages(R.drawable.find_normal, R.drawable.find_selected);
    }

    /**
     * 初始化Fragment
     */
    private void initDate() {
        mFragments = new ArrayList<>();
        mFragments.add(new NewsFragment());
        mFragments.add(new WeatherFragment());
        mFragments.add(new JournalFragment());
        mFragments.add(new DiscoverFragment());
    }

    /**
     * 设置字体的颜色
     */
    private void initTextColor() {
        mMTextNormalColor = getResources().getColor(R.color.main_bottom_normal);
        mMTextSelectedColor = getResources().getColor(R.color.main_bottom_selectes);
    }

    /**
     * 初始化控件
     */
    private void initview() {
        main_vp = (ViewPager) findViewById(R.id.main_vp);
        main_rg = (LinearLayout) findViewById(R.id.main_rg);
        main_news = (LinearLayout) findViewById(R.id.main_news);
        main_iv1 = (MyImageView) findViewById(R.id.main_iv1);
        main_rb1 = (TextView) findViewById(R.id.main_rb1);
        main_weather = (LinearLayout) findViewById(R.id.main_weather);
        main_iv2 = (MyImageView) findViewById(R.id.main_iv2);
        main_rb2 = (TextView) findViewById(R.id.main_rb2);
        main_journal = (LinearLayout) findViewById(R.id.main_journal);
        main_iv3 = (MyImageView) findViewById(R.id.main_iv3);
        main_rb3 = (TextView) findViewById(R.id.main_rb3);
        main_discover = (LinearLayout) findViewById(R.id.main_discover);
        main_iv4 = (MyImageView) findViewById(R.id.main_iv4);
        main_rb4 = (TextView) findViewById(R.id.main_rb4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_news:
                main_vp.setCurrentItem(0);
                toolbar_text.setText("新闻");
                break;
            case R.id.main_weather:
                main_vp.setCurrentItem(1);
                toolbar_text.setText("天气预报");
                break;
            case R.id.main_journal:
                main_vp.setCurrentItem(2);

                toolbar_text.setText("日记");
                break;
            case R.id.main_discover:
                main_vp.setCurrentItem(3);
                toolbar_text.setText("发现美食");
                break;


        }
    }

}
