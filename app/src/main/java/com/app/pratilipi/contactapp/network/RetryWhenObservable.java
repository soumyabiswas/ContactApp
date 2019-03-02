package com.app.pratilipi.contactapp.network;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by triode on 13/7/16
 */
public class RetryWhenObservable implements
        Func1<Observable<? extends Throwable>, Observable<?>> {

    private static final String TAG = "RetryWhenObservable";
    final int maxRetryCount;
    private final long RETRY_DELAY_MILLIS = 1000; // 1 Second
    int retryCount = 0;

    public RetryWhenObservable() {
        super();
        maxRetryCount = 5;
    }

    public RetryWhenObservable(int retryCount) {
        super();
        maxRetryCount = retryCount;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (handleException(throwable) &&
                        ++retryCount <= maxRetryCount) {
                    return Observable.timer(retryCount * RETRY_DELAY_MILLIS,
                            TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }


    boolean handleException(Throwable error) {
        return (error instanceof IOException || error instanceof UnknownHostException ||
                error instanceof SocketTimeoutException);
    }
}
