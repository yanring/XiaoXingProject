package com.mygps.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10397 on 2016/5/13.
 */
public class PositionDateFromHttp {
    private static String currentPositionUri="http://123.206.30.177/GPSServer/position/current.do?eld=";
    private static String previousPositionUri="http://123.206.30.177/GPSServer/position/previous.do?eld=";
    private String eld=null;
    public PositionDateFromHttp(String eld) {
        this.eld=eld;
        System.out.println("测试Class++++++++++++++++++++++++++++++++");
    }


    class DataThread extends Thread{
        String url=null;

        public DataThread(String url) {
            this.url=url;
        }

        @Override
        public void run() {
            try {
                System.out.println("测试run++++++++++++++++++++++++++++++++");
                parseJson(getJsonString(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private String getJsonString(String urlPath) throws Exception {
        System.out.println("测试mothed++++++++++++++++++++++++++++++++");
        String jsonString = null;
        HttpURLConnection connection = (HttpURLConnection) new URL(urlPath).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.setConnectTimeout(50000);
        InputStream in = connection.getInputStream();
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                if (null == jsonString) {
                    jsonString = new String(buffer, 0, len);
                } else {
                    jsonString += new String(buffer, 0, len);
                }
            }
        }
        System.out.println(jsonString);
        return jsonString;
    }


    private List<Map<String,String>> parseJson(String jsonString) throws JSONException {
        List<Map<String,String>> data=new ArrayList<>();
        JSONArray jsonArray=new JSONArray(jsonString);
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=(JSONObject)jsonArray.get(i);
            System.out.println("time"+jsonObject.get("time"));
        }
        return data;
    }

    public void getPreviousDate(){
        DataThread dataThread=new DataThread(previousPositionUri+eld);
        dataThread.start();
    }
}
