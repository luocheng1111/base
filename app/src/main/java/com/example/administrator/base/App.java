package com.example.administrator.base;

import android.app.Application;

import com.lc.base.util.Utils;

/**
 * Created by Administrator on 2018/2/26.
 */

public class App extends Application {

    private static App sInstance;

    public static App getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        Utils.init(this);
    }

}
