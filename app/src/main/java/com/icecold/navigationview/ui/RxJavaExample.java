package com.icecold.navigationview.ui;

import android.os.Bundle;

import com.icecold.navigationview.R;
import com.icecold.navigationview.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by icecold_laptop_2 on 2018/4/28.
 */

public class RxJavaExample extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void loadData() {
//        takeUntilOperation();
        flatMapOperation();
//        normalOperation();
//        combineLatestOperation();
    }

    private void combineLatestOperation() {
        Observable.combineLatest(
                Observable.intervalRange(0, 4, 0, 2, TimeUnit.SECONDS),
                Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long one, Long two) throws Exception {
                        Logger.d("合并前的数据是 第一个 = "+one+" 第二个 = "+two);
                        return one * two;
                    }
                })
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long result) throws Exception {
                Logger.d("合并的结果是 = "+result);
            }
        });
    }

    private void normalOperation() {
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onError(new Exception("发生错误了"));
//                emitter.onNext(3);
//                emitter.onComplete();
//            }
//        }).retry(3)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Logger.d("接收的事件 = "+integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Logger.d("全部完成了");
//                    }
//                });
        Observable.just(1,3,"2")
                .cast(Integer.class)
                .retry(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.d("接收到发射的事件 = " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                    }
                });
    }

    private void flatMapOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
//                emitter.onError(new Exception("发生错误了"));
//                emitter.onNext(3);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                ArrayList<String> source = new ArrayList<>();
                for (int i = 0; i < 3 ; i++) {
                    source.add("我是事件 "+integer+" 拆开后的子事件"+i);
                }
                //如果使用flatMap操作符的话，内部是采用的merge合并的是并行执行的，延迟3s瞬间执行完所有的操作
                //如果使用concatMap操作符的话，内部是采用的concat合并的是串行执行的，每个事件都会延时3s执行，这里有三个有效事件，所有总共花费9s
                return Observable.fromIterable(source).delay(1,TimeUnit.SECONDS);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String source) throws Exception {
                        Logger.d(source);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.d("全部完成了");
                    }
                });
    }

    private void takeUntilOperation() {
        io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong >= 5;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.d("订阅开始");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.d("发送出来的值为 = "+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Logger.d("发送完成了");
                    }
                });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
