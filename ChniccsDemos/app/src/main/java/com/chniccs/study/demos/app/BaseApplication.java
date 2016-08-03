package com.chniccs.study.demos.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by chniccs on 16/8/3.
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static Context getContext(){
        return mContext;
    }
}
