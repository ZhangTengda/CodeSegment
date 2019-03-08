package com.testone.demo.activity.keyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testone.demo.R;

import java.util.ArrayList;
import java.util.List;

public class KeybardActivity extends AppCompatActivity {
    
    private String TAG = KeybardActivity.class.getSimpleName();

    private EmojiKeyboard emojiKeyboard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        initView();
    }

    private void initView() {
        RecyclerView rv_messageList = (RecyclerView) findViewById(R.id.rv_messageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_messageList.setLayoutManager(linearLayoutManager);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("1"));
        messageList.add(new Message("2"));
        messageList.add(new Message("3"));
        messageList.add(new Message("4"));
        messageList.add(new Message("5"));
        messageList.add(new Message("6"));
        messageList.add(new Message("7"));
        messageList.add(new Message("8"));
        messageList.add(new Message("9"));
        messageList.add(new Message("10"));
        messageList.add(new Message("11"));
        messageList.add(new Message("12"));
        messageList.add(new Message("13"));
        messageList.add(new Message("14"));
        messageList.add(new Message("15"));
        messageList.add(new Message("16"));
        messageList.add(new Message("17"));
        messageList.add(new Message("18"));
        messageList.add(new Message("19"));
        messageList.add(new Message("20"));
        MessageAdapter messageAdapter = new MessageAdapter(this, messageList, R.layout.item_message);
        rv_messageList.setAdapter(messageAdapter);

        EditText et_inputMessage = (EditText) findViewById(R.id.et_inputMessage);
        ImageView iv_more = (ImageView) findViewById(R.id.iv_more);
        LinearLayout ll_rootEmojiPanel = (LinearLayout) findViewById(R.id.ll_rootEmojiPanel);
        emojiKeyboard = new EmojiKeyboard(this, et_inputMessage, ll_rootEmojiPanel, iv_more, rv_messageList);
        emojiKeyboard.setEmoticonPanelVisibilityChangeListener(new EmojiKeyboard.OnEmojiPanelVisibilityChangeListener() {
            @Override
            public void onShowEmojiPanel() {
                Log.e(TAG, "onShowEmojiPanel");
            }

            @Override
            public void onHideEmojiPanel() {
                Log.e(TAG, "onHideEmojiPanel");
            }
        });
    }
}
