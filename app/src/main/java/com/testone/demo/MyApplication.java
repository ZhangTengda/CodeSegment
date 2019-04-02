package com.testone.demo;

import android.app.Application;
import android.content.res.Configuration;

import com.testone.demo.chatboxservice.MqttAppState;

import org.litepal.LitePal;

public class MyApplication extends Application {

    private static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        instance = this;
        MqttAppState.getInstance();
        MqttAppState.setApplication(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MqttAppState.getInstance().onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
