package com.xnjz.one.http2;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * https://blog.csdn.net/liujichangdeboke/article/details/125441568
 *
 */
public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient = null;

    //Call this method in the Application class. ---> onCreate() method.
    //Thus we can get only one instance of httpClient in the whole app.
    public static void init() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(RxUtils.createSSLSocketFactory(), new RxUtils.TrustAllManager())
                    .hostnameVerifier(new RxUtils.TrustAllHostnameVerifier());
            mOkHttpClient = builder.build();
        }
    }


    //If GET method needs some other params, just need to add a HaspMap. Refer to:https://www.imooc.com/video/18685
    public static void get(String url, OkHttpCallBack okHttpCallback) {
        Call call;

        try {
            Request request = new Request.Builder().url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void post(String url, OkHttpCallBack okHttpCallback, HashMap<String, String> bodyMap) {
        Call call;
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (HashMap.Entry<String, String> entry : bodyMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody body = builder.build();
            Request.Builder builderRequest = new Request.Builder();
//            builderRequest.headers(new Headers())
            Request request = builderRequest.post(body).url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void postHasFile(String url, OkHttpCallBack okHttpCallback, HashMap<String, String> bodyMap, String filesKey, List<File> files) {
        Call call;
        try {

            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);
            for (HashMap.Entry<String, String> entry : bodyMap.entrySet()) {
                multipartBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            if (files != null) {
                for (File file : files) {
                    multipartBodyBuilder.addFormDataPart(filesKey, file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
                }
            }
            RequestBody body = multipartBodyBuilder.build();
            Request.Builder builderRequest = new Request.Builder();
//            builderRequest.headers(new Headers())
            Request request = builderRequest.post(body).url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallback);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
