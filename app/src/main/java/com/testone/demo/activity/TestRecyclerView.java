package com.testone.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.testone.demo.R;
import com.testone.demo.adapter.TestRecyclerViewAdapter;
import com.testone.demo.utils.ToastUtil;
import com.testone.demo.view.SmileyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerView extends BaseActivity {

    private List<String> list = new ArrayList<>();
    private TestRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recyclerview);


        for (int i = 0; i < 100; i++) {
            list.add("你好" + (i + 1));
        }
        SmileyRecyclerView recyclerView = findViewById(R.id.recyclerview_test);
        adapter = new TestRecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnPagerChageListener(new SmileyRecyclerView.onPagerChageListener() {
            @Override
            public void onPagerChage(int position) {
                Log.i("111111111111111", "onPagerChage: "+position);
            }
        });
        recyclerView.setOnPagerPosition(list.size() - 1);


//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list.set(17, System.currentTimeMillis()+"");
//                ToastUtil.shortToast(TestRecyclerView.this, "点击了哦");
//                adapter.notifyItemChanged(17);
//            }
//        });
//
//        for (int i = 0; i < 100; i++) {
//            list.add("你好" + (i + 1));
//        }
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview_test);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//
//        adapter = new TestRecyclerViewAdapter(this, list);
//        recyclerView.setAdapter(adapter);
    }
}
