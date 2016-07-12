package com.mygps.unrelated_to_function.feedback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mygps.AppConf;
import com.mygps.unrelated_to_function.feedback.model.Feedback;
import com.mygps.unrelated_to_function.update.InfoUtils;


/**
 * Created by silen on 16-7-10.
 */

public class FeedbackServer {
    private Context context;
    private FragmentManager fragmentManager;
    private RequestQueue requestQueue;
    private static final String FEEDBACK_URI = AppConf.ServerPath+"user/submitFeedback.do?uId=%d&desc=%s";
    private Feedback feedback;
    Activity activity;
    public FeedbackServer(Context context, Activity activity, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity=activity;
        requestQueue = Volley.newRequestQueue(context);
        initFeedback();
    }
    private void initFeedback(){
        feedback=new Feedback();
        feedback.setApi_level(InfoUtils.getAppVersionCode(context));
        feedback.setMODEL(Build.MODEL);
        feedback.setSystemVersion(Build.VERSION.RELEASE);

    }
    public void send(final String contact, String content, int uId){
        final ProgressDialog pro= ProgressDialog.show(context, null, "正在提交反馈。。。");
        feedback.setContact(contact);
        feedback.setContent(content);
        StringRequest request=new StringRequest(String.format(FEEDBACK_URI, new Object[]{uId, new Gson().toJson(feedback)}).replaceAll(" ",""), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pro.dismiss();
                Toast.makeText(context,"感谢您的反馈！",Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pro.dismiss();
                Toast.makeText(context,"好像出错了耶",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

}
