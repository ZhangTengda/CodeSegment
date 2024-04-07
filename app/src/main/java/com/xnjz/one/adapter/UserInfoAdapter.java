package com.xnjz.one.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xnjz.one.R;
import com.xnjz.one.room.UserInfoBean;

public class UserInfoAdapter extends BaseQuickAdapter<UserInfoBean, BaseViewHolder> {

    public UserInfoAdapter() {
        super(R.layout.adapter_userinfo_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, UserInfoBean userInfoBean) {
        holder.setText(R.id.tv_name, userInfoBean.getName());
        holder.setText(R.id.tv_age, userInfoBean.getBirthday());
        holder.setText(R.id.tv_sex, TextUtils.equals(userInfoBean.getSex(), "0") ? "男" : "女");
    }
}
