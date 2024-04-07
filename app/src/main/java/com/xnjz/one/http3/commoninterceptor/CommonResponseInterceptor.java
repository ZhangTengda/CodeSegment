package com.xnjz.one.http3.commoninterceptor;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CommonResponseInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response proceed = chain.proceed(chain.request());
        LogUtils.d("Request requestTIme--- " + (System.currentTimeMillis() - requestTime) + "毫秒");
        return proceed;
    }
}
