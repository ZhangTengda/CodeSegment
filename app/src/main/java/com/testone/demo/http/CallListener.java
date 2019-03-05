package com.testone.demo.http;

public interface CallListener {

    void onSuccess(String response);

    void onFail();
}
