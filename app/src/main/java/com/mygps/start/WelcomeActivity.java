package com.mygps.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.mygps.CommunityServiceActivity;
import com.mygps.MyApplication;
import com.mygps.R;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        MyApplication app = (MyApplication) getApplication();
        app.setWelcome(this);

        initView();


    }

    private void initView() {

        findViewById(R.id.welcome_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this, SigninActivity.class));

            }
        });

        findViewById(R.id.welcome_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.welcome_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, CommunityServiceActivity.class));
            }
        });

        /**
         * 渐变动画
         */
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        /** 常用方法 */
        animation.setDuration(1000);//设置动画持续时间
        //animation.setRepeatCount(int repeatCount);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(2000);//执行前的等待时间

        findViewById(R.id.temp1).setAnimation(animation);
        animation.start();

    }
}
