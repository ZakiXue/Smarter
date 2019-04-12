package com.example.fogfly.smarter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.ui.loginUI.LoginActivity;
import com.example.fogfly.smarter.utils.ShareUtils;
import com.example.fogfly.smarter.utils.StaticClass;
import com.example.fogfly.smarter.utils.UtilTools;

/**
 * @author Zaki Xue
 * @time 2019/1/3 16:15
 * @description 闪屏页
 */
public class SplashActivity extends Activity {

    private TextView tv_splash;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                       // startActivity(new Intent(SplashActivity.this,MainActivity.class));
                       startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash = (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFonts(SplashActivity.this, tv_splash);
        mHandler.sendEmptyMessageDelayed(100,1000);
       
    }

    /**
     * 判断程序是否第一次运行
     * @return
     */
    private boolean isFirst() {
        boolean b = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (b){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else{
            return b;
        }
    }
}
