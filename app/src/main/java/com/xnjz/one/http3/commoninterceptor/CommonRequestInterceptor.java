package com.xnjz.one.http3.commoninterceptor;

import androidx.annotation.NonNull;

import com.xnjz.one.http3.base.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {

    private INetworkRequiredInfo requiredInfo;

    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        long l = System.currentTimeMillis();

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", this.requiredInfo.getAppVersionCode());
        builder.addHeader("Source", "source");
        builder.addHeader("Date", l + "");
        return chain.proceed(builder.build());
    }
}
