package com.testone.demo.http;

import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by roger on 2017/4/5.
 */

public interface HttpApi {


//    @GET("{jiekou}?source=android&appVersionInfo=v2.15.0&lang=zh_CN")
//    Call<String> getChopeApi(@Header("Authorization") String basic,
//                             @Path("jiekou") String jiekou);


//    @GET("{jiekou}?source=android&appVersionInfo=v2.15.0&lang=zh_CN")
//    Observable<String> getChopeApi(@Header("Authorization") String basic,
//                                   @Path("jiekou") String jiekou);

    @GET("{jiekou}")
    Observable<String> getChopeApi(@Header("Authorization") String basic,
                                   @Path("jiekou") String jiekou,
                                   @QueryMap() Map<String, String> map);



//    String basic = "Basic " + Base64.encodeToString(("test:5QeJsWHMx3").getBytes(), Base64.NO_WRAP);
//
//    @GET("/{jiekou}/{jiekou2}?source=android&appVersionInfo=v2.15.0&lang=zh_CN")
//    @Header("Basic " + Base64.encodeToString(("test:5QeJsWHMx3").getBytes(), Base64.NO_WRAP))
//
//    Call<String> getChopeApi(@Header("Authorization") String basic,
//                             @Path("jiekou") String jiekou,
//                             @Path("jiekou2") String jiekou2,
//                             @Path("value1") String value);


}
