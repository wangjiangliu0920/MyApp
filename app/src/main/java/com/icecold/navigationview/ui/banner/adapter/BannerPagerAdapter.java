package com.icecold.navigationview.ui.banner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.icecold.navigationview.R;

import java.util.List;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/15.
 */

public class BannerPagerAdapter extends BannerAbsPagerAdapter<String> {
    private Context context;

    public BannerPagerAdapter(Context context,List<String> mData) {
        this.context = context;
        setDatas(mData);
    }

    @Override
    void setDatas(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            if (mDatas.size() == 1){
                return 1;
            }else {
                return Integer.MAX_VALUE;
            }
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mDatas != null && mDatas.size() > 0){
            View viewItem;
            //当缓存集合中没有视图数据的话就重新加载
            if (mViewCaches.isEmpty()) {
                viewItem = LayoutInflater.from(context).inflate(R.layout.banner_item, container, false);
            }else {
                //缓存集合中有数据，每次就拿出最开始的一条数据
                viewItem = (View) mViewCaches.remove(0);
            }
            //加载网络上的图片
            ImageView image = viewItem.findViewById(R.id.banner_image);
            Glide.with(context).load(mDatas.get(position % mDatas.size())).into(image);
            container.addView(viewItem);
            return viewItem;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //当页面不可见时，这个view就会被viewPager传到这个方法的Object中
        View viewItem = (View) object;
        mViewCaches.add(viewItem);
        container.removeView(viewItem);
    }
}
