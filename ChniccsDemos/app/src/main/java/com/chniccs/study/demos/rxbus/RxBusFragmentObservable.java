package com.chniccs.study.demos.rxbus;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chniccs.study.demos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chniccs on 16/8/5.
 */
public class RxBusFragmentObservable extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxbus_observable, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    int i=0;
    @OnClick(R.id.rxbus_observable_btn)
    public void postEvent(View v){
        i++;
        RxBus.getDefault().post(new EventBean("chniccs"+i+"号"));
    }
}
