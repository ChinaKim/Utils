package com.kim.imageloader.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by kim on 16-11-8.
 *
 * 异步处理Bitmap
 */
public class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private Context context;

    public BitmapWorkerTask(ImageView imageView,Context context) {
        this.context = context;
        //弱引用保证ImageView能被及时回收
        imageViewReference = new WeakReference<>(imageView);
    }


    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return BitmapHandlerUtils.decodeSampledBitmapFromResource(context.getResources(), data, 100, 100);
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask =
                    BitmapAsyncUtils.getBitmapWorkerTask(imageView);
            //检查当前线程是否是与imageView关联的线程
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public int getData() {
        return data;
    }

}
