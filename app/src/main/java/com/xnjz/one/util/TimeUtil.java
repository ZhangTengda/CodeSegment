package com.xnjz.one.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public class TimeUtil {

    private static Timer timer = null;
    private static TimerTask timerTask = null;

    private static long workTimeLong = 0;

    public static long getWorkTimeLong() {
        return workTimeLong;
    }

    private static Handler timerHandler = new Handler(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (mTimeCallBack != null) {
                    mTimeCallBack.getWorkTime((Long) msg.obj);
                }
            }
        }
    };

    public static void startCountdownByTime(TimeCllBack timeCallBack) {
        stopTimer();
        mTimeCallBack = timeCallBack;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                workTimeLong++;
                timerHandler.sendMessage(timerHandler.obtainMessage(0, workTimeLong));
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    private static TimeCllBack mTimeCallBack;

//    public static void setTimeCallBack(TimeCllBack timeCallBack) {
//        mTimeCallBack = timeCallBack;
//    }

    public interface TimeCllBack {
        void getWorkTime(long time);
    }


    private static void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        workTimeLong = 0L;
        timer = null;
        timerTask = null;
    }
}
