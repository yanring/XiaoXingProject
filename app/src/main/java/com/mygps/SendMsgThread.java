package com.mygps;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class SendMsgThread extends Thread {

    String phoneID;
    Long sleepTime;
    boolean stop = false;

    /**
     * 默认的stop是false，说明需要持续发送短信命令获取位置
     * 如果使用第二种构造方法，传入stop为true，说明仅仅发送一次短信命令
     */
    public SendMsgThread(String phoneID, Long sleepTime) {
        this.phoneID = phoneID;
        this.sleepTime = sleepTime;
    }

    /**
     * 使用第二种构造方，无论stop传入什么参数，stop都将设置成true
     */
    public SendMsgThread(String phoneID, Long sleepTime, boolean stop) {
        this.phoneID = phoneID;
        this.sleepTime = sleepTime;
        this.stop = true;
    }

    @Override
    public void run() {

        SmsManager manager = SmsManager.getDefault();
        do{

            /**
             * 这样发送出去的短信，本地保留的内容是
             * ss[MyGPS]
             * 对方收到的是
             * ss
             */
            //manager.sendTextMessage(phoneID, null, "JW#", null, null);
            Log.i("aaa","发送一条命令");

            if (stop){
                return;
            }

            try {
                Log.i("aaa","等待");
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }while(!stop);


    }

    @Override
    public void interrupt() {
        Log.i("aaa","命令线程已打断");
        super.interrupt();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
