package com.icecold.navigationview.ui.exampleFragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.icecold.navigationview.R;
import com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder.TypeOneViewHolder;
import com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder.TypeThreeViewHolder;
import com.icecold.navigationview.ui.exampleFragment.adapter.viewHolder.TypeTwoViewHolder;
import com.icecold.navigationview.dataModle.DataModleOne;
import com.icecold.navigationview.dataModle.DataModleThree;
import com.icecold.navigationview.dataModle.DataModleTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 */

public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    private ArrayList<DataModleOne> list1;
    private ArrayList<DataModleTwo> list2;
    private ArrayList<DataModleThree> list3;
    private ArrayList<Integer> positionOnTypes = new ArrayList<>();
    private Map<Integer,Integer> mPosition = new HashMap<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ONE:
                return new TypeOneViewHolder(layoutInflater.inflate(R.layout.one_item,parent,false));
            case TYPE_TWO:
                return new TypeTwoViewHolder(layoutInflater.inflate(R.layout.two_item,parent,false));
            case TYPE_THREE:
                return new TypeThreeViewHolder(layoutInflater.inflate(R.layout.three_item,parent,false));
        }
        return null;
    }

    public DemoAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int realPosition = position - mPosition.get(viewType);
        switch (viewType){
            case TYPE_ONE:
                ((TypeOneViewHolder)holder).bindHolder(list1.get(realPosition));
                break;
            case TYPE_TWO:
                ((TypeTwoViewHolder)holder).bindHolder(list2.get(realPosition));
                break;
            case TYPE_THREE:
                ((TypeThreeViewHolder)holder).bindHolder(list3.get(realPosition));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return positionOnTypes == null? 0 : positionOnTypes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return positionOnTypes.get(position);
    }
    public void addList(ArrayList<DataModleOne> list1,ArrayList<DataModleTwo> list2,ArrayList<DataModleThree> list3){
        addListByType(TYPE_ONE,list1);
        addListByType(TYPE_TWO,list2);
        addListByType(TYPE_THREE,list3);

        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    private void addListByType(int type, ArrayList list) {
        mPosition.put(type,positionOnTypes.size());
        for (int i = 0; i < list.size(); i++) {
            positionOnTypes.add(type);
        }
    }
}
