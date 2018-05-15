package com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 */

public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T model);
}
