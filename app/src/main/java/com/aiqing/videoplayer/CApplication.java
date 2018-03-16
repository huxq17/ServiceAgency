package com.aiqing.videoplayer;

import android.app.Application;

import com.aiqing.videoplayer.weex.WeexImageAdapter;
import com.aiqing.videoplayer.weex.WeexValueModule;
import com.imagepicker.ImagePickerModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class CApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config = new InitConfig.Builder().setImgAdapter(new WeexImageAdapter()).build();
        WXSDKEngine.initialize(this, config);
        try {
            WXSDKEngine.registerModule("SendValue", WeexValueModule.class);
            WXSDKEngine.registerModule("imagePicker", ImagePickerModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
