package com.icecold.navigationview.ui.exampleFragment.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 *通过重写GestureDetectorCompat实现item监听
 * Created by icecold_laptop_2 on 2018/4/9.
 */

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private final GestureDetectorCompat gestureDetectorCompat;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && clickListener != null && gestureDetectorCompat.onTouchEvent(e)) {
            clickListener.onItemClick(childView,rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    public RecyclerItemClickListener(final RecyclerView recyclerView, final OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            clickListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                        }
                    }
                });
    }

    public interface OnItemClickListener{
        /**
         * 点击时候的回调
         * @param view 点击的view
         * @param position 点击的位置
         */
        void onItemClick(View view,int position);

        /**
         *长按时候的回调
         * @param view 点击的view
         * @param position 点击的位置
         */
        void onItemLongClick(View view,int position);
    }
}
