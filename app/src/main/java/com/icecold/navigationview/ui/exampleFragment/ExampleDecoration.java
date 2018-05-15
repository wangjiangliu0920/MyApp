package com.icecold.navigationview.ui.exampleFragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.icecold.navigationview.R;

/**
 *
 * Created by icecold_laptop_2 on 2018/4/3.
 */

public class ExampleDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = ExampleDecoration.class.getSimpleName();
    private Paint paint;
    private int dividerHeight;

    public ExampleDecoration(Context context) {
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    /**
     *相当于设置内边距。类似padding
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams)view.getLayoutParams();
        int spanSize = layoutParams.getSpanSize();
        int spanIndex = layoutParams.getSpanIndex();
        outRect.top = 20;

        if (spanSize != ((GridLayoutManager)parent.getLayoutManager()).getSpanCount()) {
            if (spanIndex == 1){
                outRect.left = 10;
            }else {
                outRect.right = 10;
            }
        }
//        dividerHeight = 2;
//        outRect.bottom = dividerHeight;
    }

    /**
     *画背景图，在内容下面，通常搭配上面的方法使用
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //实现分割线
//        for (int i = 0; i < parent.getChildCount()-1; i++) {
//            View view = parent.getChildAt(i);
//            int left = view.getLeft() + view.getPaddingLeft();
//            int right = view.getRight() - view.getPaddingRight();
//            int top = view.getBottom();
//            int bottom = view.getBottom() + dividerHeight;
//            c.drawRect(left,top,right,bottom,paint);
//        }
    }

    /**
     *在内容上面画东西
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
