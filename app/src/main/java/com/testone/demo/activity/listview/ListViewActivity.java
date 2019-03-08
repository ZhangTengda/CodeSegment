package com.testone.demo.activity.listview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.testone.demo.R;
import com.testone.demo.adapter.ListViewAdapter;
import com.testone.demo.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private List<Person> list = new ArrayList<>();
    private LoadMoreListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listview = findViewById(R.id.listview);

        listview.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                    }
                },1500);
            }
        });

        for (int i = 0; i < 16; i++) {
            Person person = new Person();
            person.setName("张小鱼" + i);
            person.setAge(20 + i);
            list.add(person);
        }

        ListViewAdapter adapter = new ListViewAdapter(this, list);
        listview.setAdapter(adapter);
    }

    private void loadMore() {
        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setName("张小鱼 新增" + i);
            person.setAge(111111 + i);
            list.add(person);
        }
        listview.setLoadCompleted();
    }

    public class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
