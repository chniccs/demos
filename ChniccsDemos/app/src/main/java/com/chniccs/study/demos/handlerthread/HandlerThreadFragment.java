package com.chniccs.study.demos.handlerthread;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chniccs.study.demos.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/3.
 * 一个用于演示HandlerThread的案例
 * <p/>
 * 什么是HandlerThread?
 * handlerThread是继承于Thread类的子类，也是一个线程类
 * <p/>
 * 有什么优势?
 * handlerThread创建后会自动创建一个looper，所以他是一个创建后就一直存在的线程，可以重复使用。
 * <p/>
 * 什么用处?
 * 如果应用中需要经常去作一些需要在子线程进行的操作，而每次又不会进行太久，就可以用这个方式，如频繁的倒计时，定时进行网络操作来更新数据等
 * <p/>
 * 用法：
 * <p/>
 * 1、创建一个HandlerThread对象，并在构造中传String 类型的值，其值为创建的子线程的名称
 * <p/>
 * HandlerThread mHandlerThread = new HandlerThread("my_thread");
 * <p/>
 * 2、将handlerThread的looper对象作为参数来创建一个handler
 * Handler handler = new Handler(mHandlerThread.getLooper);
 * <p/>
 * 3、发送消息给此handler就可以在接收到消息后在mHandlerThread线程工作了
 * handler.sendEmptyMessage(MSG_UPDATE_INFO);
 * <p/>
 * 4、执行完成之后可以再创建另外一个主线程中的handler，通过它来更新UI
 * Handler mainHandler = new Handler();
 * mainHandler.setEmptyMessage(UPDATE_UI_THREAD);
 */
public class HandlerThreadFragment extends Fragment {
    private Handler mHandlerWork, mHandler;
    private HandlerThread mHandlerThread;
    private static final int MSG_UPDATE_INFO = 1;
    private static final int UPADTE_UI_THREAD = 2;
    private boolean doWork = false;

    @BindView(R.id.headlerthread_tv)
    TextView mTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_handler_thread, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        doWork = true;
        mHandlerWork.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    public void onPause() {
        super.onPause();
        doWork = false;
    }

    private void init() {
        mHandlerThread = new HandlerThread("chniccs's worker");
        mHandlerThread.start();//一定要调用
        mHandlerWork = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_UPDATE_INFO) {
                    doWork();
                }

            }
        };
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == UPADTE_UI_THREAD) {
                    mTv.setText(String.format(getResources().getString(R.string.current_time), new Date(System.currentTimeMillis()).toString()));
                    mHandlerWork.sendEmptyMessage(MSG_UPDATE_INFO);
                }
                super.handleMessage(msg);
            }
        };
    }

    private void doWork() {
        //模拟耗时
        try {
            Thread.sleep(1000);
            if (doWork) {
                mHandler.sendEmptyMessage(UPADTE_UI_THREAD);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        mHandlerThread.quit();
    }
}
