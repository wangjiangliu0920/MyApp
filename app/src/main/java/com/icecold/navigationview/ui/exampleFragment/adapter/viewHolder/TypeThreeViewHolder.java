package com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.DataModleThree;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 */

public class TypeThreeViewHolder extends TypeAbstractViewHolder<DataModleThree> {
    public ImageView avatar;
    public TextView name;
    public TextView content;
    public ImageView contentImage;

    public TypeThreeViewHolder(View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avater);
        name = itemView.findViewById(R.id.name);
        content = itemView.findViewById(R.id.content);
        contentImage = itemView.findViewById(R.id.contentImage);
    }

    @Override
    public void bindHolder(DataModleThree model) {
        avatar.setBackgroundResource(model.getAvatarColor());
        name.setText(model.getName());
        content.setText(model.getContent());
        contentImage.setBackgroundResource(model.getContentColor());
    }
}
