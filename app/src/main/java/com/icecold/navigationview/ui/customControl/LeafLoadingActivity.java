package com.icecold.navigationview.ui.customControl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.icecold.navigationview.R;
import com.icecold.navigationview.ui.base.BaseActivity;
import com.icecold.navigationview.widget.LeafLoadingView;

import java.util.Random;

/**
 *
 * Created by icecold_laptop_2 on 2018/6/13.
 */

public class LeafLoadingActivity extends BaseActivity {

    private static final int ADD_PROGRESS = 5;
    private ImageView wheel;
    private Button addButton;
    private LeafLoadingView leafLoadingView;
    private int progress;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_PROGRESS:
                    if (progress < 40){
                        progress ++;
                        mHandler.sendEmptyMessageDelayed(ADD_PROGRESS,new Random().nextInt(800));
                        leafLoadingView.setmProgress(progress);
                    }else {
                        progress ++;
                        if (progress >= 100){
                            progress = 0;
                        }
                        mHandler.sendEmptyMessageDelayed(ADD_PROGRESS,new Random().nextInt(1200));
                        leafLoadingView.setmProgress(progress);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leaf_load;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void loadData() {
        mHandler.sendEmptyMessageDelayed(ADD_PROGRESS,2000);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        leafLoadingView = findViewById(R.id.leaf_view);
        wheel = findViewById(R.id.pin_wheel);
        addButton = findViewById(R.id.add_progress);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress ++;
                leafLoadingView.setmProgress(progress);
                addButton.setText(String.valueOf(progress));
            }
        });
        //设置轮子动画
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF,0.5f);
        Interpolator interpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolator);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        //开启动画
        wheel.startAnimation(rotateAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        wheel.clearAnimation();
    }
}
