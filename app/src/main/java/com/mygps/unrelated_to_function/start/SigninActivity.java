package com.mygps.unrelated_to_function.start;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mygps.R;
import com.mygps.unrelated_to_function.start.HttpRequest.SigninUserPost;
import com.mygps.unrelated_to_function.start.model.User;
import com.mygps.unrelated_to_function.start.utils.UserCheck;
import com.mygps.utils.material_design.StatusBarUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class SigninActivity extends AppCompatActivity {

    MenuItem sign;
    EditText username;
    EditText password;

    Toolbar mToolBar;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        new StatusBarUtils().setStatusBar(this);

        initView();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0: Toast.makeText(SigninActivity.this,"出错啦。。。",Toast.LENGTH_LONG).show();break;
                    case 1:  Toast.makeText(SigninActivity.this,"注册失败，已存在该用户。",Toast.LENGTH_LONG).show();break;
                }
                super.handleMessage(msg);
            }
        };
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
        //findViewById(R.id.signInUserIM).requestFocus();
        username.addTextChangedListener(new textWatcher());
        password.addTextChangedListener(new textWatcher());

    }

    class textWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (password.getText()!=null&&username.getText()!=null&&!password.getText().toString().equals("")&&!username.getText().toString().equals("")){
                sign.setEnabled(true);
            }else{
                sign.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        sign=menu.add(0,0,0,"注册");
        sign.setTitle("注册");
        sign.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sign.setEnabled(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 0:
                final String un = username.getText().toString();
                String pw = password.getText().toString();

                switch (UserCheck.check(un,pw)){
                    case UserCheck.OK:
                        final User user=new User();
                        user.setUsername(un);
                        user.setPassword(pw);
                        try {
                            final ProgressDialog progressDialog=ProgressDialog.show(SigninActivity.this,null,"注册中...");

                            new SigninUserPost(user).setOnSignInCallback(new SigninUserPost.OnSignInCallback() {
                                @Override
                                public void onError(int errorCode) {

                                    progressDialog.dismiss();

                                    handler.sendEmptyMessage(0);

                                }

                                @Override
                                public void onSuccess() {
                                    progressDialog.dismiss();
                                    StaticObject.appCompatActivity.finish();
                                    SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                                    sp.edit().putString("username",user.getUsername()).commit();
                                    sp.edit().putString("password",user.getPassword()).commit();
                                    sp.edit().putBoolean("remeberPW",true).commit();
                                    sp.edit().putBoolean("login",true).commit();
                                    startActivity(new Intent(SigninActivity.this,WelcomeActivity.class));
                                    finish();
                                }

                                @Override
                                public void onFail() {
                                    progressDialog.dismiss();
                                    handler.sendEmptyMessage(1);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case UserCheck.USERNAMEERROR:break;//username错误处理
                    case UserCheck.PASSWORDERROR:break;//password错误处理
                    default:break;
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
