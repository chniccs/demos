package com.chniccs.study.demos.mvp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chniccs.study.demos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chniccs on 16/8/8.
 */
public class MvpFragment extends Fragment implements IView {

    private IPresenter mPresenter;
    @BindView(R.id.mvp_tv)
    TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mvp, container, false);
        ButterKnife.bind(this, view);
        //传递接口过去
        mPresenter = new Presenter(this);
        mPresenter.onCreate();

        return view;
    }

    @Override
    public void setData(String v) {

        mTv.setText(v);
    }

    @OnClick(R.id.mvp_btn)
    public void btnClick(View v) {
        mPresenter.click(v);
    }
}
