package com.mygps.related_to_extra_function.MoreInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mygps.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silen on 16-7-9.
 */

public class MoreInfoFragment extends Fragment {
    View layoutView;
    ListView firstBlockListView;
    SimpleAdapter firstBlockAdapter;
    ArrayList<Map<String,Object>> firstBlockData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView=inflater.inflate(R.layout.fragment_more_info,null);
        init();
        return layoutView;
    }

    private void init(){
        firstBlockListView=(ListView)layoutView.findViewById(R.id.MoreInfoFirstBlock);
        firstBlockData=new ArrayList<>();

        for (int i=0;i<5;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageview", R.drawable.abc_ic_star_black_48dp);
            map.put("title", "test");
            map.put("moreInfo", "无");
            firstBlockData.add(map);
        }

        firstBlockAdapter=new SimpleAdapter(getContext(),firstBlockData,R.layout.more_info_blocks_listviewitem,new String[]{"imageview","title","moreInfo"},new int[]{R.id.MoreInfoBlockItemImageVIew,R.id.MoreInfoBlockItemTitleTV,R.id.MoreInfoBlockItemInfoTV});

        firstBlockListView.setAdapter(firstBlockAdapter);


    }

}
