package com.aiqing.videoplayer;

import android.content.Context;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;


@ServiceAgent
public class CService implements IService {
    @Override
    public void init(Context context) {
        System.out.println("constants this = "+this);
    }
}
