package com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.DataModleTwo;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 */

public class TypeTwoViewHolder extends TypeAbstractViewHolder<DataModleTwo> {
    public ImageView avatar;
    public TextView name;
    public TextView content;

    public TypeTwoViewHolder(View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avater);
        name = itemView.findViewById(R.id.name);
        content = itemView.findViewById(R.id.content);
    }

    @Override
    public void bindHolder(DataModleTwo model) {
        avatar.setBackgroundResource(model.getAvatarColor());
        name.setText(model.getName());
        content.setText(model.getContent());
    }
}
