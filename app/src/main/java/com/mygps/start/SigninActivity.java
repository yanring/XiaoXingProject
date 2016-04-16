package com.mygps.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mygps.R;
import com.mygps.utils.StatusBarUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class SigninActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    Button sign;
    Button reset;

    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        new StatusBarUtils().setStatusBar(this);

        initView();

    }

    private void initView() {

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("用户注册");

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Implement this method
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
