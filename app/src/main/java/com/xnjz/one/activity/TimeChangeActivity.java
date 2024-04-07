package com.xnjz.one.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xnjz.one.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimeChangeActivity extends AppCompatActivity {

//    private Handler timerHandler = new Handler(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0) {
//                setCountdownTimeText(msg.obj as Long);
//            }
//        }
//    };
    private Timer timer = null;
    private TimerTask timerTask = null;
    private TextView textView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text1);

    }

//    private fun startCountdownByTime() {
//        stopTimer()
//        timerTask = object :TimerTask() {
//            override fun run() {
//                timerHandler.sendMessage(timerHandler.obtainMessage(0, System.currentTimeMillis()))
//            }
//        }
//        timer = Timer()
//        timer ?.schedule(timerTask, 0, 1000)
//    }
//
//    private fun stopTimer() {
//        timer ?.cancel()
//        timer = null
//        timerTask = null
//    }

//    private fun setCountdownTimeText(Long time) {
//
//
//        textView.setText();
//        binding.tvCountdownText.run {
//            post {
//                text = text.toString() + "${dateFormat.format(Date(time))}\n"
//            }
//        }
//    }
//
//    private void clearText() {
//        binding.tvCountdownText.text = ""
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopTimer();
//    }
}
