package com.mygps.unrelated_to_function.update;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.mygps.AppConf;
import com.mygps.unrelated_to_function.update.Download.DownloadVersion;
import com.mygps.unrelated_to_function.update.model.VersionInfo;

/**
 * Created by silen on 16-7-10.
 */

public class CheckUpdateServer {
    private ProgressDialog pro;
    private static final String CHECK_UPDATE_URL = AppConf.ServerPath + "setting/checkUpdate.do";
    private static final String APP_DOWNLOAD_URL = AppConf.ServerPath + "download.do";

    private VersionInfo versionInfo;
    private RequestQueue queue;
    private Context context;

    private FragmentManager fragmentManager;

    public CheckUpdateServer(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        queue = Volley.newRequestQueue(context);
    }

    public void checkUpdateWithDialog() {
        pro = ProgressDialog.show(context, null, "正在获取新版本");
        checkUpdate();

    }

    public void checkUpdate(){
        StringRequest request=new StringRequest(CHECK_UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dissPro();
                try {
                    versionInfo = new GsonBuilder().create().fromJson(response, VersionInfo.class);
                    if (versionInfo.getCode()>InfoUtils.getAppVersionCode(context)){
                        new NewVersionDialog().show(fragmentManager,null);
                    }else {
                        Toast.makeText(context,"已经是最新版本啦",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissPro();
                System.out.print(error);
                if (null!=pro){
                    Toast.makeText(context,"出错啦",Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(request);
    }

    class NewVersionDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(versionInfo.getName());
            builder.setMessage(versionInfo.getDesc());
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DownloadVersion.downloadNewVersion(APP_DOWNLOAD_URL,context);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }

    private void dissPro(){
        if (null!=pro){
            pro.dismiss();
        }
    }
}
