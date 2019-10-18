package com.testone.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class CountDownUI extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

        final CountDownLatch cdl = new CountDownLatch(3);
        Log.i("roger", "222222222");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("roger","44444444444444");
                cdl.countDown();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ;i < 10; i++) {
                    Log.i("roger2w","555555555");

                }
                cdl.countDown();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("roger","66666666666666");
                cdl.countDown();
            }
        });
        try {
            cdl.await();
            Log.i("roger", "333333333333333");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
