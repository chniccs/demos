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
import com.chniccs.study.demos.app.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chniccs on 16/8/4.
 * 几个月后再次读了大神 扔物线 的http://gank.io/post/560e15be2dca930e00da1083这篇文章后记录下简单的使用案例
 */
public class RxJavaFragment extends Fragment {
    String tag = "rxjava";

    @BindView(R.id.rxjava_tv)
    TextView mTv;
    @BindView(R.id.rxjava_iv_drawable)
    ImageView mIvDrawable;

    private String[] strs = {"1", "2", "3"};
    private Subscription mSubscribe;

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
                        //这里可以执行一些在准备工作，且是在主线程中执行的。如弹出进度条
                    }
                })
//                .compose(RxSchedulersHelper.<Drawable>io_main())//这一步也同样实现了下两步的操作，实现线程调度
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

    @OnClick(R.id.rxjava_btn_map)
    public void doMap(View v) {
        Observable.from(strs)
                .map(new Func1<String, Integer>() {//map，用于转换对象，可以处理类似取出全部人的名字这个属性这样功能，可以做循环
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(tag, integer + 1 + "");
                    }
                });
    }

    @OnClick(R.id.rxjava_btn_flatmap)
    public void doFlatMap(View v) {//flatmap的用处很厉害，主要是他返回的并不是处理后的对象或数据，而在返回一个Observable对象，你可以在这个Observable里处理你想干的事
        mSubscribe = Observable.from(strs)
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return add(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(tag, integer + "");
                    }
                });
    }

    public Observable<Integer> add(String str) {//这个用来演示的，只是用来处理string数据，其实可以拓展，你可以用这个Observable来做网络连接，或者文件加载等等
        return Observable.just(str)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s) * 10;
                    }
                });

    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG,mSubscribe.isUnsubscribed()+"");//这里主要是检测在执行完之后，mSubscribe是否自动取消了订阅，测试是会自动取消的
        super.onDestroy();
    }
}
