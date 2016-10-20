package com.kim.imageloader.ImageLoader.imageStyle;

import android.graphics.Bitmap;

import com.kim.imageloader.ImageLoader.imageImpl.ImageCache;

/**
 * Created by kim on 16-10-20.
 */
public class DoubleCache implements ImageCache {
    ImageCache mMemoryCache = new MemoryCache();
    ImageCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null)
            bitmap = mDiskCache.get(url);
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }
}
