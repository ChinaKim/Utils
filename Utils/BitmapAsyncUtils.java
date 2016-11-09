package com.kim.imageloader.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by kim on 16-11-8.
 *
 * 1.loadBitmapAsync        //在非UI线程中并发处理Bitmap的加载
 *
 */
public class BitmapAsyncUtils {

    /**
     * 在非UI线程中并发处理Bitmap的加载
     * @param resId
     * @param imageView
     * @param context
     */
    public void loadBitmapAsync(int resId, ImageView imageView, Context context,Bitmap mPlaceHolderBitmap) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView,context);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(context.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }


    /**
     * 创建一个专用的Drawable子类用来关联对BitmapWorkerTask的弱引用
     */
    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }


    /**
     *检查是否已有其他线程与imageView关联
     * 若有，取消之前线程（前提是新resId与原线程中不一致）
     * @param resId
     * @param imageView
     * @return
     */
    public static boolean cancelPotentialWork(int resId, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapResId = bitmapWorkerTask.getData();
            //bitmapResId不存在，或者与新resId不一致
            if (bitmapResId == 0 || bitmapResId != resId) {
                // 取消线程
                bitmapWorkerTask.cancel(true);
            } else {
                // 线程任务仍在处理中
                return false;
            }
        }
        // 没有与imageView关联的线程 或者 关联的线程已取消
        return true;
    }



    /**
     * 检索与指定imageView关联的线程:BitmapWorkerTask
     * @param imageView
     * @return
     */
    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
