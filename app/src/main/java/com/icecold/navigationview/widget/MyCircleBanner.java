package com.icecold.navigationview.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.icecold.navigationview.ui.banner.adapter.BannerPagerAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/15.
 */

public class MyCircleBanner extends ViewPager {
    //数据源的长度用于计算位置
    private int size;
    //当前选中的位置
    private int mSelectedIndex;
    private CompositeDisposable compositeDisposable;

    public MyCircleBanner(Context context) {
        this(context,null);
    }

    public MyCircleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //解决在布局中设置wrap_content无效的问题
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
            int measuredHeight = child.getMeasuredHeight();
            if (measuredHeight > height){
                height = measuredHeight;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 轮播图片状态监听
     */
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Logger.d("onPageScrolled------->"
//                +"      position:"+position
//                +"      positionOffset:"+positionOffset
//                +"      positionOffsetPixels:"+positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            mSelectedIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_SETTLING) {
                Logger.d("手指离开屏幕了");
                startAdvertPlay();
            }
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                Logger.d("滑动动画结束了");
            }
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                Logger.d("产生了拖拽");
                stopAdvertPlay();
            }
        }
    };
    public void play(List<String> mDataUrl,Context context){
        if (mDataUrl != null && mDataUrl.size() > 0){
            size = mDataUrl.size();
            BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(context,mDataUrl);
            //设置adapter
            setAdapter(bannerPagerAdapter);
            //设置监听器
            addOnPageChangeListener(mOnPageChangeListener);
            setCurrentItem(getInitPosition());
            startAdvertPlay();
        }
    }

    private void startAdvertPlay() {
        stopAdvertPlay();
        //开始计时器
        addSubscribe(Observable.interval(3,1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (mSelectedIndex == Integer.MAX_VALUE){
                                //几乎不可能的
                                int rightOffset = mSelectedIndex % size;
                                setCurrentItem(getInitPosition() + rightOffset + 1,true);
                            }else {
                                setCurrentItem(mSelectedIndex+1,true);
                            }
                        }
                    }));
    }

    public void stopAdvertPlay() {
        removeSubscribe();
    }

    private int getInitPosition() {

        int halfValue = Integer.MAX_VALUE / 2;
        int offset = halfValue % size;

        return halfValue - offset;

    }
    private void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    private void removeSubscribe(){
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}
