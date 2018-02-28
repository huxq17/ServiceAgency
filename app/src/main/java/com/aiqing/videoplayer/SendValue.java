package com.aiqing.videoplayer;

import android.util.Log;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

public class SendValue extends WXModule {

    @JSMethod
    public void SendValueModule(String str, JSCallback callback){
        Toast.makeText(mWXSDKInstance.getContext(), str, Toast.LENGTH_SHORT).show();
//        Map<String, Object> map = new HashMap<>();
//        map.put("caicai", "my");
        callback.invokeAndKeepAlive(str+"native");
        //callback.invoke(map);
    }

    @JSMethod
    public void log(String str){
        Log.e("123", str);
    }
}