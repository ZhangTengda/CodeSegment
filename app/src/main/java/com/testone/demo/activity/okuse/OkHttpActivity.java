package com.testone.demo.activity.okuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.utils.AESUtil;
import com.testone.demo.utils.CommonData;
import com.testone.demo.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHttp 请求方式
 */
public class OkHttpActivity extends BaseActivity {

    private TextView showContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        showContent = findViewById(R.id.show_content);
    }

    /**
     * Get 同步请求
     **/
    public void test1(View view) {
        getDatasync();
    }

    /**
     * Get 异步请求
     **/
    public void test2(View view) {
        getDataAsync();
    }

    /**
     * Post 同步请求
     **/
    public void test3(View view) {

    }

    /**
     * Post 异步请求
     **/
    public void test4(View view) {
        postDataWithParame();
    }


    public void getDatasync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("11111111111", "response.code()==" + response.code());
                        Log.d("11111111111", "response.message()==" + response.message());

                        final Response finalResponse = response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showContent.setText(finalResponse.message());
                            }
                        });

                        Log.d("11111111111", "res==" + response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    } else {
                        Log.d("11111111111", "获取数据失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getDataAsync() {
        OkHttpClient client = new OkHttpClient();

        HashMap<String,String> paramsMap = new HashMap<>();
        paramsMap.put("all_dept", "false");
        paramsMap.put("only_dept", "false");
        paramsMap.put("limit", "20");


        Request request = new Request.Builder()
                .url(CommonData.HTTP_HOST + CommonData.CONTACT_LIST+ HttpUtils.paramsTransform(paramsMap))
                .addHeader("Authorization","3lShv1i-t33tuAqw41TAKgjtqqvyg5CZ8y4Ji5fl61H-0xJ3")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("11111111111", "获取数据失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {//回调的方法执行在子线程。
                    Log.d("11111111111", "获取数据成功了");
                    Log.d("11111111111", "response.code()==" + response.code());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showContent.setText(response.body().toString());
                        }
                    });
                    Log.d("11111111111", "response.body().string()==" + response.body().string());
                } else {
                    Log.d("11111111111", "获取数据---失败了"+response.code());
                }
            }
        });
    }

    /**
     * Post 异步请求
     */
    private void postDataWithParame() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体

        Request request = new Request.Builder()//创建Request 对象。
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("X-CLIENT-CHECKSUM", "e7c1b02e46ee71129cfed68cee913ce5b0487bb8")
                .url(CommonData.HTTP_HOST + CommonData.OAUTH2)
                .post(getLoginParams())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("11111111111", "获取数据失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("11111111111", "数据请求结果：" + response.body().string());
            }
        });
    }


    private RequestBody getLoginParams() {
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("login_name", "t9");//传递键值对参数
        formBody.add("grant_type", "password");//传递键值对参数
        String nonce = AESUtil.getRawKey();
        formBody.add("nonce", nonce);//传递键值对参数
        formBody.add("password", AESUtil.encrypt(nonce, "111111"));//传递键值对参数
        formBody.add("client_id", "2");//传递键值对参数
        formBody.add("include_user", "true");//传递键值对参数
        formBody.add("device_uuid", "997f69ff8031e1dc8b72890d962e838b50d89faf");//传递键值对参数
        formBody.add("mqtt", "true");//传递键值对参数
        return formBody.build();
    }
}
