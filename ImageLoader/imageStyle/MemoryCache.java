package com.kim.imageloader.ImageLoader.imageStyle;


import android.graphics.Bitmap;
import android.util.LruCache;

import com.kim.imageloader.ImageLoader.imageImpl.ImageCache;

/**
 * Created by kim on 16-10-20.
 */
public class MemoryCache implements ImageCache {
    private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCache(){
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemory()){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight()/1024;
            }
        };
    }



    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
            mMemoryCache.put(url,bitmap);
    }


    /**
     * get the cache memory which is 1/4 of maxMemory
     * @return
     */
    public int cacheMemory(){
        final  int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        return maxMemory/4;
    }

}
