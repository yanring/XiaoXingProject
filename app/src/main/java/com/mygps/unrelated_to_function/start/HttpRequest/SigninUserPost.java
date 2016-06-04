package com.mygps.unrelated_to_function.start.HttpRequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mygps.unrelated_to_function.start.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10397 on 2016/6/1.
 */
public class SigninUserPost extends BaseRequest {

    public static final int SIGNIN_OK = 0;
    public static final int SIGNIN_ERROR_USEREXIST = 1;
    public static final int SIGNIN_ERROR_OTHER = 2;
    private static final int SERVER_NOT_AVAIBLE = 3;
    private static final String postUri = "http://123.206.30.177/GPSServer/user/signin.do";
    private OnSignInCallback callback;

    public SigninUserPost(User user) throws Exception {
        super(postUri, user);
    }

    @Override
    void onResult(InputStream inputStream) {
        if (null != inputStream && null != callback) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String result = reader.readLine();
                if (null != result)
                    if (result.toLowerCase().equals("true")) {
                        callback.onSuccess();
                    } else if (result.toLowerCase().equals("false")) {
                        callback.onFail();
                    } else {
                        callback.onError(SERVER_NOT_AVAIBLE);
                    }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(int httpErrorCode) {
        if (null != callback) {
            callback.onError(httpErrorCode);
        }
    }

    public interface OnSignInCallback {
        void onError(int errorCode);

        void onSuccess();

        void onFail();
    }

    public void setOnSignInCallback(OnSignInCallback onSignInCallback) {
        this.callback = onSignInCallback;
    }
}
