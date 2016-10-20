package com.kim.imageloader.Utils;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kim on 16-10-20.
 */
public class MeadiaPlayUtils {
    /**
     * 装载文件到mediaplayer
     *
     * @param context
     * @param mp
     * @param fileInfo
     * @throws Exception
     */
    public static void setMediaPlayerDataSource(Context context,
                                                MediaPlayer mp, String fileInfo){
        if (fileInfo.startsWith("content://")) {
            Uri uri = Uri.parse(fileInfo);
            fileInfo = getRingtonePathFromContentUri(context, uri);
        }
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                try {
                    setMediaPlayerDataSourcePreHoneyComb(mp, fileInfo);
                } catch (Exception e) {
                    setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo);
                }
            } else {
                setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo);
            }
        } catch (Exception e) {
            try {
                setMediaPlayerDataSourceUsingFileDescriptor(mp, fileInfo);
            } catch (Exception ee) {
                String uri = getRingtoneUriFromPath(context, fileInfo);
                mp.reset();
                try {
                    mp.setDataSource(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    private static void setMediaPlayerDataSourceUsingFileDescriptor(MediaPlayer mp, String fileInfo) {
        File file = new File(fileInfo);
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            mp.reset();
            mp.setDataSource(inputStream.getFD());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeQuietly(inputStream);
        }

    }

    private static void setMediaPlayerDataSourcePreHoneyComb(MediaPlayer mp, String fileInfo) {
        mp.reset();
        try {
            mp.setDataSource(fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setMediaPlayerDataSourcePostHoneyComb(
            Context context, MediaPlayer mp, String fileInfo) {
        mp.reset();
        try {
            mp.setDataSource(context, Uri.parse(Uri.encode(fileInfo)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRingtonePathFromContentUri(Context context,
                                                       Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor ringtoneCursor = context.getContentResolver().query(contentUri,
                proj, null, null, null);
        ringtoneCursor.moveToFirst();

        String path = ringtoneCursor.getString(ringtoneCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

        ringtoneCursor.close();
        return path;
    }

    private static String getRingtoneUriFromPath(Context context, String path) {
        Uri ringtonesUri = MediaStore.Audio.Media.getContentUriForPath(path);
        Cursor ringtoneCursor = context.getContentResolver().query(
                ringtonesUri, null,
                MediaStore.Audio.Media.DATA + "='" + path + "'", null, null);
        while (ringtoneCursor.moveToFirst()) {
            long id = ringtoneCursor.getLong(ringtoneCursor.getColumnIndex(MediaStore.Audio.Media._ID));
            ringtoneCursor.close();

            if (!ringtonesUri.toString().endsWith(String.valueOf(id))) {
                return ringtonesUri + "/" + id;
            }
        }

        return ringtonesUri.toString();
    }


}
