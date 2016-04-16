package com.mygps.test;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.mygps.R;

public class ReceiveMessageActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        //initView();

    }

    private void initView() {
        MyMessageReceiver receiver = new MyMessageReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(1000);
        registerReceiver(receiver, intentFilter);

    }
}
