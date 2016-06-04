package com.mygps.unrelated_to_function.start;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mygps.R;
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
        // TODO: Implement this method
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 0:
                final String un = username.getText().toString();
                String pw = password.getText().toString();

                switch (UserCheck.check(un,pw)){
                    case UserCheck.OK:;break;
                    case UserCheck.USERNAMEERROR:break;//username错误处理
                    case UserCheck.PASSWORDERROR:break;//password错误处理
                    default:break;
                }

                //用户名与密码检查

                BmobUser user = new BmobUser();
                user.setUsername(un);
                user.setPassword(pw);
                user.signUp(SigninActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
