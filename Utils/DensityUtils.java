package com.kim.imageloader.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by kim on 16-9-5.
 *
 * 这是一个尺寸转化的方法类，包含如下方法：
 * 1.dip2px         //dp 的单位 转成为 px
 * 2.px2dip         //px 的单位 转成为 dp
 * 3.dip2pxMethod   //dp 的单位 转成为 px
 */
public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static  int dip2pxMethod(Context context, float dipValue)
    {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

}
