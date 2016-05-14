package com.mygps.utils;

/**
 * Created by HowieWang on 2016/5/14.
 */
public class URLUtils {

    private static String server = "http://123.206.30.177/GPSServer/";
    private static String position = "position/";
    private static String user = "user/";
    private static String setting = "setting/";
    private static String current = "current.do?eId=";
    private static String previous = "previous.do?eId=";

    public static String getPreviousURL(){
        return server+position+previous;

    }

    public static String getCurrentURL(){
        return server+position+current;
    }


}
