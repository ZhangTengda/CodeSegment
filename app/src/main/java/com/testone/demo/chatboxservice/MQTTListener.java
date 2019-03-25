package com.testone.demo.chatboxservice;

public interface MQTTListener {

    void onConnected();//连接成功
    void onFail();//连接失败
    void onLost();//丢失连接
    void onReceiveMsg(String topic, String message);//接收到消息
    void onSendSuccess();//消息发送成功
}
