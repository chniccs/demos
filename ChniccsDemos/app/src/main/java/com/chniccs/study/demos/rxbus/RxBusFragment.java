package com.chniccs.study.demos.rxbus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by chniccs on 16/8/5.
 * rxbus的使用案例
 */
public class RxBusFragment extends Fragment {
    protected Subscription subscription;
    @BindView(R.id.rxbus_tv)
    TextView mTvName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rxbus, container, false);
        ButterKnife.bind(this, view);
        register();
        return view;
    }

    int i = 0;

    private void register() {
        subscription = RxBus.getDefault().toObservable(EventBean.class)
                .subscribe(new Action1<EventBean>() {
                    @Override
                    public void call(EventBean eventBean) {
                        i++;
                        if (i >= 5) {//满足条件就取消订阅
                            subscription.unsubscribe();
                        }
                        mTvName.setText(eventBean.name);
                    }
                });
    }

    @OnClick(R.id.rxbus_btn_show_fragment)
    public void showFragment(View v) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fg = fm.beginTransaction();
        fg.replace(R.id.rxbus_container, new RxBusFragmentObservable());
        fg.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    /**
     * 解除订阅，通常情况下是执行过事件就会的自动取消订阅的
     */
    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
