package com.mygps.unrelated_to_function.MoreInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygps.R;

/**
 * Created by silen on 16-7-9.
 */

public class MoreInfoFragment extends Fragment {
    View layoutView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView=inflater.inflate(R.layout.fragment_more_info,null);
        init();
        return layoutView;
    }

    private void init(){

        initLoginOut();

    }

    private void initLoginOut(){
        ((CardView)layoutView.findViewById(R.id.MoreInfoLoginOut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login out

            }
        });
    }
}
