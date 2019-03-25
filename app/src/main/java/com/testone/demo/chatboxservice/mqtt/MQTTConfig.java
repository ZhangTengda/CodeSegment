package com.testone.demo.chatboxservice.mqtt;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.testone.demo.BuildConfig;
import com.testone.demo.chatboxservice.Constants;
import com.testone.demo.chatboxservice.MQTTListener;
import com.testone.demo.chatboxservice.MqttAppState;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTConfig {

    private static final String TAG = MQTTConfig.class.getSimpleName();

    private static final String host = Constants.MQTT_IP;
    private static final String port = Constants.MQTT_PORT;
    private static String userID = "admin";
    private static String passWord = "password";
    // 客户端ID
    private static String clientID = MqttAppState.getInstance().getIMEI();

    /**
     * Mqtt 重连次数
     **/
    private int mqttRetryCount = 0;

    /**
     * MQTT状态信息
     **/
    private boolean isConnect = false;

    /**
     * MQTT支持类
     **/
    private MqttAsyncClient mqttClient = null;

    private MQTTListener mMqttListener;

    public MQTTConfig(MQTTListener listener) {
        this.mMqttListener = listener;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case MQTTTag.MQTT_STATE_CONNECTED:
                    if (BuildConfig.DEBUG) Log.d(TAG, "handleMessage: connected");
                    mMqttListener.onConnected();
                    mqttRetryCount = 0;
                    break;

                case MQTTTag.MQTT_STATE_LOST:
                    if (BuildConfig.DEBUG) Log.d(TAG, "handleMessage: lost");
                    mMqttListener.onLost();
                    break;

                case MQTTTag.MQTT_STATE_FAIL:
                    if (BuildConfig.DEBUG) Log.d(TAG, "handleMessage: fail");
                    mMqttListener.onFail();
                    break;

                case MQTTTag.MQTT_STATE_RECEIVE_MSG:
                    if (BuildConfig.DEBUG) Log.d(TAG, "handleMessage: receive message");
                    MqttObject object = (MqttObject) msg.obj;
                    mMqttListener.onReceiveMsg(object.getTopic(), object.getMessage());
                    break;

                case MQTTTag.MQTT_STATE_SEND_SUCCESS:
                    if (BuildConfig.DEBUG) Log.d(TAG, "handleMessage: send success");
                    mMqttListener.onSendSuccess();
                    break;
            }
            return true;
        }
    });

    /**
     * 链接MQTT
     */
    public void connectMqtt() {
        try {
            mqttClient = new MqttAsyncClient("tcp://" + this.host + ":" + this.port,
                    "ClientID" + this.clientID, new MemoryPersistence());
            mqttClient.connect(getOptions(), null, mIMqttActionListener);
            mqttClient.setCallback(mMqttCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Mqtt的连接信息
     */
    private MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);//重连不保持状态
        if (this.userID != null && this.userID.length() > 0 && this.passWord != null && this.passWord.length() > 0) {
            options.setUserName(this.userID);//设置服务器账号密码
            options.setPassword(this.passWord.toCharArray());
        }
        options.setConnectionTimeout(10);//设置连接超时时间
        options.setKeepAliveInterval(20);//设置保持活动时间，超过时间没有消息收发将会触发ping消息确认
//        options.setAutomaticReconnect(true);
//        options.setCleanSession(false);
        return options;
    }

    /**
     * 自带的监听类，判断Mqtt活动变化
     */
    private IMqttActionListener mIMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            isConnect = true;
            Message msg = new Message();
            msg.arg1 = MQTTTag.MQTT_STATE_CONNECTED;
            handler.sendMessage(msg);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            isConnect = false;
            Message msg = new Message();
            msg.arg1 = MQTTTag.MQTT_STATE_FAIL;
            handler.sendMessage(msg);
        }
    };

    /**
     * 向Mqtt服务器发送数据
     */
    public void pubMsg(String Topic, String Msg, int Qos) {
        if (!isConnect) {
            Log.d(TAG, "Mqtt连接未打开");
            return;
        }
        try {
            /** Topic,Msg,Qos,Retained**/
            mqttClient.publish(Topic, Msg.getBytes(), Qos, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启MQTT
     */
    public void reStartMqtt() {
        if (mqttRetryCount < 5) {
            disConnectMqtt();
            connectMqtt();
            mqttRetryCount++;
        } else {
            Log.i(TAG, "mqtt server reconnect error!");
        }
    }

    /**
     * 断开MQTT链接
     */
    private void disConnectMqtt() {
        try {
            mqttClient.disconnect();
            mqttClient = null;
            isConnect = false;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向Mqtt服务器订阅某一个Topic
     *
     * @param mqttTopic
     * @param i
     */
    public void subTopic(String mqttTopic, int Qos) {
        if (!isConnect) {
            Log.d(TAG, "Mqtt连接未打开");
            return;
        }
        try {
            mqttClient.subscribe(mqttTopic, Qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自带的监听回传类
     */
    private MqttCallback mMqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
            isConnect = false;
            Message msg = new Message();
            msg.arg1 = MQTTTag.MQTT_STATE_LOST;
            handler.sendMessage(msg);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Message msg = new Message();
            msg.arg1 = MQTTTag.MQTT_STATE_RECEIVE_MSG;
            String content = new String(message.getPayload());
            MqttObject object = new MqttObject();
            object.setTopic(topic);
            object.setMessage(content);
            msg.obj = object;
            handler.sendMessage(msg);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Message msg = new Message();
            msg.arg1 = MQTTTag.MQTT_STATE_SEND_SUCCESS;
            handler.sendMessage(msg);
        }
    };

    private class MqttObject {
        String topic;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        String message;
    }

    public boolean isConnect() {
        return isConnect;
    }
}
