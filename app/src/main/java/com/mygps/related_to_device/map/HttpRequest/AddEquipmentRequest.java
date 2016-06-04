package com.mygps.related_to_device.map.HttpRequest;

import com.mygps.unrelated_to_function.start.HttpRequest.BaseRequest;

import java.io.InputStream;

/**
 * Created by 10397 on 2016/6/4.
 */

public class AddEquipmentRequest extends BaseRequest {

    private static final String uri ="http://123.206.30.177/GPSServer/user/addEquip.do";

    public AddEquipmentRequest(Object writeObject) throws Exception {
        super(uri, writeObject);
    }

    @Override
    public void onResult(InputStream inputStream) {

    }

    @Override
    public void onError(int httpErrorCode) {

    }
}
