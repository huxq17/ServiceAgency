package com.aiqing.videoplayer;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;


@ServiceAgent
public class NetworkServiceImpl implements NetworkSevice {
    @Override
    public void get(String url) {
        System.out.println("get url = " + url);
    }
}
