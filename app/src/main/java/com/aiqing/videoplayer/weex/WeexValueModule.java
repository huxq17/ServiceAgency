package com.aiqing.videoplayer.weex;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

public class WeexValueModule extends WXModule {

    @JSMethod
    public void SendValueModule(String str, JSCallback callback){
//        Map<String, Object> map = new HashMap<>();
//        map.put("caicai", "my");
        callback.invoke(str+"native");
        //callback.invoke(map);
    }

    @JSMethod
    public void log(String str){
        Log.e("123", str);
    }
}