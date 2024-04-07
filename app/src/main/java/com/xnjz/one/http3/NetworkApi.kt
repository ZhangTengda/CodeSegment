package com.xnjz.one.http3

import com.blankj.utilcode.util.LogUtils
import com.xnjz.one.http3.base.BaseService
import com.xnjz.one.http3.base.INetworkRequiredInfo
import com.xnjz.one.http3.base.NetworkResponseAdapterFactory
import com.xnjz.one.http3.commoninterceptor.CommonRequestInterceptor
import com.xnjz.one.http3.commoninterceptor.CommonResponseInterceptor
import com.xnjz.one.http3.commoninterceptor.HttpBaseParamsLoggingInterceptor
import com.xnjz.one.http3.converter.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class NetworkApi {
    companion object {
        val BASE_URL: String = "https://www.wanandroid.com/"

        private var iNetWorkRequiredInfo: com.xnjz.one.http3.base.INetworkRequiredInfo? = null

        fun init(networkRequiredInfo: com.xnjz.one.http3.base.INetworkRequiredInfo) {
            iNetWorkRequiredInfo = networkRequiredInfo;
        }

        /**Retrofit*/
        fun <T : BaseService> getApiService(serviceClass: Class<T>, baseUrl: String? = null): T {
//            return Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com")
//                .client(getOkHttpClient())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
////                .addCallAdapterFactory(NetworkResponseAdapterFactory())
////                .addCallAdapterFactory(NewNetworkResponseAdapterFactory())
////                .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(serviceClass)

            return Retrofit.Builder()
                .client(getOkHttpClient())
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl ?: BASE_URL)
                .build()
                .create(serviceClass)
        }


        private fun getOkHttpClient(): OkHttpClient {

            val basicParamsInterceptor: com.xnjz.one.http3.commoninterceptor.HttpBaseParamsLoggingInterceptor =
                com.xnjz.one.http3.commoninterceptor.HttpBaseParamsLoggingInterceptor.Builder()
                    .addHeaderLine("hello:hello")
                    .addQueryParam("deviceId", "12345")
                    .build()

            //手动创建一个OkHttpClient并设置超时时间缓存等设置
            val okHttpClientBuilder = OkHttpClient.Builder()
//            okHttpClientBuilder.addInterceptor(basicParamsInterceptor)
            okHttpClientBuilder.addInterceptor(
                com.xnjz.one.http3.commoninterceptor.CommonRequestInterceptor(
                    iNetWorkRequiredInfo
                )
            )
            okHttpClientBuilder.addInterceptor(com.xnjz.one.http3.commoninterceptor.CommonResponseInterceptor())
            if (iNetWorkRequiredInfo != null && iNetWorkRequiredInfo!!.isDebug) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()

                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

                LogUtils.d("iNetWorkRequiredInfo is not Null")
            } else {
                LogUtils.d("iNetWorkRequiredInfo   is Null")
//                throw java.lang.Exception("init Api")
            }
            return okHttpClientBuilder.build()
        }
    }


}