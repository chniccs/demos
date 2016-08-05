package com.chniccs.study.demos.retrofit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chniccs on 16/8/5.
 */
public class RetrofitFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick(R.id.retrofit_btn_ip)
    public void getIp(View v) {//这是简单的动态参数，固定地址的请求方式
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        IIPInterface iipInterface = retrofit.create(IIPInterface.class);
        Call<IPBean> ipBeanCall = iipInterface.getIP("63.223.108.42");
        ipBeanCall.enqueue(new Callback<IPBean>() {
            @Override
            public void onResponse(Call<IPBean> call, Response<IPBean> response) {
                Log.d(Constants.TAG, response.body().data.country);
            }

            @Override
            public void onFailure(Call<IPBean> call, Throwable t) {
            }
        });
    }

    @OnClick(R.id.retrofit_btn_ip_by_rxjava)
    public void getIpByRxjava(View v) {//rxjava与retrofit的结合使用

        Observer<IPBean> observer = new Observer<IPBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IPBean ipBean) {
                Log.d(Constants.TAG, ipBean.data.country);
            }
        };
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        IIPInterface iipInterface = retrofit.create(IIPInterface.class);
        iipInterface.getIPByRxjava("63.223.108.42")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
