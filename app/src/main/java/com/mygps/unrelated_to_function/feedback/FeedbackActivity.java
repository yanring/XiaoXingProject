package com.mygps.unrelated_to_function.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.utils.material_design.StatusBarUtils;

/**
 * Created by silen on 16-7-10.
 */

public class FeedbackActivity extends AppCompatActivity {

    Toolbar mToolBar;

    MenuItem sendFeedback;

    EditText contactET;
    EditText contentET;

    FeedbackServer feedbackServer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        new StatusBarUtils().setStatusBar(this);

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("意见反馈");

        initView();

        feedbackServer=new FeedbackServer(this,this,getSupportFragmentManager());

    }

    private void initView() {
        contentET=(EditText)findViewById(R.id.feedbackContentET);
        contactET=(EditText)findViewById(R.id.feedbackContactET);
        contentET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contentET.getText().length()>0){
                    sendFeedback.setEnabled(true);
                }else {
                    sendFeedback.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        sendFeedback = menu.add(0, 0, 0, "发送");
        sendFeedback.setTitle("发送");
        sendFeedback.setEnabled(true);
        sendFeedback.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 0:
                feedbackServer.send(contactET.getText().toString(),contentET.getText().toString(),((MyApplication) getApplication()).getUser().getId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
