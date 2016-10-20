package com.kim.imageloader.Utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by kim on 16-10-20.
 */

/**
 * this is util for closing objects that implements closeable
 * for example FileInputStream
 */
public final class CloseUtils {

    private CloseUtils(){}

    public static void closeQuietly(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
