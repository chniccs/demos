package com.chniccs.study.demos.dynamic_proxy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chniccs.study.demos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chniccs on 16/8/9.
 * 动态代理的演示类，这里主要是引用了http://www.iteye.com/topic/683613/的例子
 */
public class DynamicProxyFragment extends Fragment {
    @BindView(R.id.dynamic_proxy_tv)
    TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_proxy, container, false);
        ButterKnife.bind(this, view);
        mTv.setText(R.string.dynamic_proxy);

        return view;
    }

    @OnClick(R.id.dynamic_proxy_btn_dynamic_proxy)
    public void dynamicProxy(View v) {
        DynamicProxy.dynamicProxy();
        Toast.makeText(getActivity(), "结果请查看日志", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.dynamic_proxy_btn_non_proxy)
    public void nonProxy(View v) {
        DynamicProxy.nonProxy();
    }
}
