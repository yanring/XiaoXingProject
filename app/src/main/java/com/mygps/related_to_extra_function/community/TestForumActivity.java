package com.mygps.related_to_extra_function.community;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mygps.R;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.ui.fragments.CommunityMainFragment;

/**
 * Created by Yanring on 2016/3/27.
 */
public class TestForumActivity extends FragmentActivity {
    CommunitySDK mCommSDK = null;
    String topicId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCommSDK = CommunityFactory.getCommSDK(this);
        setContentView(R.layout.activity_test_forum);

        Toast.makeText(this, "fsef", Toast.LENGTH_SHORT).show();
// 初始化sdk，请传递ApplicationContext
        mCommSDK.initSDK(this);

        CommunityMainFragment fragment = new CommunityMainFragment();
//设置Feed流页面的返回按钮不可见
        fragment.setBackButtonVisibility(View.GONE);
//添加并显示Fragment

        getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();


    }


}
