package com.xnjz.one.http1.okcallback;

public interface ResultCallSimple {
    //网络请求失败,返回失败原因
    void onError(String message);
    //网络请求成功，但有可能不是200
    void onSuccess(String response);
}
