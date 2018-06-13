package com.icecold.navigationview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.Leaf;
import com.icecold.navigationview.dataModle.StartType;
import com.icecold.navigationview.util.UiUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * Created by icecold_laptop_2 on 2018/6/12.
 */

public class LeafLoadingView extends View {

    private static final int TOTAL_PREGRESS = 100;
    //一个树叶飘完的整个周期时长
    private static final int LEAF_FINISH_TIME = 2000;
    private static final int MIDDLE_AMPLITUDE = 13;
    private static final int AMPLITUDE_DISPARITY = 5;
    private static final int LEAF_ROTATE_TIME = 2000;
    private Context context;
    private int mRightMargin;
    //表示进度条与view的左/上/下的间隔
    private int mLeftMargin;
    private static final int LEFT_MARGIN = 9;
    private static final int RIGHT_MARGIN = 25;
    private Bitmap mLeafBitmap;
    private int WHITE_COLOR = 0xfffde399;
    private int ORANGE_COLOR = 0xffffa800;
    private Paint mBitmapPaint;
    private Paint mWhitePaint;
    private Paint mOrangePaint;
    private int mTotalWidth;
    private int mTotalHight;
    private int mProgressWidth;
    private int mArcRadius;
    private int mTagerBitmapWidth;
    private int mTagerBitmapHeight;
    private Rect mTagerSrcRect;
    private Rect mTagerDisRect;
    private int mCurrentProgressPosition;
    private int mRealLeftLoction;
    private RectF mArcRectf;
    private RectF mOrangRectf;
    private RectF mWhiteRectf;
    private int mProgress;
    private Bitmap mTagerBitmap;
    //一个树叶飘完的整个周期时长
    private int mLeafFinishTime;
    private int mAddTime;
    private List<Leaf> mLeafList;
    //振幅大小
    private float mMiddleAmplitude = MIDDLE_AMPLITUDE;
    //振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;
    //记录叶子旋转的时间
    private int mLeafRotateTime;
    private int mLeafWidth;
    private int mLeafHight;

