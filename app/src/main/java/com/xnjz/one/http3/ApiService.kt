package com.xnjz.one.http3

import com.xnjz.one.http3.adapter.NetworkResponse
import com.xnjz.one.http3.base.BaseService
import com.xnjz.one.http3.base.NetWorkResponse
import com.xnjz.one.http3.bean.Banner
import com.xnjz.one.http3.bean.BannerBean2
import com.xnjz.one.http3.bean.NewsChannelsBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService : BaseService {

    @FormUrlEncoded
    @POST
    suspend fun loginAction(
        @Field("service") service: String,
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("type") type: String
    ): NetWorkResponse<NewsChannelsBean>


//    @GET("/banner/json")
//    suspend fun getBanner(): WanResponse<List<BannerBean2.BannerDataBean2>>


    @GET("/banner/json")
    suspend fun getBannerNew(): NetworkResponse<List<com.xnjz.one.http3.bean.BannerBean2.BannerDataBean2>>
//    suspend fun getBannerNew(): NetworkResponse<List<Banner>>
//        suspend fun getBannerNew(): Deferred<List<BannerBean2.BannerDataBean2>>

//    suspend fun getBannerNew(): NetWorkResponse<List<BannerBean2.BannerDataBean2>>
}