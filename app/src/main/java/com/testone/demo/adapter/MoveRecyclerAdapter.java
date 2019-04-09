package com.testone.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testone.demo.R;

import java.util.List;

public class MoveRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> messageList;

    public MoveRecyclerAdapter(Context context, List<String> messageList) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.move_recycler_item_view, null);

        return new MoveRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MoveRecyclerViewHolder) {
            MoveRecyclerViewHolder holder = (MoveRecyclerViewHolder) viewHolder;
            holder.recyclerItemTextView.setText(messageList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    public class MoveRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView recyclerItemTextView;
        public LinearLayout ll_item;

        public MoveRecyclerViewHolder(View view) {
            super(view);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            recyclerItemTextView = view.findViewById(R.id.move_recycler_item_tv);
        }
    }
}
