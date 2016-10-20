package com.kim.imageloader.Utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kim on 16-10-20.
 *
 * 这是一个处理Base64相关的方法类，方法如下：
 * 1.encodeBase64File           //将文件转为Base64字符串
 * 2.encodeBase64Bitmap         //将Bitmap转为Base64字符串
 *
 */
public class Base64Utils {
    /**
     * 将文件转为Base64字符串
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }



    /**
     * 将Bitmap转为Base64字符串
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encodeBase64Bitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
            // 将bitmap做成输出流，保证图片质量100%
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 转换成byte数组
            byte[] appicon = baos.toByteArray();// 转为byte数组
            // 以base64编码将byte数组转换成字符串
            return Base64.encodeToString(appicon, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
