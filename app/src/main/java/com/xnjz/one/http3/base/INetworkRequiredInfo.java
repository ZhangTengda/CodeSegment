package com.xnjz.one.http3.base;

import android.app.Application;

public interface INetworkRequiredInfo {
    String getAppVersionCode();

    String getAppVersionName();

    boolean isDebug();

    Application getApplicationContext();
}
