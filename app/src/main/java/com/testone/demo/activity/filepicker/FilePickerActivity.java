package com.testone.demo.activity.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.adapter.FilesListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class FilePickerActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private String startPath = "/";

    private List<File> mFilesList = new ArrayList<>();

    private File bootFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());//根目录
    private FilesListAdapter fileListAdapter;
    private String currentPath;
    private boolean mOptMultiFileItem = true; //是否支持多选

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filepicker);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData(startPath);

        listSetAdapter();
    }

    private void initData(String path) {
        mFilesList.clear();
        File startFile = new File(path);
        if (startFile.exists() && startFile.isDirectory()){
            File[] files = startFile.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    mFilesList.add(files[i]);
                }
            }
        }

        // 文件排序
        fileSort();

        if (!TextUtils.equals(path, Environment.getExternalStorageDirectory().getAbsolutePath())){
            File parentFile = startFile.getParentFile();
            mFilesList.add(0, bootFile);
            mFilesList.add(1, parentFile);
        }
    }

    /**
     *
     */
    private void fileSort() {
        Collections.sort(mFilesList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                boolean isDirectory1 = file1.isDirectory();
                boolean isDirectory2 = file2.isDirectory();
                if (isDirectory1 && !isDirectory2)
                    return -1;
                if (!isDirectory1 && isDirectory2)
                    return 1;
                // Default, ExFilePicker.SORT_NAME_ASC
                return file1.getName().toLowerCase(Locale.getDefault()).compareTo(file2.getName().toLowerCase(Locale.getDefault()));
            }
        });
    }

    /**
     *
     */
    private void listSetAdapter() {
        if (fileListAdapter == null){
            fileListAdapter = new FilesListAdapter(this, mFilesList, startPath, mOptMultiFileItem);
            currentPath = startPath;
            recyclerView.setAdapter(fileListAdapter);
        }else{
//            if(onRefreshListener!=null){
//                onRefreshListener.onRefreshList(getFileListsSize());
//            }
            fileListAdapter.notifyDataSetChanged();
        }
    }
}
