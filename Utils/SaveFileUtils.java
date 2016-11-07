package com.kim.imageloader.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kim on 16-11-7.
 */


/**
 * 注：当用户卸载您的应用时，Android 系统会删除以下各项：
 *您保存在内部存储中的所有文件
 *您使用 getExternalFilesDir() 保存在外部存储中的所有文件。
 *但是，您应手动删除使用 getCacheDir() 定期创建的所有缓存文件并且定期删除不再需要的其他文件。
 */
public class SaveFileUtils {


    /**
     * 创建内部目录的 File
     * @param context
     * @param filename
     * @return
     */
    public static File createFilesDir(Context context,String filename){
        return new File(context.getFilesDir(), filename);
    }

    /**
     * 使用openFileOutput写入文件
     * @param context
     * @param filename
     * @param srcStr
     */
    public static void createFileStream(Context context,String filename,String srcStr){
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            outputStream.write(srcStr.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存文件
     * @param context
     * @param url
     * @return
     */
    public static File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }


    //-----------------------外部存储------------------------------------------

    /**
     * 检查外部存储是否可读写
     * @return
     */
    public static boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
        return false;
    }

    /**
     * 检查外部存储是否至少是可读的
     * @return
     */
    public static boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        return false;
    }


    /**
     * 指定保存到公共文件上，比如 DIRECTORY_MUSIC 或 DIRECTORY_PICTURES
     * @param albumName
     * @return
     */
    public static File getAlbumStorageDir(String albumName){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),albumName);
        if (!file.mkdir()){
            Log.e("TAG", "Directory not created");
        }
        return file;
    }

    /**
     * 如果您要保存您的应用专用文件，您可以通过调用 getExternalFilesDir() 并向其传递指示您想要的目录类型的名称，
     * 从而获取相应的目录。通过这种方法创建的各个目录将添加至封装您的应用的所有外部存储文件的父目录，
     * 当用户卸载您的应用时，系统会删除这些文件。
     * 例如，您可以使用以下方法来创建个人相册的目录：
     *
     * 当您的应用是照相机并且用户要保留照片时—您应改用 getExternalStoragePublicDirectory()。
     * @param context
     * @param albumName
     * @return
     */
    public File getAlbumStorageDir(Context context, String albumName) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("TAG", "Directory not created");
        }
        return file;
    }


}
