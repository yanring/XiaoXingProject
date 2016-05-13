package com.mygps.unrelated_to_function.start;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.mygps.unrelated_to_function.main.MainActivity;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.utils.PositionDateFromHttp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new);

        //  new StatusBarUtils().setStatusBar(this);
        new PositionDateFromHttp(this,"2").getPreviousDate();
        app = (MyApplication) getApplication();
        app.setWelcome(this);

        initView();

      //  startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

    }

    private void initView() {

        //test
        findViewById(R.id.welcomeTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            }
        });

        findViewById(R.id.welcomeIconIV).requestFocus();//获取焦点
        passportET = (EditText) findViewById(R.id.welcomeActivityETCipher);
        usernameET = (EditText) findViewById(R.id.welcomeActivityETUsername);
        loginBT = (Button) findViewById(R.id.welcomeActivityBTLogin);
        registerTV = (TextView) findViewById(R.id.welcomeActivityRegiter);
        remeberCB=(AppCompatCheckBox)findViewById(R.id.welcomeActivityCBRemeberCipher);
        loginBT.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           showPro();
                                           String un = usernameET.getText().toString();
                                           String pw = passportET.getText().toString();

                                           /**
                                            * 此处做用户名和密码的检查
                                            */

                                           BmobUser user = new BmobUser();
                                           user.setUsername(un);
                                           user.setPassword(pw);
                                           user.login(WelcomeActivity.this, new SaveListener() {
                                               @Override
                                               public void onSuccess() {
                                                   Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                                   BmobUser currentUser = BmobUser.getCurrentUser(WelcomeActivity.this);
                                                   app.setUser(currentUser);

                                                   if (remeberCB.isChecked()){
                                                       //记录密码
                                                   }

                                                   disPro();
                                                   startActivity(intent);
                                                   finish();
                                                   app.getWelcome().finish();//yanring:这边2个finish是用来优化占用内存的吗?
                                               }

                                               @Override
                                               public void onFailure(int i, String s) {
                                                   disPro();
                                                   Log.i("aaa", i + "   " + s);

                                               }
                                           });


                                       }
                                   }


        );
        registerTV.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              startActivity(new Intent(WelcomeActivity.this,SigninActivity.class));
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
