package com.icecold.navigationview.ui.banner.adapter;

import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/15.
 */

abstract class BannerAbsPagerAdapter<T> extends PagerAdapter {
    ArrayList<Object> mViewCaches = new ArrayList<>();
    List<T> mDatas;
    void setDatas(List<T> datas){

    }
}
