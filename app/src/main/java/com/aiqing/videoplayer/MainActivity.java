package com.aiqing.videoplayer;

import android.os.Bundle;

import com.buyi.huxq17.serviceagency.ServiceAgency;
import com.taobao.weex.WXSDKInstance;

import java.util.HashMap;
import java.util.Map;

import cn.jzvd.JZVideoPlayerStandard;

public class MainActivity extends WeexActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JZVideoPlayerStandard player = findViewById(R.id.videoplayer);
        player.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
//        player.setUp();
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        NetworkService service = ServiceAgency.getService(NetworkService.class);
        service.get("https://www.baidu.com");
    }

    @Override
    public String getPath() {
        return "dist/index.js";
    }

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, "file://hello.js");
        return null;
    }
}
