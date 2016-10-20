package com.kim.imageloader.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kim on 16-10-20.
 * <p>
 * 这是一个处理bitmap的方法类，包含如下方法：
 * 1.getRoundedCornerBitmap   //使bitmap变成指定圆角大小的图片
 * 2.duplicateBitmap          //复制bitmap
 * 3.matrixBitmap             //旋转bitmap
 * 4.bitampToByteArray        //将bitmap转成字节数组
 * 5.saveImageToLocal         //将bitmap保存到本地指定路径
 * 6.compressBitmap           //质量压缩
 * 7.DimensionCompress        //尺寸压缩
 * 8.readImageDegree          //获取图片角度
 */

public class BitmapHandlerUtils {

    /**
     * 处理bitmap，使其变成指定圆角大小的图片
     *
     * @param bmpSrc
     * @param rx
     * @param ry
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bmpSrc, float rx, float ry) {
        if (null == bmpSrc) return null;

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawColor(Color.TRANSPARENT);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, rx, ry, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bmpSrc, rect, rect, paint);
        }
        return bmpDest;
    }


    /**
     * 复制bitmap
     *
     * @param bmpSrc
     * @return
     */
    public static Bitmap duplicateBitmap(Bitmap bmpSrc) {
        if (null == bmpSrc) return null;

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }
        return bmpDest;
    }


    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap matrixBitmap(Bitmap bitmap, Matrix matrix ) {
        return  Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 将bitmap转成字节数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitampToByteArray(Bitmap bitmap) {
        byte[] array = null;
        try {
            if (null != bitmap) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                array = os.toByteArray();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }


    /**
     * 将bitmap保存到本地指定路径
     *
     * @param bitmap
     * @param dirName 文件夹名
     * @param name    文件名
     */
    public static void saveImageToLocal(Bitmap bitmap, String dirName, String name) {
        FileOutputStream outputStream = null;
        try {
            File dir = new File(dirName);
            if (!dir.exists())    // 判断文件夹是否存在，不存在则创建
                dir.mkdir();

            File file = new File(dirName + name);
            if (!file.exists())  // 判断文件是否存在，不存在则创建
                file.createNewFile();

            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeQuietly(outputStream);
        }
    }


    /**
     * 质量压缩
     * @param bitmap    //源bitmap
     * @param size      //压缩到指定大小 单位：kb
     * @return
     */
    private static Bitmap compressBitmap(Bitmap bitmap,int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmapResult = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

        try {
            baos.close();
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapResult;
    }


    /**
     * 尺寸压缩
     * 压缩出来的图片尺寸宽高都不超过指定值
     * @param path
     * @param resultWidth  目标宽
     * @param resultHeight 目标高
     * @return
     */
    public static Bitmap DimensionCompress(String path,int resultWidth,int resultHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        float tempHeight = resultHeight;
        float tempWidth = resultWidth;
        int zoomScale = 1;
        if (sourceWidth > sourceHeight && sourceWidth > tempWidth) {
            zoomScale = (int) (sourceWidth / tempWidth);
        } else if (sourceHeight > sourceWidth && sourceHeight > tempHeight) {
            zoomScale = (int) (sourceHeight / tempHeight);
        }
        // 缩放因子小于0按0计算
        if (zoomScale <= 0)
            zoomScale = 1;
        options.inSampleSize = zoomScale;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        // 在内存中创建bitmap对象，这个对象按照缩放大小创建的
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return bitmap;
    }



    /**
     * 获取图片角度，
     * @param path
     * @return
     */
    public static int readImageDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
