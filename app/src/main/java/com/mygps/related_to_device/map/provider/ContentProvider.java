package com.mygps.related_to_device.map.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mygps.related_to_device.map.DatabaseHelper.GpsDatabaseHelper;

/**
 * Created by Yanring on 2016/5/13.
 */
public class ContentProvider extends android.content.ContentProvider {
    private static UriMatcher sUriMatcher;
    String mTableName = GpsDatabaseHelper.TABLE_NAME;
    public  static final  int URI_MATH_WHEATHER = 1;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(URIList.AUTHORITY, GpsDatabaseHelper.TABLE_NAME,1);
    }

    private GpsDatabaseHelper mGpsDatabaseHelper;
    @Override
    public boolean onCreate() {
        mGpsDatabaseHelper = new GpsDatabaseHelper(this.getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName = mTableName;
        if (TextUtils.isEmpty(tableName)){
            return null;
        }
        Cursor cursor = mGpsDatabaseHelper.getReadableDatabase().query(tableName,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = mTableName;
        if (TextUtils.isEmpty(tableName)){
            return null;
        }
        long id = mGpsDatabaseHelper.getWritableDatabase().insert(tableName,null,values);
        //insert will return a id, to recognize which item you inserted.

        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = mTableName;
        if (TextUtils.isEmpty(tableName)){
            return -1;
        }
        int count = mGpsDatabaseHelper.getWritableDatabase().delete(tableName,selection,selectionArgs);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
