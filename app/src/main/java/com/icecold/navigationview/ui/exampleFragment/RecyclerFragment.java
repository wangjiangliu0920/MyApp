package com.icecold.navigationview.ui.exampleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.DataModleOne;
import com.icecold.navigationview.dataModle.DataModleThree;
import com.icecold.navigationview.dataModle.DataModleTwo;
import com.icecold.navigationview.ui.RxJavaExample;
import com.icecold.navigationview.ui.banner.BannerActivity;
import com.icecold.navigationview.ui.base.BaseFragment;
import com.icecold.navigationview.ui.customControl.PieActivity;
import com.icecold.navigationview.ui.exampleFragment.adapter.DemoAdapter;
import com.icecold.navigationview.ui.exampleFragment.listener.ItemClickSupport;

import java.util.ArrayList;

/**
 * Created by icecold_laptop_2 on 2018/3/26.
 */

public class RecyclerFragment extends BaseFragment {

    private RecyclerView recyclerView;
    int colors[] = {android.R.color.holo_red_dark,
            android.R.color.holo_blue_dark
            ,android.R.color.holo_orange_dark};
    public static final String TAG = RecyclerFragment.class.getSimpleName();
    private DemoAdapter mAdapter;

    public static RecyclerFragment newInstance(String flag){
        Bundle bundle = new Bundle();
        bundle.putString(TAG,flag);
        RecyclerFragment recyclerFragment = new RecyclerFragment();
        recyclerFragment.setArguments(bundle);

        return recyclerFragment;

    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_recycler;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recyclerView.getAdapter().getItemViewType(position);
                if (type == DemoAdapter.TYPE_THREE) {
                    return gridLayoutManager.getSpanCount();
                }else {
                    return 1;
                }
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(gridLayoutManager);
        ExampleDecoration decoration2 = new ExampleDecoration(getContext());
        //给item加入标签
//        LeftAndRightTagDecoration decoration = new LeftAndRightTagDecoration(getContext());
//        recyclerView.addItemDecoration(decoration);
        //实现每个view的点击事件
//        RecyclerItemClickListener itemClickListener = new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                showToastMessage("按下第"+position+"的位置");
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                showToastMessage("长按下第"+position+"的位置");
//            }
//        });
//        recyclerView.addOnItemTouchListener(itemClickListener);
        //最优的方式实现每个view的点击事件
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                showToastMessage("按下第"+position+"的位置");
                switch (position){
                    case 0:
                        Intent intent0 = new Intent(getActivity(), PieActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), RxJavaExample.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), BannerActivity.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemLongListener(new ItemClickSupport.OnItemLongListener() {
            @Override
            public void onLongItemClicked(RecyclerView recyclerView, int position, View view) {
                showToastMessage("长按下第"+position+"的位置");
            }
        });
        recyclerView.addItemDecoration(decoration2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void fetchData() {
        //通知刷新数据
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
//        ArrayList<DataModel> modelArrayList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            int type = (int) ((Math.random() * 3)+1);
//            DataModel dataModel = new DataModel();
//            dataModel.setType(type);
//            dataModel.setName("name : "+i);
//            dataModel.setContent("content : "+i);
//            dataModel.setAvatarColor(colors[type-1]);
//            dataModel.setContentColor(colors[(type+1)%3]);
//            modelArrayList.add(dataModel);
//        }
//        adapter = new DemoAdapter(getContext());
//        adapter.addList(modelArrayList);
        ArrayList<DataModleOne> list1 = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            DataModleOne data = new DataModleOne();
            data.setAvatarColor(colors[0]);
            data.setName("name : "+i);
            list1.add(data);
        }

        ArrayList<DataModleTwo> list2 = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            DataModleTwo data = new DataModleTwo();
            data.setAvatarColor(colors[1]);
            data.setName("name : "+i);
            data.setContent("content : "+i);
            list2.add(data);
        }

        ArrayList<DataModleThree> list3 = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            DataModleThree data = new DataModleThree();
            data.setAvatarColor(colors[2]);
            data.setName("name : "+i);
            data.setContent("content : "+i);
            data.setContentColor(colors[1]);
            list3.add(data);
        }
        mAdapter = new DemoAdapter(getContext());
        mAdapter.addList(list1,list2,list3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (recyclerView != null) {
            ItemClickSupport.removeFrom(recyclerView);
        }
    }
}
