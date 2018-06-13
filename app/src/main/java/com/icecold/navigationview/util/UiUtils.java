package com.icecold.navigationview.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 *
 * Created by icecold_laptop_2 on 2018/6/12.
 */

public class UiUtils {
    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }
}
