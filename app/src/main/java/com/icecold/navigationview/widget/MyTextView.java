package com.icecold.navigationview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/16.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    private int width;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
//        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90);
//        canvas.translate(-getHeight(),0);
        canvas.translate(0,-getWidth());
//        Logger.d("宽度width = "+getWidth());
//        Logger.d("宽度width2 = "+width);
        super.onDraw(canvas);
    }
}
