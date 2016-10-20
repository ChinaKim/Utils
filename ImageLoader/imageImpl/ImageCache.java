package com.kim.imageloader.ImageLoader.imageImpl;

import android.graphics.Bitmap;

/**
 * Created by kim on 16-10-20.
 */
public interface ImageCache {
    public Bitmap get(String url);
    public void put(String url,Bitmap bitmap);
}
