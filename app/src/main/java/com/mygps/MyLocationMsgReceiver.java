package com.mygps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class MyLocationMsgReceiver extends BroadcastReceiver {

    private ARInteraction interaction;

    @Override
    public void onReceive(Context context, Intent intent) {

        double latitude = 0;
        double longitude = 0;
        String phoneID = "";

        /**
         * 处理短信内容的代码
         */


        /**
         * 首先将数据上传到数据库，然后显示位置
         */
        interaction.updateLocation(phoneID , latitude , longitude);
        interaction.setLocation(phoneID ,latitude , longitude);
        abortBroadcast();
    }


    public interface ARInteraction{

        public void setLocation(String phoneID , double latitude , double longitude);
        public void updateLocation(String phoneID , double latitude, double longitude);

    }

    public void setARInteraction(ARInteraction interaction){
        this.interaction = interaction;
    }


}
