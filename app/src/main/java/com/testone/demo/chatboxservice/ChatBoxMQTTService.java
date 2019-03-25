package com.testone.demo.chatboxservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testone.demo.R;
import com.testone.demo.chatboxservice.mqtt.MQTTConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatBoxMQTTService extends Service implements MQTTListener {

    private static MQTTConfig mqttConfig;
    private static final int MESSAGE_CHECK = 0;

    private Timer timer = new Timer(true);
    private static List<MQTTListener> mMqttListenerList = new ArrayList<>();

    private CheckMqttThread myThread;

    public static void addMqttListener(MQTTListener listener) {
        if (!mMqttListenerList.contains(listener)) {
            mMqttListenerList.add(listener);
        }
    }

    public static void removeMqttListener(MQTTListener listener) {
        mMqttListenerList.remove(listener);
    }

    public static MQTTConfig getMqttConfig() {
        return mqttConfig;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_CHECK) {
                if (mqttConfig != null && !mqttConfig.isConnect()) {
                    mqttConfig.connectMqtt();
                }
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mqttConfig = new MQTTConfig(this);
        mqttConfig.connectMqtt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (myThread == null) {
            myThread = new CheckMqttThread();//定时检查mqtt服务是否连接
            timer.scheduleAtFixedRate(myThread, 2000, 10000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//启动前台服务
            startForeground(1, getNotification());
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onConnected() {
        Log.i("mqtt", "mqttserver connnected");
        if (mqttConfig != null) {
            mqttConfig.subTopic(Constants.MQTT_TOPIC, 0);
        }
        for (MQTTListener mqttListener : mMqttListenerList) {
            mqttListener.onConnected();
        }
    }

    @Override
    public void onFail() {
        Log.i("mqtt", "mqttserver fail");
        if (mqttConfig != null) {
            mqttConfig.reStartMqtt();
        }
        for (MQTTListener mqttListener : mMqttListenerList) {
            mqttListener.onFail();
        }
    }

    @Override
    public void onLost() {
        Log.i("mqtt", "mqttserver lost");
        if (mqttConfig != null) {
            mqttConfig.reStartMqtt();
        }
        for (MQTTListener mqttListener : mMqttListenerList) {
            mqttListener.onLost();
        }
    }

    @Override
    public void onReceiveMsg(String topic, String message) {
        Log.i("mqtt", "mqttserver receive message:" + message);
        for (MQTTListener mqttListener : mMqttListenerList) {
            mqttListener.onReceiveMsg(topic, message);
        }
    }

    @Override
    public void onSendSuccess() {
        Log.i("mqtt", "mqttserver send success!");
        for (MQTTListener mqttListener : mMqttListenerList) {
            mqttListener.onSendSuccess();
        }
    }

    private class CheckMqttThread extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(MESSAGE_CHECK);
        }
    }

    /**
     *
     * @return
     */
    private Notification getNotification() {
        Notification notification=null;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //设置Notification的ChannelID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("mqtt", getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(getApplicationContext(), "mqtt").build();
        }else {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText("");
            notification = builder.build();
        }
        return notification;
    }
}
