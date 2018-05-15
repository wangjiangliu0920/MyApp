package com.icecold.navigationview.ui.banner;

import android.os.Bundle;

import com.icecold.navigationview.R;
import com.icecold.navigationview.ui.base.BaseActivity;
import com.icecold.navigationview.widget.MyCircleBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icecold_laptop_2 on 2018/5/15.
 */

public class BannerActivity extends BaseActivity {

    private MyCircleBanner mBanner;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void loadData() {
        List<String> mData = new ArrayList<>();
        mData.add("http://onq81n53u.bkt.clouddn.com/photo1.jpg");
        mData.add("http://onq81n53u.bkt.clouddn.com/photo2.jpg");
        mData.add("http://onq81n53u.bkt.clouddn.com/photo3333.jpg");
        mData.add("http://7xrwkh.com1.z0.glb.clouddn.com/1.jpg");
        mBanner.play(mData,this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBanner = findViewById(R.id.my_banner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.stopAdvertPlay();
    }
}
