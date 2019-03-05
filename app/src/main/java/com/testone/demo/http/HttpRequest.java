package com.testone.demo.http;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();

    public static void okhttpGet(String url, final CallListener callListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callListener.onSuccess(response.body().string());
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }


    public static void okhttpPost(String url, final CallListener callListener) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                callListener.onSuccess(response.body().string());
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public static void okhttpPostFile(String url, final CallListener callListener) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File("test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, file))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                callListener.onSuccess(response.body().string());
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public static void okhttpPostParams(String url, final CallListener callListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }


    /**
     *
     *   OkHttp的拦截器链可谓是其整个框架的精髓，用户可传入的 interceptor 分为两类：
     *      ①一类是全局的 interceptor，该类 interceptor 在整个拦截器链中最早被调用，
     *      通过 OkHttpClient.Builder#addInterceptor(Interceptor) 传入；
     *      ②另外一类是非网页请求的 interceptor ，这类拦截器只会在非网页请求中被调用，
     *      并且是在组装完请求之后，真正发起网络请求前被调用，所有的 interceptor
     *      被保存在 List<Interceptor> interceptors 集合中，按照添加顺序来逐个调用，
     *      具体可参考 RealCall#getResponseWithInterceptorChain() 方法。
     *      通过 OkHttpClient.Builder#addNetworkInterceptor(Interceptor) 传入；
     */




}
