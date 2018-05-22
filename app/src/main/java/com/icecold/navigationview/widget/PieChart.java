package com.icecold.navigationview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.icecold.navigationview.dataModle.PieDataEntity;
import com.icecold.navigationview.util.CalculateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 实现一个自定义的饼图
 * Created by icecold_laptop_2 on 2018/5/22.
 */

public class PieChart extends View {
    /**
     * 饼图绘制的区域
     */
    private RectF mRectF;
    /**
     * 饼图点击以后绘制的区域
     */
    private RectF mTouchRectF;
    /**
     * 绘制饼图的画笔
     */
    private Paint mPaint;
    /**
     * 绘制饼图上面的线
     */
    private Paint mLinePaint;
    /**
     * 绘制饼图线后面的文字
     */
    private Paint mTextPaint;
    private int mTotleWidth;
    private int mTotleHeight;
    private List<PieDataEntity> mDataList;
    private float mTotleBlock;
    /**
     * 当前需要画圆的半径
     */
    private float mRadius;
    /**
     * 记录下所有的起始的角度值
     */
    private float[] angles;
    private OnItemPieClickListener mOnItemPieClickListener;
    /**
     * 记录点击的位置是属于哪个位置的扇形,初始值为负数是为了作为一个标识使用
     */
    private int position = -1;

    public PieChart(Context context) {
        super(context);
        init(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //得到view真实的宽度和高度
        mTotleWidth = w - getPaddingLeft() - getPaddingRight();
        mTotleHeight = h - getPaddingTop() - getPaddingBottom();

        mRadius = (float) (Math.min(mTotleWidth, mTotleHeight) / 2 * 0.6);

        mRectF.left = -mRadius;
        mRectF.top = -mRadius;
        mRectF.right = mRadius;
        mRectF.bottom = mRadius;

        mTouchRectF.left = -mRadius - 16;
        mTouchRectF.top = -mRadius - 16;
        mTouchRectF.right = mRadius + 16;
        mTouchRectF.bottom = mRadius + 16;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mTotleWidth / 2,mTotleHeight / 2);
        drawEachArea(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX() - (mTotleWidth / 2);
                float y = event.getY() - (mTotleHeight / 2);
                float touchAngle = 0;//触摸的地方对应的角度
                if (x<0 && y<0){ // 4象限
                    touchAngle += 180;
                }else if (x > 0 && y<0){ // 1象限
                    touchAngle += 360;
                }else if (x<0 && y>0){ // 3象限
                    touchAngle += 180;
                }
                touchAngle += Math.toDegrees(Math.atan(y/x));
                if (touchAngle < 0){
                    touchAngle += 360;
                }
                float touchRadius = (float) Math.sqrt(x * x + y * y);
                if (touchRadius < mRadius){
                    //得到点击的地方对应的是哪个位置的扇形
                    position = -Arrays.binarySearch(angles,touchAngle) - 1;
                    if (mOnItemPieClickListener != null){
                        invalidate();
                        mOnItemPieClickListener.onClick(position-1);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    public interface OnItemPieClickListener{
        void onClick(int position);
    }
    public void setOnItemPieClickListener(OnItemPieClickListener onItemPieClickListener){
        mOnItemPieClickListener = onItemPieClickListener;
    }

    private void drawEachArea(Canvas canvas) {

        if (mDataList == null || mDataList.size() == 0){
            return;
        }
        float startAngle = 0;

        for (int i = 0; i < mDataList.size(); i++) {

            float sweepAngle = mDataList.get(i).getValue() / mTotleBlock * 360 - 1;
            mPaint.setColor(mDataList.get(i).getColor());
            if (position-1 == i){
                canvas.drawArc(mTouchRectF,startAngle,sweepAngle,true,mPaint);
                //更改圆的半径
                float mNewRadius = mRadius + 16;
                //计算出画线的坐标
                double toRadians = Math.toRadians(startAngle + sweepAngle / 2);
                float pxStart = (float) (mNewRadius * Math.cos(toRadians));
                float pyStart = (float) (mNewRadius * Math.sin(toRadians));
                float pxEnd = (float) ((mNewRadius + 30) * Math.cos(toRadians));
                float pyEnd = (float) ((mNewRadius + 30) * Math.sin(toRadians));
                //开始划线
                canvas.drawLine(pxStart,pyStart,pxEnd,pyEnd,mLinePaint);
                float value = mDataList.get(i).getValue() / mTotleBlock * 100;
                double percentValue = CalculateUtil.round(value, 2);
                if (startAngle % 360.0 >= 90.0 && startAngle % 360.0 <= 270.0){
                    //判断是否在2,3象限中
                    canvas.drawLine(pxEnd,pyEnd,pxEnd-30,pyEnd,mLinePaint);
                    canvas.drawText(percentValue+"%",pxEnd-30-mTextPaint.measureText(percentValue+"%"),pyEnd,mTextPaint);
                }else {
                    canvas.drawLine(pxEnd,pyEnd,pxEnd + 30,pyEnd,mLinePaint);
                    canvas.drawText(percentValue+"%",pxEnd + 30,pyEnd,mTextPaint);
                }
            }else {
                //开始画圆弧
                canvas.drawArc(mRectF,startAngle,sweepAngle,true,mPaint);
                //计算出画线的坐标
                double toRadians = Math.toRadians(startAngle + sweepAngle / 2);
                float pxStart = (float) (mRadius * Math.cos(toRadians));
                float pyStart = (float) (mRadius * Math.sin(toRadians));
                float pxEnd = (float) ((mRadius + 30) * Math.cos(toRadians));
                float pyEnd = (float) ((mRadius + 30) * Math.sin(toRadians));
                //开始划线
                canvas.drawLine(pxStart,pyStart,pxEnd,pyEnd,mLinePaint);
                float value = mDataList.get(i).getValue() / mTotleBlock * 100;
                double percentValue = CalculateUtil.round(value, 2);
                if (startAngle % 360.0 >= 90.0 && startAngle % 360.0 <= 270.0){
                    //判断是否在2,3象限中
                    canvas.drawLine(pxEnd,pyEnd,pxEnd-30,pyEnd,mLinePaint);
                    canvas.drawText(percentValue+"%",pxEnd-30-mTextPaint.measureText(percentValue+"%"),pyEnd,mTextPaint);
                }else {
                    canvas.drawLine(pxEnd,pyEnd,pxEnd + 30,pyEnd,mLinePaint);
                    canvas.drawText(percentValue+"%",pxEnd + 30,pyEnd,mTextPaint);
                }
            }

            angles[i] = startAngle;
            startAngle += sweepAngle + 1;
        }
    }

    private void init(Context context){
        mRectF = new RectF();
        mTouchRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(24);
    }
    public void setDataList(List<PieDataEntity> pieData){

        this.mDataList = pieData;
        mTotleBlock = 0;
        for (PieDataEntity pieDataEntity : mDataList) {
            mTotleBlock += pieDataEntity.getValue();
        }
        angles = new float[mDataList.size()];
        //开始画饼图
        invalidate();
    }

}
