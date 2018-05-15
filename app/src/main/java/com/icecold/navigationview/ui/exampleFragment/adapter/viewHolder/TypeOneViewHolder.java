package com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.DataModleOne;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 */

public class TypeOneViewHolder extends TypeAbstractViewHolder<DataModleOne> {
    public ImageView avatar;
    public TextView name;

    public TypeOneViewHolder(View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avater);
        name = itemView.findViewById(R.id.name);
    }

    @Override
    public void bindHolder(DataModleOne model) {
        avatar.setBackgroundResource(model.getAvatarColor());
        name.setText(model.getName());
    }
}
