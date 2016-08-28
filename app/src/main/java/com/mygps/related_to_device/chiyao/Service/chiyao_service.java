package com.mygps.related_to_device.chiyao.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygps.related_to_device.chiyao.Entity.chiyao_addremind_entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2016/3/22.
 */
public class chiyao_service {
    public chiyao_service(){
        super();
    }
    public final String Encodding="utf-8";
    public final static String downLoad_chiyao_URL = "http://115.29.136.135:8080/web/Chiyao_addremind_select";
    public final static String upLoad_chiyao_URL = "http://115.29.136.135:8080/web/Chiyao_addremind_save";
    public final static String change_chiyao_URL = "http://115.29.136.135:8080/web/Chiyao_addremind_update";
    public final static String delete_chiyao_URL = "http://115.29.136.135:8080/web/Chiyao_addremind_delete";
    public final static String downLoad_chiyao_all_URL = "http://115.29.136.135:8080/web/Chiyao_addremind_selectall";
    /*
    * 上传吃药的信息
    * */
    public static boolean uploadUserInfo(String medision_name, String time,String equip_name,String equip_id) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("medision_name", medision_name);
            params.put("time", time);
            params.put("equip_name", equip_name);
            params.put("equip_id", equip_id);
            HttpUtils httpUtils=new HttpUtils();
            String str= HttpUtils.submitPostData(params, "utf-8",upLoad_chiyao_URL);
            return str != null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }
    /*
    * 改变吃药的信息
    * */
    public static boolean change_UserInfo(String id, String medision_name,String time,String equip_name,String equip_id) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            params.put("medision_name", medision_name);
            params.put("time", time);
            params.put("equip_name",equip_name);
            params.put("equip_id",equip_id);
            HttpUtils httpUtils=new HttpUtils();
            String str= HttpUtils.submitPostData(params, "utf-8",change_chiyao_URL);
            return str != null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }
    /*
    * 下载吃药信息
    * */
    public static List<chiyao_addremind_entity> downloadUserInfo(String equip_name, String equip_id) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("equip_name", equip_name);
            params.put("equip_id", equip_id);
            HttpUtils httpUtils=new HttpUtils();
            String str= HttpUtils.submitPostData(params, "utf-8",downLoad_chiyao_URL);
            Gson gson=new Gson();
            List<chiyao_addremind_entity> ps = gson.fromJson(str, new TypeToken<List<chiyao_addremind_entity>>() {
            }.getType());
            return ps;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
   * 下载吃药信息
   * */
    public static List<chiyao_addremind_entity> downloadUserInfoall(String equip_name, String equip_id) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("equip_name", equip_name);
            params.put("equip_id", equip_id);
            HttpUtils httpUtils=new HttpUtils();
            String str= HttpUtils.submitPostData(params, "utf-8",downLoad_chiyao_all_URL);
            Gson gson=new Gson();
            List<chiyao_addremind_entity> ps = gson.fromJson(str, new TypeToken<List<chiyao_addremind_entity>>() {
            }.getType());
            return ps;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 删除吃药的信息
    * */
    public static boolean deleteUserInfo(String id) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            HttpUtils httpUtils=new HttpUtils();
            String str= HttpUtils.submitPostData(params, "utf-8",delete_chiyao_URL);
            return str != null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
