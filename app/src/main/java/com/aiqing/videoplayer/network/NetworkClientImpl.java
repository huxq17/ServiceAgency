package com.aiqing.videoplayer.network;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;

@ServiceAgent
public class NetworkClientImpl implements INetworkClient {
    @Override
    public void get() {
        System.out.println("test network get" );
    }

    @Override
    public void post() {
        System.out.println("test network post" );
    }
}
