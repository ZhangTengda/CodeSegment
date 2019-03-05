package com.testone.demo.guodu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.testone.myapplication.R;

public class GuoDuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        findViewById(R.id.guodu_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuoDuActivity.this, Transitions.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
            }
        });
    }

}
