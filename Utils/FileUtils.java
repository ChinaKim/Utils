package com.kim.imageloader.Utils;

import java.io.File;

/**
 * Created by kim on 16-10-20.
 *
 * 这是一个处理文件相关的方法类，方法如下：
 * 1.deleteFile         //删除文件
 * 2.
 *
 */
public class FileUtils {


    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

}
