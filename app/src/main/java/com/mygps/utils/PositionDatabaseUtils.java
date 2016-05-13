package com.mygps.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 10397 on 2016/5/13.
 */
public class PositionDatabaseUtils {
    SQLiteDatabase sqLiteDatabase;
    private Context context;
    String dbName="position.db";
    private String eld=null;
    public PositionDatabaseUtils(Context context) {
        this.context=context;
        sqLiteDatabase=context.openOrCreateDatabase(dbName,SQLiteDatabase.CREATE_IF_NECESSARY,null);
        /*try{
            sqLiteDatabase.execSQL("select id from LOCATIONHISTORY");
        }catch (Exception e){
            createTableForHistory();
        }*/

       // sqLiteDatabase.execSQL("INSERT INTO LOCATIONHISTORY VALUES(0,\"da\",2.1,6,5,\"451\")");
    }

    private void createTableForHistory(){
        sqLiteDatabase.execSQL("CREATE TABLE LOCATIONHISTORY(id INTEGER,time TEXT,lat REAL,lng REAL,speed REAL,eId TEXT)");
    }

    public void insertHistory(Map<String,Object> oneHistory){
        boolean haveResult=false;
        if ((haveResult=checkHistoryIsExist(oneHistory))){

            sqLiteDatabase.execSQL("INSERT INTO LOCATIONHISTORY VALUES(" +
                    oneHistory.get("id")+
                    " \'" +oneHistory.get("time")+
                    "\' " +oneHistory.get("lat")+
                    " " +oneHistory.get("lng")+
                    " " +oneHistory.get("speed")+
                    " \'" +oneHistory.get("eId")+
                    "\')");
        }
        System.out.println(haveResult+"++++++++");
    }

    public void insertHistory(List<Map<String,Object>> historyDataList){
        for (Map<String,Object> map:historyDataList){
            insertHistory(map);
        }
    }
    public boolean checkHistoryIsExist(Map<String,Object> map){
       Cursor cursor= sqLiteDatabase.query(true,"LOCATIONHISTORY",null,"id=?s and eId='?s'",new String[]{""+ map.get("id"),(String) map.get("eId")},null,null,null,null);
        System.out.println("SELECT id FROM LOCATIONHISTORY where id="+map.get("id")+" and eId=\'"+map.get("eId")+"\'");
     //   Cursor cursor=sqLiteDatabase.rawQuery("SELECT id FROM LOCATIONHISTORY where id="+map.get("id")+" and eId=\'"+map.get("eId")+"\'",null);

        if (null==cursor){
            return false;
        }else {
            if (cursor.getColumnCount()>0){
                return true;
            }
        }
        return false;
    }

    public List<Map<String,Object>> quaryHistory(String eId){
        int num=10;
        List<Map<String,Object>> data=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM LOCATIONHISTORY where eId=\""+eld+"\" order by id DESC",null);

        if (null==cursor){
            return null;
        }
        for (int i=0;i<num&&cursor.isAfterLast();i++,cursor.moveToNext()){
            Map<String,Object> map=new HashMap<>();
            map.put("id",cursor.getInt(cursor.getColumnIndex("id")));
            map.put("time",cursor.getString(cursor.getColumnIndex("time")));
            map.put("lat",cursor.getDouble(cursor.getColumnIndex("lat")));
            map.put("lng",cursor.getDouble(cursor.getColumnIndex("lng")));
            map.put("speed",cursor.getDouble(cursor.getColumnIndex("speed")));
            map.put("eId",cursor.getDouble(cursor.getColumnIndex("eId")));
            data.add(map);
        }
        return data;
    }

    public void closeDataBase(){
        sqLiteDatabase.close();
    }
}
