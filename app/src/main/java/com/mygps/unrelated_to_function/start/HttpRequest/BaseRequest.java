package com.mygps.unrelated_to_function.start.HttpRequest;

import android.content.Context;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 10397 on 2016/6/1.
 */
public abstract class BaseRequest {

    public BaseRequest(String uri,Object writeObject) throws Exception {
        new RequestThread(uri,writeObject).run();
    }

    class RequestThread extends Thread {
        Object writeObject;
        URL uri;
        public RequestThread(String uri,Object writeObject) throws Exception {
            this.uri=new URL(uri);
            this.writeObject=writeObject;
        }

        @Override
        public void run() {
            super.run();
            try {
                request(uri,writeObject);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void request(URL url,Object writeObject) throws Exception {
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
        ObjectOutputStream outputStream=new ObjectOutputStream(connection.getOutputStream());
        outputStream.writeObject(writeObject);
        outputStream.flush();
        outputStream.close();
        InputStream inputStream=connection.getInputStream();
        if (HttpURLConnection.HTTP_OK==connection.getResponseCode()){
            onResult(inputStream);
        }else {
            onError(connection.getResponseCode());
            inputStream.close();
            connection.disconnect();
        }

    }

    abstract void onResult(InputStream inputStream);
    abstract void onError(int httpErrorCode);

}
