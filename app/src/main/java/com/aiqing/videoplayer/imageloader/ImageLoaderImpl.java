package com.aiqing.videoplayer.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;
import com.squareup.picasso.Picasso;

@ServiceAgent
public class ImageLoaderImpl implements ImageLoader {
    private Context context;

    private ImageLoaderImpl() {
//        throw new AssertionError();
    }

    @Override
    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void loadImage(String url, ImageView imageview) {
        Picasso.with(context).load(url).into(imageview);
    }

    @Override
    public void test(String msg) {
        System.out.println("test msg=" + msg);
    }
}
