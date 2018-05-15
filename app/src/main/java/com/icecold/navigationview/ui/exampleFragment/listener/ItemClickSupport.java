package com.icecold.navigationview.ui.exampleFragment.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.icecold.navigationview.R;

/**
 *利用OnChildAttachStateChangeListener实现对每个itemView的点击事件，非常的解耦
 * Created by icecold_laptop_2 on 2018/4/9.
 */

public class ItemClickSupport {
    private RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongListener mOnItemLongListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(mRecyclerView,mRecyclerView.getChildAdapterPosition(v),v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongListener != null) {
                mOnItemLongListener.onLongItemClicked(mRecyclerView,mRecyclerView.getChildAdapterPosition(v),v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };
    private ItemClickSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support,this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }
    public static ItemClickSupport addTo(RecyclerView recyclerView){
        ItemClickSupport support = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(recyclerView);
        }
        return support;
    }
    public static ItemClickSupport removeFrom(RecyclerView recyclerView){
        ItemClickSupport support = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (support != null){
            support.detach(recyclerView);
        }
        return support;
    }
    private void detach(RecyclerView view){
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support,null);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongListener(OnItemLongListener longListener){
        mOnItemLongListener = longListener;
    }

    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView,int position,View view);
    }
    public interface OnItemLongListener{
        void onLongItemClicked(RecyclerView recyclerView,int position,View view);
    }
}
