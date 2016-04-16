package com.mygps.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mygps.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class SigninActivity extends Activity {

    EditText username;
    EditText password;

    Button sign;
    Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        initView();

    }

    private void initView() {

        ((TextView)findViewById(R.id.title)).setText("用户注册");

        username = (EditText) findViewById(R.id.signin_username);
        password = (EditText) findViewById(R.id.signin_password);

        sign = (Button) findViewById(R.id.signin_signin);
        reset = (Button) findViewById(R.id.signin_reset);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String un = username.getText().toString();
                String pw = password.getText().toString();


                /**
                 * 此处做用户名和密码的检查
                 */





                BmobUser user = new BmobUser();
                user.setUsername(un);
                user.setPassword(pw);
                user.signUp(SigninActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                        intent.putExtra("username" , un);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });


            }
        });


    }
}
