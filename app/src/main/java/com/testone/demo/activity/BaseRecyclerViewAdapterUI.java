package com.testone.demo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.RecyclerViewBean;
import com.testone.demo.adapter.MyRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class BaseRecyclerViewAdapterUI extends BaseActivity {

    private TextView textViewShow;
    private String localUrl;
    private EditText edittext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baserecyclerview_adapter);

        RecyclerView recyclerView = findViewById(R.id.base_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<RecyclerViewBean> lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            RecyclerViewBean bean = new RecyclerViewBean();
            bean.setTitle("明天七夕你和谁过 " + i);
            bean.setDescription("明天是个好日子，字字自己子子子自在");
            lists.add(bean);
        }

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, lists);
        recyclerView.setAdapter(adapter);


        /////------------------------------------------------/////////
        Button clickOne = findViewById(R.id.click_one);
        clickOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Editable text = edittext.getText();
                if (text != null) {
                    localUrl = text.toString();
                }

                String localString = "app-debug.zip";
                if (!TextUtils.isEmpty(localUrl)) {
                    localString = localUrl;
                }
                String zipFile = Environment.getExternalStorageDirectory() + "/Download/" + localString;
                File file = new File(zipFile);
                if (file.exists()) {
                    try {
                        String s = readZipFile(file, "mx_config.png");
                        Log.d("roger", "----------dddddd----------" + s);
                        String[] strings = parseJson(s);
                        textViewShow.setText(Arrays.toString(strings));
                        Log.d("roger", "----------dddddd------5555----------" + Arrays.toString(strings));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        });
        edittext = findViewById(R.id.eidttext);
        textViewShow = findViewById(R.id.text_show);
        /////------------------------------------------------/////////
    }

    private String[] parseJson(String s) {
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray colorgroups = jsonObject.getJSONArray("colorgroups");
                String[] colorGroupsArray = new String[colorgroups.length()];
                for (int i = 0; i < colorgroups.length(); i++) {
                    JSONObject colorObject = (JSONObject) colorgroups.get(i);
                    String color = colorObject.getString("color");
                    colorGroupsArray[i] = color;
                    Log.i("roger","1111111--------2222222" + color);
                }
                return colorGroupsArray;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new String[]{};
    }

    private String getSystemUpgradeVersion() {
        String zipFile = Environment.getExternalStorageState() + "/Download/app-debug.zip";
        File f = new File(zipFile);
        if (!f.exists()) {
            return "file is not exists";
        }
        try {
            ZipFile zf = new ZipFile(zipFile);
            InputStream in = new BufferedInputStream(new FileInputStream(
                    zipFile));
            ZipInputStream zin = new ZipInputStream(in);
            Enumeration enumeration = zf.entries();
            ZipEntry ze = null;
            while (enumeration.hasMoreElements()) {
                ze = (ZipEntry) enumeration.nextElement();
                if (!ze.isDirectory()) {
                    if (ze.getName().equals("version")) {
                        break;
                    }
                }
            }
            if (zf == null || ze == null) {
                return "ZipFile is null or ZipEntry is null";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
            String line;
            while ((line = br.readLine()) != null) {
                Log.d("roger", "======line: " + line);
                return line;
            }
            br.close();
            zin.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 读取zip包里的文件（不需要解压zip）
     *
     * @param zipFile         zip包
     * @param readFileName 需要读取的文件名 * @return 读取结果 * @throws Exception
     */
    public static String readZipFile(File zipFile, String readFileName) throws Exception {
        ZipFile zf = new ZipFile(zipFile);
        InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        String mFileData = "";
        String line;
        while ((ze = zin.getNextEntry()) != null) {
            if (!ze.isDirectory()) {
//                Log.d("roger", "file - " + ze.getName());
                if (ze.getName().contains(readFileName)) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
                    while ((line = br.readLine()) != null) {
                        Log.d("roger", line);
                        mFileData = line;
                    }
                    br.close();

                    zin.closeEntry();
                    in.close();
                    return mFileData;
                }
            }
        }
        zin.closeEntry();
        in.close();
        return mFileData;
    }


}
