package com.mygps.unrelated_to_function.update.Download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by silen on 16-7-10.
 */

public class DownloadVersion {
    public static void downloadNewVersion(String path, Context context) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(path);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);

        //不显示下载界面
        request.setVisibleInDownloadsUi(true);
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*/
        //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
        long id = downloadManager.enqueue(request);
    }
}
