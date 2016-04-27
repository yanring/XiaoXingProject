package com.mygps.unrelated_to_function.start;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mygps.MyApplication;
import com.mygps.related_to_device.map.MyEquipListActivity;
import com.mygps.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class LoginActivity extends Activity {

    EditText username;
    EditText password;

    Button login;
    Button reset;

    MyApplication app;

    ProgressDialog pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();

    }

    private void initView() {

        ((TextView)findViewById(R.id.title)).setText("用户登录");


        app = (MyApplication) getApplication();

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        login = (Button) findViewById(R.id.login_login);
        reset = (Button) findViewById(R.id.login_reset);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPro();
                String un = username.getText().toString();
                String pw = password.getText().toString();


                /**
                 * 此处做用户名和密码的检查
                 */



                BmobUser user = new BmobUser();
                user.setUsername(un);
                user.setPassword(pw);
                user.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(LoginActivity.this, MyEquipListActivity.class);
                        BmobUser currentUser = BmobUser.getCurrentUser(LoginActivity.this);
                        app.setUser(currentUser);
                        disPro();
                        startActivity(intent);
                        LoginActivity.this.finish();
                        app.getWelcome().finish();//yanring:这边2个finish是用来优化占用内存的吗?
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        disPro();
                        Log.i("aaa" , i+"   "+s);

                    }
                });


            }
        });

    }


    public void showPro(){
        if (pro == null){
            pro = ProgressDialog.show(this , null , "正在登录");
        }else {
            pro.show();
        }
    }

    public void disPro(){
        pro.dismiss();
    }
}
