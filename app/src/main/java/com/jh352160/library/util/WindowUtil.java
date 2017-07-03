package com.jh352160.library.util;

import android.content.Context;

/**
 * Created by jh352160 on 2017/7/3.
 */

public class WindowUtil {
    /**
     * 获取屏幕宽度.
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度.
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高度.
     */
    public static int getDeviceDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取状态栏高度.
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
