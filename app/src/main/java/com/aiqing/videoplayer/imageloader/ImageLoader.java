package com.aiqing.videoplayer.imageloader;

import android.widget.ImageView;

import com.buyi.huxq17.serviceagency.IService;

public interface ImageLoader extends IService {
    void loadImage(String url, ImageView imageview);
    void test(String msg);
}
