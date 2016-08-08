package com.chniccs.study.demos.mvp;

import android.util.Log;
import android.view.View;

import com.chniccs.study.demos.app.Constants;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by chniccs on 16/8/8.
 * 结合rxjava来实现Presenter
 */
public class Presenter implements IPresenter {
    private IView mIView;
    private final IModel mModel;

    public Presenter(IView iView) {
        this.mIView = iView;
        Log.d(Constants.TAG, iView.toString());
        mModel = new Model();
    }

    @Override
    public void onCreate() {
        Log.d(Constants.TAG, "onCreate");
    }

    @Override
    public void click(View v) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String data = mModel.getData();
                subscriber.onNext(data);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mIView.setData(s);
                    }
                });


    }
}
