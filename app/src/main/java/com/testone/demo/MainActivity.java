package com.testone.demo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.testone.demo.ele.StartSearchActivity;
import com.testone.demo.guodu.GuoDuActivity;
import com.testone.demo.share.ShareOneActivity;
import com.testone.myapplication.R;
import com.testone.demo.keyboard.KeybardActivity;
import com.testone.demo.listview.ListViewActivity;
import com.testone.demo.listview.LoadMoreWrapperActivity;
import com.testone.demo.listview.RecyclerViewActivity;

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


    }

}
