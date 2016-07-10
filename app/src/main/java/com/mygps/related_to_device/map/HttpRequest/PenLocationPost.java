package com.mygps.related_to_device.map.HttpRequest;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.mygps.AppConf;

/**
 * Created by 10397 on 2016/5/30.
 */
public class PenLocationPost {
    RequestQueue queue;
    Context context;
    String postUri = AppConf.ServerPath+"setting/addRail.do?eId=%s&lat=%s&lng=%s&radius=%s";
    String eId;
    LatLng centLatLng,radiusLatLng;
    Activity activity;
    ProgressDialog pro;
    FragmentManager fragmentManager;
    SharedPreferences sp;
    public PenLocationPost(Context context, Activity activity, FragmentManager fragmentManager,SharedPreferences sp) {
        this.fragmentManager=fragmentManager;
        this.sp=sp;
        this.activity=activity;
        this.context = context;
        queue = Volley.newRequestQueue(context);

    }

    public void start(String eId, LatLng centLatLng, LatLng radiusLatLng){
        this.radiusLatLng=radiusLatLng;
        this.centLatLng=centLatLng;
        this.eId=eId;
        new postDialog().show(fragmentManager,null);
    }
    public void startPost() {
        showPro();
        LatLng postCentlatLng = convertBDToGps(centLatLng);
        LatLng postRadiusLatLng = convertBDToGps(radiusLatLng);
        StringRequest request=new StringRequest(String.format(postUri, new Object[]{eId, postCentlatLng.latitude + "", postCentlatLng.longitude + "", "" + Math.sqrt(Math.pow(postCentlatLng.latitude - postRadiusLatLng.latitude, 2) + Math.pow(postCentlatLng.longitude - postRadiusLatLng.longitude, 2))}), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("PostPenResponse",response);
                disPro();
                if (null!=response&&response.toLowerCase().equals("true")){
                    saveData();
                    activity.finish();
                }else{
                    new postErrorDialog().show(fragmentManager,null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disPro();
                new postErrorDialog().show(fragmentManager,null);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(6 * 1000, 1, 1.0f));
        queue.add(request);
    }

    private LatLng convertBDToGps(LatLng latLng) {
        LatLng returnLatlng = GpsRequestThread.ConvertGPS2Baidu(latLng);
        LatLng returnLatlng2 = new LatLng(2 * latLng.latitude - returnLatlng.longitude, (2 * latLng.longitude) - returnLatlng.longitude);
        return returnLatlng2;
    }

    private void saveData() {
        sp.edit().putFloat(eId + "CenterLat", (float)centLatLng.latitude).commit();
        sp.edit().putFloat(eId + "CenterLng", (float)centLatLng.longitude).commit();

        sp.edit().putFloat(eId + "RadiusLat", (float) radiusLatLng.latitude).commit();
        sp.edit().putFloat(eId + "RadiusLng", (float) radiusLatLng.longitude).commit();
    }



    class postDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("更新围栏");
            builder.setMessage("更新围栏信息吗？");
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startPost();
                }
            });
            builder.setNegativeButton("放弃更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });

            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            return builder.create();
        }
    }

    class postErrorDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("上传失败");
            builder.setMessage("围栏数据更新上传失败，请重试。");
            builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startPost();
                }
            });
            builder.setNegativeButton("放弃更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });

            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            return builder.create();
        }
    }

    private void showPro() {
        if (pro == null) {
            pro = ProgressDialog.show(context, null, "正在登录");
        } else {
            pro.show();
        }
    }

    private void disPro() {
        pro.dismiss();
    }
}
