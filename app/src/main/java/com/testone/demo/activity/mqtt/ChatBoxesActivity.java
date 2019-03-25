package com.testone.demo.activity.mqtt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.adapter.MQTTAdapter;
import com.testone.demo.chatboxservice.ChatBoxMQTTService;
import com.testone.demo.chatboxservice.Constants;
import com.testone.demo.chatboxservice.MQTTListener;
import com.testone.demo.chatboxservice.MqttAppState;
import com.testone.demo.chatboxservice.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MQTT聊天室
 * https://github.com/biloba123/ChatRoom
 */
public class ChatBoxesActivity extends BaseActivity implements MQTTListener {

    private EditText inputEditText;
    private Gson gson = new Gson();
    private List<MqttMessage> messageList = new ArrayList<>();
    private MQTTAdapter adapter;
    private RecyclerView recycleriew;
    private String loginName = "121";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_boxes);

        Intent intentActivity = getIntent();
        loginName = intentActivity.getStringExtra("loginname");
        String password = intentActivity.getStringExtra("password");

        ChatBoxMQTTService.addMqttListener(this);
        Intent intent = new Intent(this, ChatBoxMQTTService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        // 布局recyclerview
        recycleriew = findViewById(R.id.chat_boxes_recyclerview);
        inputEditText = findViewById(R.id.send_message_edit);

        // 布局send button
        TextView sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    MqttMessage mqttMessage = new MqttMessage();
//                    mqttMessage.setUserid(Constants.MQTT_USERID);
                    mqttMessage.setUserid(loginName);
                    mqttMessage.setMessage(message);
//                    mqttMessage.setUsername(Constants.MQTT_USERNAME);
                    mqttMessage.setUsername("1" + loginName);
                    mqttMessage.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                    // Qos介绍 ： https://www.jianshu.com/p/a8502ad9d014
                    ChatBoxMQTTService.getMqttConfig().pubMsg(Constants.MQTT_TOPIC, gson.toJson(mqttMessage), 0);
                } else {
                    MqttAppState.getInstance().showToast("不可发送为空的消息");
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleriew.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);//倒序显示
        layoutManager.setStackFromEnd(true);

        adapter = new MQTTAdapter(this, messageList);
        recycleriew.setAdapter(adapter);
        adapter.setCurrentUserId(loginName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatBoxMQTTService.removeMqttListener(this);
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onLost() {

    }

    @Override
    public void onReceiveMsg(String topic, String message) {
        if (topic.equals(Constants.MQTT_TOPIC)) {
            MqttMessage mqttMessage = gson.fromJson(message, MqttMessage.class);
            if (messageList.size() > 0)
                messageList.add(0, mqttMessage);
            else
                messageList.add(mqttMessage);
            adapter.setList(messageList);
            recycleriew.scrollToPosition(0);
        }
    }

    @Override
    public void onSendSuccess() {
        MqttAppState.getInstance().showToast("消息发送成功");
        inputEditText.setText("");
//        inputEditText.clearFocus();
    }
}
