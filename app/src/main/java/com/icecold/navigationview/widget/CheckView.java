package com.icecold.navigationview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.icecold.navigationview.R;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/29.
 */

public class CheckView extends View {

    private static final int ANIMATION_CHECK = 1;
    private static final int ANIMATION_NULL = 0;
    private static final int ANIMATION_UNCHECK = 2;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (animCurrentPage < animMaxPage && animCurrentPage >= 0){
                invalidate();
                if (animationState == ANIMATION_NULL) {
                    return;
                }
                if (animationState == ANIMATION_CHECK) {
                    animCurrentPage++;
                }else if (animationState == ANIMATION_UNCHECK){
                    animCurrentPage --;
                }
//                invalidate();
                this.sendEmptyMessageDelayed(0,animDuration / animMaxPage);
            }else {
                if (isCheck) {
                    animCurrentPage = animMaxPage-1;
                }else {
                    animCurrentPage = -1;
                }
                invalidate();
                animationState = ANIMATION_NULL;
            }
        }
    };
    private int animationState;
    private int animMaxPage = 13;
    private int animCurrentPage = -1;
    private int animDuration = 500;
    private boolean isCheck;
    private Bitmap okBitmap;

    public CheckView(Context context) {
        this(context,null);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(0xFFFF5317);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        //取得资源图片
        okBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkmark);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个圆
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,40,mPaint);


        int sideLength = okBitmap.getHeight();
        Rect src = new Rect(sideLength * animCurrentPage,0,sideLength * (animCurrentPage + 1),sideLength);
        Rect dst = new Rect(-30,-30,30,30);
        canvas.drawBitmap(okBitmap,src,dst,null);
    }
    public void check(){
//        if (animationState != ANIMATION_NULL || isCheck) {
//            return;
//        }
        handler.removeMessages(0);
        animationState = ANIMATION_CHECK;
        animCurrentPage = 0;
        handler.sendEmptyMessageDelayed(0,animDuration/animMaxPage);
        isCheck = true;
    }
    public void unCheck(){
        if (animationState != ANIMATION_NULL || (!isCheck)){
            return;
        }
        animationState = ANIMATION_UNCHECK;
        animCurrentPage = animMaxPage - 1;
        handler.sendEmptyMessageDelayed(0,animDuration / animMaxPage);
        isCheck = false;
    }
    public void setAnimationDuration(int animationDuration){
        if (animationDuration < 0){
            return;
        }
        this.animDuration = animationDuration;
    }

}
