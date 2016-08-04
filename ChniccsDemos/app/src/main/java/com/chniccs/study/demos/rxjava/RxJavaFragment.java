package com.chniccs.study.demos.rxjava;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chniccs.study.demos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by chniccs on 16/8/4.
 */
public class RxJavaFragment extends Fragment {
    String tag = "rxjava";

    @BindView(R.id.rxjava_tv)
    TextView mTv;
    @BindView(R.id.rxjava_iv_drawable)
    ImageView mIvDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.rxjava_btn_go)
    public void go(View view) {

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
                mTv.setText(s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };
        observable.subscribe(subscriber);

    }

    @OnClick(R.id.rxjava_btn_get_drawable)
    public void getDrawable(View v) {
        final int drawable = R.drawable.dayu;
        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable1 = getActivity().getTheme().getDrawable(drawable);
                subscriber.onNext(drawable1);
            }
        })
                .subscribeOn(Schedulers.io())//多次调用subscribeOn时，只有第一次调用时才有效果
                .doOnSubscribe(new Action0() {//可以作一些准备工作，并且它的线程是与它最近的subscribeOn所指定的线程有关的，这里最近的是AndroidSchedulers.mainThread()，所以可以操作UI线程
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mIvDrawable.setImageDrawable(drawable);
                    }
                });

    }


}
