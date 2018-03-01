package com.aiqing.videoplayer.imageloader;

import android.widget.ImageView;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;

@ServiceAgent
public class ImageLoaderImpl implements ImageLoader {
    private ImageLoaderImpl() {
//        throw new AssertionError();
    }

    @Override
    public void loadImage(String url, ImageView imageview) {
        Picasso.with(WXEnvironment.getApplication()).load(url).into(imageview);
    }

    @Override
    public void test(String msg) {
        System.out.println("test msg=" + msg);
    }
}
