package com.mygps.unrelated_to_function.start.HttpRequest;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 10397 on 2016/6/1.
 */
public abstract class BaseRequest {

    public BaseRequest(String uri,String parms) throws Exception {
        new RequestThread(uri, parms).start();
    }

    class RequestThread extends Thread {
        String parms;
        URL uri;

        public RequestThread(String uri, String parms) throws Exception {
            this.uri = new URL(uri);
            this.parms = parms;
        }

        @Override
        public void run() {
            super.run();
            try {
                request(uri, parms);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void request(URL url, String parms) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);
  //      connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(parms.getBytes());
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = connection.getInputStream();

        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            onResult(inputStream);
        } else {
            onError(connection.getResponseCode());
            inputStream.close();
            connection.disconnect();
        }

    }

    public abstract void onResult(InputStream inputStream);

    public abstract void onError(int httpErrorCode);

}
