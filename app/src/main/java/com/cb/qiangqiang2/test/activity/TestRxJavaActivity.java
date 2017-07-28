package com.cb.qiangqiang2.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cb.qiangqiang2.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TestRxJavaActivity extends Activity {
    private static final String TAG = "TestRxJavaActivity";

    TextView tv;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestRxJavaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rx_java);

        tv = (TextView) findViewById(R.id.tv);

        initData();
    }

    private void initData() {
        m1();
        m2();
        m3();
        m4();
        m5();
        m6();
    }

    private void m6() {

    }

    private void m5() {
        Observable
                .from(new String[]{"1", "2"})
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return Observable.from(new Integer[]{Integer.parseInt(s), 33});
                    }
                })
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv.setText(tv.getText().toString() + integer + "...." + "\n");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv.setText(tv.getText().toString() + integer + "\n");
                    }
                });
    }

    private void m4() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onNext("aaa");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tv.setText(tv.getText().toString() + s + "\n");
                    }
                });
    }

    private void m3() {
        Observable<String> observable = Observable.just("a", "b", "c", "d");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tv.setText(tv.getText().toString() + s + "\n");
                    }
                });
    }

    private void m2() {
        Observable observable = Observable.just("a", "b", "d");
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Timber.e(s);
            }
        };
        observable.subscribe(action1);
    }

    private void m1() {
        Observable observable = Observable.just("a", "b", "d");
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Timber.e("onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("onError()");
            }

            @Override
            public void onNext(String string) {
                Timber.e(string);
            }

            @Override
            public void onStart() {
                Timber.e("onStart()");
            }
        });
    }
}
