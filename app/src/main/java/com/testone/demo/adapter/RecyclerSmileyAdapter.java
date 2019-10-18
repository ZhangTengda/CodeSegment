package com.testone.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.testone.demo.R;
import com.testone.demo.activity.smiley.ConversationSmiley;

import java.util.List;

public class RecyclerSmileyAdapter extends RecyclerView.Adapter {

    private List<ConversationSmiley> list;
    private Context context;

    public RecyclerSmileyAdapter(Context context, List<ConversationSmiley> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mx_smiley_grid_item_s, null);
        return new SmileyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SmileyViewHolder) {
            ConversationSmiley conversationSmiley = list.get(i);
            SmileyViewHolder smileyViewHolder = (SmileyViewHolder) viewHolder;
            smileyViewHolder.imageView.setImageResource(conversationSmiley.getImageID());
        }
    }

    private float standardFloat = 0;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (holder instanceof SmileyViewHolder) {
                SmileyViewHolder smileyViewHolder = (SmileyViewHolder) holder;
                float alpha = (float) payloads.get(0);

                if (alpha <= 1) {
                    // 从0开始向右滑，滑动到1
//                    if (alpha > standardFloat){
//                        smileyViewHolder.imageView.setAlpha(alpha);
//                        smileyViewHolder.delView.setAlpha(1 - alpha);
//                    } else {
//                        //左滑
//                        smileyViewHolder.imageView.setAlpha(alpha);
//                        smileyViewHolder.delView.setAlpha(1 - alpha);
//                    }

                    smileyViewHolder.imageView.setAlpha(alpha);
                    smileyViewHolder.delView.setAlpha(1 - alpha);

                    standardFloat = alpha;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class SmileyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final ImageView delView;

        public SmileyViewHolder(View inflate) {
            super(inflate);
            imageView = inflate.findViewById(R.id.art_emoji_icon_iv);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int layoutPosition = getLayoutPosition();
                        onItemClickListener.onItemClick(layoutPosition, view);
                    }
                }
            });

            delView = inflate.findViewById(R.id.art_emoji_icon_iv_del);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
