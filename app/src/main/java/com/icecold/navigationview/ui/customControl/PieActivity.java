package com.icecold.navigationview.ui.customControl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.icecold.navigationview.R;
import com.icecold.navigationview.dataModle.PieDataEntity;
import com.icecold.navigationview.ui.base.BaseActivity;
import com.icecold.navigationview.widget.CheckView;
import com.icecold.navigationview.widget.PieChart;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/22.
 */

public class PieActivity extends BaseActivity implements View.OnClickListener {

    private PieChart pieChart;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private Button checkButton;
    private CheckView checkView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void loadData() {
        List<PieDataEntity> dataEntities = new ArrayList<>();
        for (int i = 0;i<9;i++){

            PieDataEntity entity = new PieDataEntity("name"+i,i+1,mColors[i]);
            dataEntities.add(entity);
        }
        pieChart.setOnItemPieClickListener(new PieChart.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(PieActivity.this, String.format("点击的位置 = %d", position),Toast.LENGTH_SHORT).show();
            }
        });
        pieChart.setDataList(dataEntities);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        pieChart = findViewById(R.id.pieChart);
        checkButton = findViewById(R.id.checkBt);
        checkView = findViewById(R.id.checkView);
        checkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        checkView.setAnimationDuration(13000);
        checkView.check();
    }
}
