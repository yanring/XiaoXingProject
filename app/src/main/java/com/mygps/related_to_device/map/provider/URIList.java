package com.mygps.related_to_device.map.provider;

import com.mygps.related_to_device.map.DatabaseHelper.GpsDatabaseHelper;

/**
 * Created by Yanring on 2016/5/13.
 */
public class URIList {
    public static final  String CONTENT = "content://";
    public static final  String AUTHORITY= "com.mygps";
    public static final  String GPS_URI = CONTENT+AUTHORITY+"/"+ GpsDatabaseHelper.TABLE_NAME;
}
