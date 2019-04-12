package com.example.fogfly.smarter.ui.loginUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.entity.Bmob.MyUser;
import com.example.fogfly.smarter.utils.StaticClass;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CardView mCardView;
    private EditText mEditText;
    private EditText mPassword;
    private EditText mRepeatpassword;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //第一：默认初始化
        Bmob.initialize(RegisterActivity.this, StaticClass.bmob_key);
        initview();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mCardView = (CardView) findViewById(R.id.cv_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateRevealClose();
            }
        });
    }

    private void initview() {
        mEditText = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mRepeatpassword = (EditText) findViewById(R.id.et_repeatpassword);
        et_email = (EditText) findViewById(R.id.et_email);
        Button button = (Button) findViewById(R.id.bt_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEditText.getText().toString().trim();
                 String password = mPassword.getText().toString().trim();
                  String repeatpassword = mRepeatpassword.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                if (TextUtils.isEmpty(username) & TextUtils.isEmpty(password) & TextUtils.isEmpty(repeatpassword)& TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this,"输入框内容不能为空", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(repeatpassword)){
                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }else{
                  final MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    myUser.setPassword(password);
                    myUser.setEmail(email);
                    myUser.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                                    animateRevealClose();
                                //Toast.makeText(RegisterActivity.this,"添加数据成功，返回objectId为"+s, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this,"该邮箱已注册", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mCardView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView, mCardView.getWidth() / 2, 0, fab.getWidth() / 2, mCardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView, mCardView.getWidth() / 2, 0, mCardView.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
