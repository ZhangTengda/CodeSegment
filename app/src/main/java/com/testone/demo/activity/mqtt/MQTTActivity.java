package com.testone.demo.activity.mqtt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.service.IGetMessageCallBack;
import com.testone.demo.service.MQTTService;
import com.testone.demo.service.MyServiceConnection;
import com.testone.demo.utils.ToastUtil;

public class MQTTActivity extends BaseActivity implements IGetMessageCallBack {

    private TextView textView;
    private TextView button;
    private MyServiceConnection serviceConnection;
    private MQTTService mqttService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);

        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);

        serviceConnection = new MyServiceConnection();
        serviceConnection.setIGetMessageCallBack(MQTTActivity.this);

        Intent intent = new Intent(this, MQTTService.class);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.shortToast(getApplicationContext(), "测试一下子");
                MQTTService.publish("测试一下子");
            }
        });
    }

    @Override
    public void setMessage(String message) {
        textView.setText(message);
        mqttService = serviceConnection.getMqttService();
        mqttService.toCreateNotification(message);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
