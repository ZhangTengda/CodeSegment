package com.testone.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.chatboxservice.Constants;
import com.testone.demo.chatboxservice.MqttMessage;

import java.util.List;

public class MQTTAdapter extends RecyclerView.Adapter<MQTTAdapter.ViewHolder> {

    private Context mContext;
    private List<MqttMessage> messageList;

    private int ITEM_MESSAGE_ME = 0;
    private int ITEM_MESSAGE_OTHER = 1;

    private String currentUserId;

    public MQTTAdapter(Context context, List<MqttMessage> list) {
        mContext = context;
        messageList = list;
    }

    public void setList(List<MqttMessage> list) {
        messageList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_MESSAGE_ME) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_me, null, false);
            return new ViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_other, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MqttMessage message = messageList.get(position);
        viewHolder.timeTV.setText(message.getSendTime());
        viewHolder.userTV.setText(message.getUsername());
        viewHolder.contentTV.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MqttMessage message = messageList.get(position);
        if (currentUserId.equals(message.getUserid())) {
            return ITEM_MESSAGE_ME;
        }
        return ITEM_MESSAGE_OTHER;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View contentView;
        TextView userTV;
        TextView contentTV;
        TextView timeTV;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            userTV = contentView.findViewById(R.id.message_user);
            contentTV = contentView.findViewById(R.id.message_text);
            timeTV = contentView.findViewById(R.id.message_time);
        }
    }
}
