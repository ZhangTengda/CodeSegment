package com.testone.demo.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.testone.demo.R;

public class TestActivity extends AppCompatActivity {
    private TextView suofangView;
    private EditText editText;
    private int subViewWidth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suofangView = (TextView) findViewById(R.id.suofang_cancel);
        suofangView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnimator();
                editText.clearFocus();
//                v.setVisibility(View.GONE);
            }
        });

        editText = (EditText) findViewById(R.id.edittext);


        findViewById(R.id.zhibuzhidao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reduce();
//                suofangView.setVisibility(View.VISIBLE);
            }
        });
    }


    private void openAnimator() {
        suofangView.setVisibility(View.GONE);
        View parentView = (View) editText.getParent();
        int parentViewWidth = parentView.getWidth();
        subViewWidth = editText.getWidth();
        ValueAnimator openAnimator = ValueAnimator.ofFloat(editText.getWidth(), parentViewWidth);
        openAnimator.setDuration(500);
        openAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updata(animation);
            }
        });
        openAnimator.start();

    }

    private void updata(ValueAnimator animator) {
        float currentValue = (float) animator.getAnimatedValue();
        editText.getLayoutParams().width = (int) currentValue;
        editText.requestLayout();
    }


    private void reduce() {
        ValueAnimator closeAnimator = ValueAnimator.ofFloat(editText.getWidth(), subViewWidth);
        closeAnimator.setDuration(500);
        closeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updata(animation);
            }
        });
        closeAnimator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                suofangView.setVisibility(View.VISIBLE);
            }
        }, 500);
    }
}
