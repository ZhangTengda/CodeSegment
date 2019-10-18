package com.testone.demo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.testone.demo.R;
import com.testone.demo.RecyclerViewBean;
import com.testone.demo.utils.ToastUtil;

import java.util.List;

public class MyRecyclerViewAdapter extends BaseQuickAdapter<RecyclerViewBean, BaseViewHolder> {

    private List<RecyclerViewBean> data;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, @Nullable List<RecyclerViewBean> data) {
        super(R.layout.recycler_item_title, data);
        this.mContext = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RecyclerViewBean item) {
        TextView title = helper.getView(R.id.rv_title);
        title.setText(item.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "当前的position is "+ (helper.getLayoutPosition() - getHeaderLayoutCount()));
            }
        });

        TextView description = helper.getView(R.id.rv_description);
        description.setText(item.getDescription());
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "当前的position is "+ (helper.getLayoutPosition()));
            }
        });
    }
}
