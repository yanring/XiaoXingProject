package com.mygps.unrelated_to_function.start;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mygps.related_to_device.map.MyEquipListActivity;
import com.mygps.related_to_device.map.service.MyEquipListService;
import com.mygps.unrelated_to_function.main.MainActivity;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.unrelated_to_function.start.HttpRequest.LoginRequest;
import com.mygps.unrelated_to_function.start.model.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class WelcomeActivity extends AppCompatActivity {

    EditText passportET, usernameET;
    Button loginBT;
    TextView registerTV;
    ProgressDialog pro;
    MyApplication app;
    AppCompatCheckBox remeberCB;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new);

        app = (MyApplication) getApplication();

        sp = getSharedPreferences("Login", MODE_PRIVATE);

        initView();

        if (sp.getBoolean("login", false)) {
            login(sp.getString("username","1"),sp.getString("password","2"));
        }

/*        startActivity(new Intent(this,MainActivity.class));
        finish();*/
    }

    private void initView() {

        //test
        findViewById(R.id.welcomeTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.welcomeIconIV).requestFocus();//获取焦点
        passportET = (EditText) findViewById(R.id.welcomeActivityETCipher);
        usernameET = (EditText) findViewById(R.id.welcomeActivityETUsername);
        loginBT = (Button) findViewById(R.id.welcomeActivityBTLogin);
        registerTV = (TextView) findViewById(R.id.welcomeActivityRegiter);
        remeberCB = (AppCompatCheckBox) findViewById(R.id.welcomeActivityCBRemeberCipher);

        usernameET.setText(sp.getString("username",""));
        remeberCB.setChecked(sp.getBoolean("remeberPW",false));
        if (sp.getBoolean("remeberPW",false)){
            passportET.setText(sp.getString("password",""));
        }else {
            passportET.setText("");
        }

        loginBT.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           login(usernameET.getText().toString(), passportET.getText().toString());
                                       }
                                   }


        );
        registerTV.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              StaticObject.appCompatActivity=WelcomeActivity.this;
                                              startActivity(new Intent(WelcomeActivity.this, SigninActivity.class));
                                          }
                                      }

        );

      /*  *//**
         * 渐变动画
         *//*
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        *//** 常用方法 *//*
        animation.setDuration(1000);//设置动画持续时间
        //animation.setRepeatCount(int repeatCount);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(2000);//执行前的等待时间

        findViewById(R.id.temp1).setAnimation(animation);
        animation.start();
*/
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.welcome_activity_imageup);
        findViewById(R.id.welcomeTopLayout).setAnimation(anim);
        anim.start();

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void login(String username, String password) {
        showPro();


        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            new LoginRequest(user, WelcomeActivity.this).setOnLoginInCallback(new LoginRequest.OnLoginInCallback() {
                @Override
                public void onError(int errorCode) {
                    disPro();
                    sp.edit().putBoolean("login", false).commit();
                }

                @Override
                public void onSuccess() {
                    sp.edit().putString("username", user.getUsername()).commit();
                    sp.edit().putString("password", user.getPassword()).commit();
                    if (remeberCB.isChecked()) {
                        //记录密码
                        sp.edit().putBoolean("remeberPW", true).commit();
                    } else {
                        sp.edit().putBoolean("remeberPW", false).commit();
                    }

                    sp.edit().putBoolean("login", true).commit();

                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    disPro();
                    finish();

                }

                @Override
                public void onFail(int errorCode) {
                    disPro();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showPro() {
        if (pro == null) {
            pro = ProgressDialog.show(this, null, "正在登录");
        } else {
            pro.show();
        }
    }

    public void disPro() {
        pro.dismiss();
    }
}
