package com.testone.demo.activity.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.testone.demo.R;

public class ShareOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_one);

        findViewById(R.id.activity_share_one_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v);
            }
        });
    }


    private void click(View v) {
        Intent intent = new Intent(this, ShareTwoActivity.class);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(v, ShareTwoActivity.VIEW_NAME_HEADER_IMAGE));

        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }
}
