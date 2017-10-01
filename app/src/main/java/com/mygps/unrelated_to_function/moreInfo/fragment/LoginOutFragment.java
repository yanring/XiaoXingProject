package com.mygps.unrelated_to_function.moreInfo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.unrelated_to_function.start.WelcomeActivity;

/**
 * Created by silen on 16-7-12.
 */

public class LoginOutFragment extends Fragment {

    private static final int ImageViewResource = R.mipmap.radius_position_icon;
    View layoutView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_moreinfo_loginout, null);
        init();
        return layoutView;
    }

    private void init() {
        ((ImageView) layoutView.findViewById(R.id.MoreInfoBlockItemImageView)).setImageDrawable(null);
        //setImageResource(ImageViewResource);
        ((TextView) layoutView.findViewById(R.id.MoreInfoBlockItemTitleTV)).setText(((MyApplication) getActivity().getApplication()).getUser().getUsername());
        ((TextView) layoutView.findViewById(R.id.MoreInfoBlockItemtInfoTV)).setText("退出登录");
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginOutDialog().show(getActivity().getSupportFragmentManager(),null);
            }
        });
    }

    class LoginOutDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle("确认");
            builder.setMessage("确定退出本帐号么？");
            builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sp=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                    sp.edit().putBoolean("login",false).commit();
                    startActivity(new Intent(getContext(), WelcomeActivity.class));
                    getActivity().finish();
                }
            });
            builder.setNegativeButton("取消",null);
            return builder.create();
        }
    }
}
