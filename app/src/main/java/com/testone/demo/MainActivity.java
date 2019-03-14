package com.testone.demo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.testone.demo.activity.EditTextActivity;
import com.testone.demo.activity.TestActivity;
import com.testone.demo.activity.TestUrlRequestActivity;
import com.testone.demo.activity.ele.StartSearchActivity;
import com.testone.demo.activity.guodu.GuoDuActivity;
import com.testone.demo.activity.seekbar.SeekBarKtActivity;
import com.testone.demo.activity.share.ShareOneActivity;
import com.testone.demo.activity.keyboard.KeybardActivity;
import com.testone.demo.activity.listview.ListViewActivity;
import com.testone.demo.activity.listview.LoadMoreWrapperActivity;
import com.testone.demo.activity.listview.RecyclerViewActivity;
import com.testone.demo.database.DataBaseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartSearchActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShareOneActivity.class));
            }
        });


        findViewById(R.id.test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GuoDuActivity.class));
            }
        });


        findViewById(R.id.test4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditTextActivity.class));
            }
        });


        findViewById(R.id.test5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadMoreWrapperActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KeybardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SeekBarKtActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestUrlRequestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.test11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataBaseActivity.class);
                startActivity(intent);
            }
        });


    }

}
