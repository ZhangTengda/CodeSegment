package com.testone.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testone.demo.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> messageList;

    public CalendarAdapter(Context context, List<String> messageList) {
        this.context = context;
        this.messageList= messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);

        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String s = messageList.get(i);
        if (viewHolder instanceof CalendarHolder) {
            CalendarHolder calendarHolder = (CalendarHolder) viewHolder;
            calendarHolder.calendarContent.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    /**
     *
     */
    public class CalendarHolder extends RecyclerView.ViewHolder {

        private final TextView calendarContent;

        public CalendarHolder(View view) {
            super(view);
            calendarContent = view.findViewById(R.id.calendar_content);
        }
    }
}
