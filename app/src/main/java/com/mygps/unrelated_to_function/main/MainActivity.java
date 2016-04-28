package com.mygps.unrelated_to_function.main;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.mygps.R;
import com.mygps.related_to_device.map.MyEquipListActivity;
import com.mygps.unrelated_to_function.main.adapter.MainViewPaperAdapter;
import com.mygps.related_to_device.chiyao.chiyao;
import com.mygps.related_to_extra_function.shopping.ShopWebViewActivity;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.ui.fragments.CommunityMainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yanring on 2016/3/20.
 * 社区服务界面
 */
public class MainActivity extends AppCompatActivity {
    LinearLayout containerLayout;
    Toolbar mToolBar;
    ArrayList<Map<String, Object>> viewList = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    //ViewPager viewPager;
    LocalActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_service);
/*

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setTitle("用户名");
*/

        activityManager = new LocalActivityManager(this, true);
        activityManager.dispatchCreate(savedInstanceState);

        tabLayout = (TabLayout)findViewById(R.id.activityCommunityTablayout);

        viewPager =(ViewPager)findViewById(R.id.activityCommunityViewPager);


        Map<String, Object> map = new HashMap<>();
        map.put("title", "轨迹");
        map.put("view", getView((String) map.get("title"),new Intent(this, MyEquipListActivity.class)));
        viewList.add(map);


        Map<String, Object> map4 = new HashMap<>();
        CommunitySDK mCommSDK = CommunityFactory.getCommSDK(this);
        mCommSDK.initSDK(this);

        CommunityMainFragment fragment = new CommunityMainFragment();
//设置Feed流页面的返回按钮不可见
        fragment.setBackButtonVisibility(View.GONE);

        map4.put("title", "论坛");
        map4.put("view", fragment);
        viewList.add(map4);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("title", "商店");
        map1.put("view", getView((String) map1.get("title"), new Intent(this, ShopWebViewActivity.class)));
        viewList.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("title", "吃药提醒");
        map2.put("view", getView((String) map2.get("title"), new Intent(this, chiyao.class)));
        viewList.add(map2);
        MainViewPaperAdapter mAdapter = new MainViewPaperAdapter(viewList, getSupportFragmentManager());
        tabLayout.setTabsFromPagerAdapter(mAdapter);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new MyViewPagerPageChangeListener());
    }

    public class MyViewPagerPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            loadActivity(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private View getView(String id, Intent intent) {
        return activityManager.startActivity(id, intent).getDecorView();
    }

    //调用子Activity发放
    private void loadActivity(int position) {
        Activity activity = activityManager.getActivity((String) viewList.get(position).get("title"));

    }

}
