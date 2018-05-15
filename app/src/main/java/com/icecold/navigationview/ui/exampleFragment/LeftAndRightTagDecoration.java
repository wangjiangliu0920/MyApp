package com.icecold.navigationview.ui.exampleFragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.icecold.navigationview.R;

/**
 *
 * Created by icecold_laptop_2 on 2018/4/3.
 */

public class LeftAndRightTagDecoration extends RecyclerView.ItemDecoration {

    private Paint leftPaint;
    private Paint rightPaint;
    private int tagWidth;

    LeftAndRightTagDecoration(Context context) {
        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        tagWidth = 20;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //实现item上类似于标签的功能
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(childView);
            boolean isLeft = childAdapterPosition % 2 == 0;
            if (isLeft) {
                int left = childView.getLeft();
                int top = childView.getTop();
                int right = left + tagWidth;
                int bottom = childView.getBottom();
                c.drawRect(left,top,right,bottom,leftPaint);
            }else {
                int right = childView.getRight();
                int left = right - tagWidth;
                int top = childView.getTop();
                int bottom = childView.getBottom();
                c.drawRect(left,top,right,bottom,rightPaint);
            }
        }
    }
}
