package com.xnjz.one.http3.commoninterceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Retrofit 2.0+okHttp3.9添加公共参数【史上支持最全get、post（Filed、Part、PartMap）】
 * <p>
 * https://www.jianshu.com/p/f77d379ebcfa
 * <p>
 * 备用
 * https://www.cnblogs.com/mesmerize/p/16067955.html
 */
public class CommonParamsInterceptor implements Interceptor {

    private static final String TAG = CommonParamsInterceptor.class.getSimpleName();

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuild = null;
        String method = oldRequest.method();
        String postBodyString = "";
        if ("POST".equals(method)) {
            RequestBody oldBody = oldRequest.body();
            if (oldBody instanceof FormBody) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("deviceOs", iCommon.DEVICE_OS);
//                formBodyBuilder.add("appVersion", Utils.instance().getAppVersionName());
//                formBodyBuilder.add("appName", Utils.instance().getAppNameNew());
                newRequestBuild = oldRequest.newBuilder();

                RequestBody formBody = formBodyBuilder.build();
                postBodyString = bodyToString(oldRequest.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            } else if (oldBody instanceof MultipartBody) {
                MultipartBody oldBodyMultipart = (MultipartBody) oldBody;
                List<MultipartBody.Part> oldPartList = oldBodyMultipart.parts();
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
//                RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), iCommon.DEVICE_OS);
//                RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), Utils.instance().getAppNameNew());
//                RequestBody requestBody3 = RequestBody.create(MediaType.parse("text/plain"), Utils.instance().getAppVersionName());
                for (MultipartBody.Part part : oldPartList) {
                    builder.addPart(part);
                    postBodyString += (bodyToString(part.body()) + "\n");
                }
//                postBodyString += (bodyToString(requestBody1) + "\n");
//                postBodyString += (bodyToString(requestBody2) + "\n");
//                postBodyString += (bodyToString(requestBody3) + "\n");
//              builder.addPart(oldBody);  //todo 不能用这个方法，因为不知道oldBody的类型，可能是PartMap过来的，也可能是多个Part过来的，所以需要重新逐个加载进去

//                builder.addPart(requestBody1);
//                builder.addPart(requestBody2);
//                builder.addPart(requestBody3);
                newRequestBuild = oldRequest.newBuilder();
                newRequestBuild.post(builder.build());
                Log.e(TAG, "MultipartBody," + oldRequest.url());
            } else {
                newRequestBuild = oldRequest.newBuilder();
            }
        } else {
            // 添加新的参数
            HttpUrl.Builder commonParamsUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
//                    .addQueryParameter("deviceOs", iCommon.DEVICE_OS)
//                    .addQueryParameter("appVersion", Utils.instance().getAppVersionName())
//                    .addQueryParameter("appName", Utils.instance().getAppNameNew());
            newRequestBuild = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(commonParamsUrlBuilder.build());
        }
        Request newRequest = newRequestBuild
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "zh")
                .build();

        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(newRequest);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        int httpStatus = response.code();
        StringBuilder logSB = new StringBuilder();
        logSB.append("-------start:" + method + "|");
        logSB.append(newRequest.toString() + "\n|");
        logSB.append(method.equalsIgnoreCase("POST") ? "post参数{" + postBodyString + "}\n|" : "");
        logSB.append("httpCode=" + httpStatus + ";Response:" + content + "\n|");
        logSB.append("----------End:" + duration + "毫秒----------");
        Log.d(TAG, logSB.toString());

        // 因为okhttp的response.body().string();只能读取一次，所以需要重新构建
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
