package com.testone.demo.share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.testone.myapplication.R;

public class ShareTwoActivity extends AppCompatActivity {


    public static String VIEW_NAME_HEADER_IMAGE = "view_name_header_image";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_two);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView viewById = (TextView) findViewById(R.id.activity_share_two_textview);

        ViewCompat.setTransitionName(viewById, VIEW_NAME_HEADER_IMAGE);
    }
}
