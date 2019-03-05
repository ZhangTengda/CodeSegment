package com.testone.demo.guodu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.testone.myapplication.R;

public class Transitions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transition_to);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }
}
