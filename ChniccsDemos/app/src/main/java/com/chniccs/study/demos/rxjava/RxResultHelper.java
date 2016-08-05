package com.chniccs.study.demos.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by chniccs on 16/8/5.
 * Rx处理服务器返回
 */

public class RxResultHelper {

    public static <T> Observable.Transformer<RESTResult<T>, T> handleResult() {
        return new Observable.Transformer<RESTResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<RESTResult<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<RESTResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(RESTResult<T> result) {

                                if (result.status == RESTResult.SUCCESS) {
                                    return createData(result.data);
                                } else if (result.status == RESTResult.SIGN_OUT) {
                                    // 处理被踢出登录情况
                                } else {
                                    return Observable.error(new ServerException(result.message));
                                }
                                return Observable.empty();

                            }
                        }

                );
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
