package com.xnjz.one.http1;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.xnjz.one.http1.Interceptor.NetCacheInterceptor;
import com.xnjz.one.http1.Interceptor.OfflineCacheInterceptor;
import com.xnjz.one.http1.builder.OkDownloadBuilder;
import com.xnjz.one.http1.builder.OkGetBuilder;
import com.xnjz.one.http1.builder.OkPostBuilder;
import com.xnjz.one.http1.builder.OkUploadBuilder;
import com.xnjz.one.http1.builder.OkDownloadBuilder;
import com.xnjz.one.http1.builder.OkGetBuilder;
import com.xnjz.one.http1.builder.OkPostBuilder;
import com.xnjz.one.http1.builder.OkUploadBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * 博客 https://blog.csdn.net/leol_2/article/details/98585624
 * Github  https://github.com/lihangleo2/EasyOk
 */
public class EasyOk {

    private OkHttpClient okHttpClient;
    //这个handler的作用是把子线程切换主线程。在后面接口中的具体实现，就不需要用handler去回调了
    private Handler mDelivery;
    //防止网络重复请求的tagList;
    private ArrayList<String> onesTag;

    private EasyOk() {
        onesTag = new ArrayList<>();
        mDelivery = new Handler(Looper.getMainLooper());
        okHttpClient = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), 50 * 1024 * 1024))
                .hostnameVerifier(new HostnameVerifier() {//证书信任
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                //这里是网上对cookie的封装 github : https://github.com/franmontiel/PersistentCookieJar
                //如果你的项目没有遇到cookie管理或者你想通过网络拦截自己存储，那么可以删除persistentcookiejar包
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext())))
                .addInterceptor(OfflineCacheInterceptor.getInstance())
                .addNetworkInterceptor(NetCacheInterceptor.getInstance())
                .build();
    }


    private static final class EasyOkHolder {
        static final EasyOk easyOk = new EasyOk();
    }

    public static EasyOk getInstance() {
        return EasyOkHolder.easyOk;
    }


    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Handler getmDelivery() {
        return mDelivery;
    }

    public ArrayList<String> getOnesTag() {
        return onesTag;
    }


    public static OkGetBuilder get() {
        return new OkGetBuilder();
    }

    public static OkPostBuilder post() {
        return new OkPostBuilder();
    }

    public static OkUploadBuilder upload() {
        return new OkUploadBuilder();
    }

    public static OkDownloadBuilder download() {
        return new OkDownloadBuilder();
    }

    //tag取消网络请求
    public void cancleOkhttpTag(String tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            //请求列表里的，取消网络请求
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            //正在请求网络的，取消网络请求
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
