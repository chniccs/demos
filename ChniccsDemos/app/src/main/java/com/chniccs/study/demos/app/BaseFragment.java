package com.chniccs.study.demos.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/15.
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, mView);
        initView();
        initData();

        return mView;
    }

    public abstract int getLayout();

    public abstract void initView();

    protected  void initData() {

    }


}
