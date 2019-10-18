package com.testone.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.testone.demo.activity.BaseActivity;
import com.testone.demo.utils.BadgeUtil;
import com.testone.demo.utils.ToastUtil;

public class BadgeUI extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

        findViewById(R.id.badge_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(BadgeUI.this, "出来6个角标啊啊");
                BadgeUtil.setBadgeCount(BadgeUI.this, 6);
            }
        });
    }
}
