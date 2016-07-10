package com.mygps.unrelated_to_function.start.HttpRequest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mygps.AppConf;
import com.mygps.MyApplication;
import com.mygps.related_to_device.map.model.Equipment;
import com.mygps.unrelated_to_function.start.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10397 on 2016/6/4.
 */

public class LoginRequest extends BaseRequest {
    MyApplication app;
    private OnLoginInCallback callback;
    private static final int OK = 0;
    private static final int ERROR_USERNAME = 1;
    private static final int ERROR_PASSWORD = 2;
    private static final int SERVER_NOT_AVAIBLE = 3;
    private static final String uri = AppConf.ServerPath+"user/login.do";

    public LoginRequest(User user, Activity activity) throws Exception {
        super(uri, "username=" + user.getUsername() + "&password=" + user.getPassword());
        app = (MyApplication) activity.getApplication();
    }

    @Override
    public void onResult(InputStream inputStream) {
        if (null != inputStream) {
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                String line=null;
                String result=null;
                try {
                    while((line=reader.readLine())!=null){
                        Log.i("LINE",line);
                        if (null==result){
                            result= URLDecoder.decode(line,"UTF-8");
                        }else {
                            result+=URLDecoder.decode(line,"UTF-8");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                parseJson(result);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setERRORCallback(SERVER_NOT_AVAIBLE);
        }
    }

    @Override
    public void onError(int httpErrorCode) {
        if (null != callback) {
            callback.onError(httpErrorCode);
        }
    }

    public interface OnLoginInCallback {
        void onError(int errorCode);

        void onSuccess();

        void onFail(int errorCode);
    }

    public void setOnLoginInCallback(OnLoginInCallback onLoginInCallback) {
        this.callback = onLoginInCallback;
    }

    private void setERRORCallback(int callbackInt) {
        if (null != callback) {
            callback.onError(callbackInt);
        }
    }
    private Map<String,Object> parseJson(String jsonString){
        Map<String,Object> map=new HashMap<>();
        try {

            Log.i("JsonParse",jsonString);

            JSONObject jsonObject=new JSONObject(jsonString);

            map.put("error",jsonObject.get("error"));

            if (0==(int)jsonObject.get("error")){
                JSONArray jsonArrayEquips=jsonObject.getJSONArray("equips");
                List<Equipment> list=new ArrayList<>();
                for (int i=0;i<jsonArrayEquips.length();i++){
                    JSONObject jsonObjectEquip=(JSONObject)jsonArrayEquips.get(i);
                    Equipment equipment=new Equipment();
                    equipment.setId((int)jsonObjectEquip.get("id"));
                    equipment.seteId((String) jsonObjectEquip.get("eId"));
                    equipment.setName((String) jsonObjectEquip.get("name"));
                  //  equipment.setPhone((String) jsonObjectEquip.get("phone"));
                    equipment.setuId((int) jsonObjectEquip.get("uId"));
                    list.add(equipment);
                }
                app.setEquips(list);
                JSONObject jsonObjectUser=jsonObject.getJSONObject("user");
                Log.i("json",jsonObjectUser.toString());
                User user=new User();
                user.setPassword(jsonObjectUser.getString("password"));
                Log.i("pw0",user.getPassword());
                user.setUsername(jsonObjectUser.getString("username"));
                Log.i("un",user.getUsername());
                user.setId(jsonObjectUser.getInt("id"));

                app.setUser(user);

                if (null != callback) callback.onSuccess();

            }else if(ERROR_USERNAME==(int)jsonObject.get("error")){
                if (null != callback) callback.onFail(ERROR_USERNAME);
            }else if (ERROR_PASSWORD==(int)jsonObject.get("error")){
                if (null != callback) callback.onFail(ERROR_PASSWORD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


}
