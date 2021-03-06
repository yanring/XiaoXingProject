package com.mygps.related_to_device.map.HttpRequest;

import com.mygps.AppConf;
import com.mygps.related_to_device.map.model.Equipment;
import com.mygps.unrelated_to_function.start.HttpRequest.BaseRequest;

import java.io.InputStream;

/**
 * Created by 10397 on 2016/6/4.
 */

public class AddEquipmentRequest extends BaseRequest {

    private static final String uri = AppConf.ServerPath+"user/addEquip.do";

    public AddEquipmentRequest(Equipment equipment) throws Exception {
        super(uri, "");
    }

    @Override
    public void onResult(InputStream inputStream) {

    }

    @Override
    public void onError(int httpErrorCode) {

    }
}
