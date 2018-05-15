package com.icecold.navigationview.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.icecold.navigationview.R;

/**
 * Created by icecold_laptop_2 on 2017/12/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @LayoutRes
    protected abstract int getLayoutId();
    @MenuRes
    protected abstract int getMenuId();

    protected abstract void loadData();

    protected abstract void initViews(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initTheme();
        setContentView(getLayoutId());
        initViews(savedInstanceState);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuId() != 0){
            getMenuInflater().inflate(getMenuId(),menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initTheme() {
        int themeIndex = 0;
        switch (themeIndex) {
            case 0:
                setTheme(R.style.LapisBlueTheme);
                break;
            case 1:

                break;
            default:
                break;
        }
    }
    protected void initToolBar( Toolbar toolbar,String title,boolean showHomeAsUp) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }
}
