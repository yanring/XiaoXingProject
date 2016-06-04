package com.mygps.unrelated_to_function.start.HttpRequest;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10397 on 2016/6/4.
 */

public class LoginRequest extends BaseRequest {
    private OnLoginInCallback callback;
    private static final int OK = 0;
    private static final int ERROR_USERNAME = 1;
    private static final int ERROR_PASSWORD = 2;
    private static final int SERVER_NOT_AVAIBLE = 3;
    private static final String uri ="http://123.206.30.177/GPSServer/user/login.do";
    public LoginRequest( Object writeObject) throws Exception {
        super(uri, writeObject);
    }

    @Override
    void onResult(InputStream inputStream) {
        if (null != inputStream) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Map<String, Object> map = new HashMap<>();
                map = (Map<String, Object>) objectInputStream.readObject();
                if (null!=map){
                    switch ((int)map.get("error")){
                        case OK:
                            if (null!=callback)callback.onSuccess();
                            break;
                        case ERROR_PASSWORD:if (null!=callback)callback.onFail(ERROR_PASSWORD);break;
                        case ERROR_USERNAME:if (null!=callback)callback.onFail(ERROR_USERNAME);break;
                    }
                }else {
                    setERRORCallback(SERVER_NOT_AVAIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            setERRORCallback(SERVER_NOT_AVAIBLE);
        }
    }

    @Override
    public void onError(int httpErrorCode) {
        if (null != callback) {
            callback.onError(httpErrorCode);
        }
    }

    public interface OnLoginInCallback {
        void onError(int errorCode);

        void onSuccess();

        void onFail(int errorCode);
    }

    public void setOnLoginInCallback(OnLoginInCallback onLoginInCallback) {
        this.callback = onLoginInCallback;
    }

    private void setERRORCallback(int callbackInt){
        if (null!=callback){
            callback.onError(callbackInt);
        }
    }
}
