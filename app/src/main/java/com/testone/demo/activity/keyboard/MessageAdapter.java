package com.testone.demo.activity.keyboard;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.testone.demo.R;

import java.util.List;

public class MessageAdapter extends CommonRecyclerAdapter<Message> {

    public MessageAdapter(Context context, List<Message> messageList, @LayoutRes int layoutId) {
        super(context, messageList, layoutId);
    }

    @Override
    protected void bindData(CommonRecyclerHolder holder, Message message) {
        holder.setText(R.id.tv_message, message.getMessage());
    }
}
