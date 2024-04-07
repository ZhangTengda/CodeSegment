package com.xnjz.one.http3.base;

import android.app.Application;

import com.xnjz.one.BuildConfig;
import com.xnjz.one.BuildConfig;

public class MyNetWork implements INetworkRequiredInfo{


    private Application mApplication;

    public MyNetWork(Application application){
        this.mApplication = application;
    }

    @Override
    public String getAppVersionCode() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionName() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }
}
