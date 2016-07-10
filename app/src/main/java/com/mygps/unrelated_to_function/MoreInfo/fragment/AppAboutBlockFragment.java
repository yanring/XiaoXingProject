package com.mygps.unrelated_to_function.MoreInfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mygps.R;
import com.mygps.unrelated_to_function.feedback.FeedbackActivity;
import com.mygps.unrelated_to_function.update.CheckUpdateServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silen on 16-7-10.
 */

public class AppAboutBlockFragment extends Fragment {
    View layoutView;
    ListView appAboutBlockListView;
    SimpleAdapter appAboutBlockAdapter;
    ArrayList<Map<String,Object>> appAboutBlockData;

    private static final String title[] = {
            "意见反馈",
            "关于我们",
            "检查更新"
    };
    private static final int drawableId[]={
            R.drawable.abc_ic_star_black_48dp,
            R.drawable.abc_ic_star_black_48dp,
            R.drawable.abc_ic_star_black_48dp
    };

    private static final String moreInfo[] ={
            null,
            null,
            "v1.0"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView=inflater.inflate(R.layout.fragment_moreinfo_appabout_block,null);
        init();
        return layoutView;
    }

    private void init() {
        appAboutBlockListView=(ListView)layoutView.findViewById(R.id.MoreInfoAppAboutBlock);
        appAboutBlockData=new ArrayList<>();

        for (int i=0;i<title.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("imageview", drawableId[i]);
            map.put("title",title[i]);
            map.put("moreInfo", moreInfo[i]);
            appAboutBlockData.add(map);
        }

        appAboutBlockAdapter=new SimpleAdapter(getContext(),appAboutBlockData,R.layout.more_info_blocks_listviewitem,new String[]{"imageview","title","moreInfo"},new int[]{R.id.MoreInfoBlockItemImageVIew,R.id.MoreInfoBlockItemTitleTV,R.id.MoreInfoBlockItemInfoTV});

        appAboutBlockListView.setAdapter(appAboutBlockAdapter);
        appAboutBlockListView.setOnItemClickListener(new ListViewOnItemClick());
    }

    class ListViewOnItemClick implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:startActivity(new Intent(getContext(), FeedbackActivity.class));break;
                case 1:;break;
                case 2:new CheckUpdateServer(getContext(),getActivity().getSupportFragmentManager()).checkUpdateWithDialog();break;

            }
        }
    }

}