    public LeafLoadingView(Context context) {
//        super(context);
        this(context,null);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //初始化进度条的margin
        mLeftMargin = UiUtils.dipToPx(context,LEFT_MARGIN);
        mRightMargin = UiUtils.dipToPx(context,RIGHT_MARGIN);

        initBitmap();
        initPaint();
        //初始化叶子的数据
        LeafFactory leafFactory = new LeafFactory();
        mLeafList = leafFactory.generateLeafs(LeafFactory.MAX_LEAFS);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHight = h;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHight - 2 * mLeftMargin) / 2;
        //图片显示需要的矩阵
        mTagerSrcRect = new Rect(0, 0, mTagerBitmapWidth, mTagerBitmapHeight);
        mTagerDisRect = new Rect(0, 0, mTotalWidth, mTotalHight);
        //初始化画进度需要用到的矩阵信息
        mWhiteRectf = new RectF(mLeftMargin + mCurrentProgressPosition, mLeftMargin, mTotalWidth - mRightMargin, mTotalHight - mLeftMargin);
        mOrangRectf = new RectF(mLeftMargin + mArcRadius, mLeftMargin, mCurrentProgressPosition, mTotalHight - mLeftMargin);
        mArcRectf = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + 2 * mArcRadius, mTotalHight - mLeftMargin);
        mRealLeftLoction = mLeftMargin + mArcRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgessAndLeafs(canvas);
        //把图片画到整个自定义view上
        canvas.drawBitmap(mTagerBitmap,mTagerSrcRect,mTagerDisRect,mBitmapPaint);
        postInvalidate();
    }

    private void drawProgessAndLeafs(Canvas canvas) {
        if (mProgress > TOTAL_PREGRESS) {
            mProgress = (mProgress - TOTAL_PREGRESS) % TOTAL_PREGRESS;
        }
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PREGRESS;
        if (mCurrentProgressPosition < mArcRadius){
            //1.绘制白色的ARC
            canvas.drawArc(mArcRectf,90,180,false,mWhitePaint);
            //2.绘制白色的矩形
            mWhiteRectf.left = mRealLeftLoction;
            canvas.drawRect(mWhiteRectf,mWhitePaint);
            //绘制叶子
            drawLeafs(canvas);
            //3.绘制橘色的ARC
            double acos = Math.acos((mArcRadius - mCurrentProgressPosition) / (float) mArcRadius);
            //走过一半的角度
            int degrees = (int) Math.toDegrees(acos);
            canvas.drawArc(mArcRectf,180-degrees,2 * degrees,false,mOrangePaint);
        }else {
            //1.绘制白色的矩形
            mWhiteRectf.left = mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectf,mWhitePaint);
            //绘制叶子
            drawLeafs(canvas);
            //2.绘制橘色的ARC
            canvas.drawArc(mArcRectf,90,180,false,mOrangePaint);
            //3.绘制橘色的矩形
            mOrangRectf.left = mRealLeftLoction;
            mOrangRectf.right = mCurrentProgressPosition;
            canvas.drawRect(mOrangRectf,mOrangePaint);
        }
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);
    }

    private void initBitmap() {
        mLeafBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaf);
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHight = mLeafBitmap.getHeight();
        mTagerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaf_kuang);
        mTagerBitmapWidth = mTagerBitmap.getWidth();
        mTagerBitmapHeight = mTagerBitmap.getHeight();
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
        postInvalidate();
    }
    private void drawLeafs(Canvas canvas){
        mLeafRotateTime = mLeafRotateTime <= 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < mLeafList.size(); i++) {
            Leaf leaf = mLeafList.get(i);
            if (currentTime > leaf.getStartTime() && leaf.getStartTime() != 0){
                //绘制叶子之前需要得到它的坐标(x,y)
                getLeafLocation(leaf,currentTime);
                //开始叶子旋转的动画，利用快照的形式
                canvas.save();
                //通过控制Matrix来实现旋转
                Matrix matrix = new Matrix();
                float transX = mLeftMargin + leaf.getX();
                float transY = mLeftMargin + leaf.getY();
                matrix.postTranslate(transX,transY);
                //通过时间来确认旋转的角度
                float rotateFraction = ((currentTime - leaf.getStartTime()) % mLeafRotateTime) / (float)mLeafRotateTime;
                int angle = (int) (rotateFraction * 360);
                //再根据叶子的方向来确定角度
                int rotate = leaf.getRotateDirection() == 0 ? angle + leaf.getRotateAngle() : -angle + leaf.getRotateAngle();
                matrix.postRotate(rotate,transX + mLeafWidth / 2,transY + mLeafHight / 2);
                canvas.drawBitmap(mLeafBitmap,matrix,mBitmapPaint);
                //回放
                canvas.restore();
            }else {
                continue;
            }
        }
    }
    private class LeafFactory{
        static final int MAX_LEAFS = 8;
        Random random = new Random();

        public List<Leaf> generateLeafs(int leafSize){
            List<Leaf> leafList = new LinkedList<>();
            for (int i = 0; i < leafSize; i++) {
                leafList.add(generateLeaf());
            }
            return leafList;
        }

        private Leaf generateLeaf() {
            Leaf leaf = new Leaf();
            int randomType = random.nextInt(3);
            StartType type = StartType.MIDDLE;
            switch (randomType) {
                case 0:

                    break;
                case 1:
                    type = StartType.LITTLE;
                    break;
                case 2:
                    type = StartType.BIG;
                    break;
            }
            leaf.setType(type);
            leaf.setRotateAngle(random.nextInt(360));
            leaf.setRotateDirection(random.nextInt(2));
            //为了产生视觉上的错觉，让开始的时间有一定的随机性
            mLeafFinishTime = mLeafFinishTime <= 0 ? LEAF_FINISH_TIME:mLeafFinishTime;
            mAddTime += random.nextInt((mLeafFinishTime * 2));
            leaf.setStartTime(System.currentTimeMillis() + mAddTime);
            return leaf;
        }
    }
    private void getLeafLocation(Leaf leaf,long currentTime){
        long interval = currentTime - leaf.getStartTime();
        if (interval < 0){
            return;
        }else if (interval >= mLeafFinishTime){
            //重置初始化时间
            leaf.setStartTime(System.currentTimeMillis()+new Random().nextInt(mLeafFinishTime));
        }
        float fraction = (float) interval / mLeafFinishTime;
        leaf.setX((int)(mProgressWidth - fraction * mProgressWidth));
        leaf.setY(getLocationY(leaf));
    }

    private int getLocationY(Leaf leaf) {
        //y = A(wx+Q)+h
        float w = (float) ((float) 2 * Math.PI / mProgressWidth);
        float a = mMiddleAmplitude;
        switch (leaf.getType()) {
            case LITTLE:
                a = mMiddleAmplitude - mAmplitudeDisparity;
                break;
            case MIDDLE:
                a = mMiddleAmplitude;
                break;
            case BIG:
                a = mMiddleAmplitude + mAmplitudeDisparity;
                break;
            default:
                break;
        }
        return (int) (a * Math.sin(w * leaf.getX()) + mArcRadius * 2 / 3);
    }
}
