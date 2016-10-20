package com.kim.imageloader.ImageLoader.imageStyle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kim.imageloader.ImageLoader.imageImpl.ImageCache;
import com.kim.imageloader.Utils.CloseUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kim on 16-10-20.
 */
public class DiskCache implements ImageCache {

    static String cacheDir = "sdcard/cache/";

    /**
     * get image from sdcard
     * @param url
     * @return
     */
    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    /**
     * put a image into sdcard
     * @param url
     * @param bitmap
     */
    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeQuietly(fileOutputStream);
        }

    }



}
