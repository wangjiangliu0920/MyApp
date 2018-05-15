package com.icecold.navigationview.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by icecold_laptop_2 on 2017/12/8.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isUsePrepared;
    private boolean hasFetchData;

    @LayoutRes
    protected abstract int attachLayoutId();

    protected abstract void initView(View view);

    protected abstract void fetchData();

    protected abstract void initData();
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isUsePrepared = true;
        lazyFetchDataIfPrepared();
    }

    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isUsePrepared){
            hasFetchData = true;
            fetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUsePrepared = false;
        hasFetchData = false;
    }

    protected void initToolBar(Toolbar toolbar, String title, boolean showHomeAsUp){
        ((BaseActivity)getActivity()).initToolBar(toolbar,title,showHomeAsUp);
    }
}
