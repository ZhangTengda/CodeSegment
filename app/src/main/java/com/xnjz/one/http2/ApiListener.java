package com.xnjz.one.http2;

public interface ApiListener {
    //Request success
    void success(ApiUtil apiUtil);

    //Request failed
    void failrure(ApiUtil apiUtil);
}
