package com.kim.imageloader.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kim on 16-10-20.
 *
 * 这是一个和网络相关的bitmap处理方法类，包含如下方法：
 * 1.getBitmapFromUrl       //根据url从网络获取图片
 * 2.getBitmapByUrl         //与方法1功能相同，只是处理方式不一样
 */
public class BitmapNetworkUtils {

    /**
     * 根据url从网络获取图片
     * @param url
     * @return
     */
    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            inputStream = imageUrl.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeQuietly(inputStream);
        }
        return bitmap;
    }

    public static Bitmap getBitmapByUrl(String url) {
        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
