package com.aiqing.videoplayer;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;

@ServiceAgent
public class NetworkServiceImpl implements NetworkService {
    private NetworkServiceImpl(){
//        throw new AssertionError();
    }
    @Override
    public void get(String url) {
        System.out.println("get url  = " + url);
    }
}
