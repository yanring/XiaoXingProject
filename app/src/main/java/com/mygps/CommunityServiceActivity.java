package com.mygps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mygps.chiyao.chiyao;

/**
 * Created by Yanring on 2016/3/20.
 * 社区服务界面
 */
public class CommunityServiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_service);
        findViewById(R.id.community_forum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityServiceActivity.this, TestForumActivity.class));
            }
        });
        findViewById(R.id.general_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityServiceActivity.this, TestShopWebViewActivity.class));
            }
        });
        findViewById(R.id.activityCommunityIBTakeMedicine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommunityServiceActivity.this,chiyao.class));
            }
        });
    }

}
