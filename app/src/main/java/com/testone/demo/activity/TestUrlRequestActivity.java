package com.testone.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.testone.demo.MyApplication;
import com.testone.demo.R;
import com.testone.demo.bean.LoginApp;
import com.testone.demo.http.HttpApi;
import com.testone.demo.http.StringConverterFactory;
import com.testone.demo.utils.ApiUtils;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class TestUrlRequestActivity extends BaseActivity {


    private TextView showTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_urlrequest);

        LoginApp app = new LoginApp(MyApplication.getInstance());
        app.setCountryCode("HK");
        app.setI18Language("en_US");


        findViewById(R.id.request_data_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });

        showTextView = findViewById(R.id.main_text);
    }

    private void requestData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.chope.cc/restaurants/")
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        HttpApi httpApi = retrofit.create(HttpApi.class);
        String basic = "Basic " + Base64.encodeToString(("test:5QeJsWHMx3").getBytes(), Base64.NO_WRAP);
        Map<String, String> paramsMap = ApiUtils.getNecessaryParams();
        httpApi.getChopeApi(basic, "get_cities", paramsMap)
                .subscribeOn(Schedulers.io()) // 请求时的线程
                .observeOn(AndroidSchedulers.mainThread()) // 请求完的线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(final String s) {
                        showTextView.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
