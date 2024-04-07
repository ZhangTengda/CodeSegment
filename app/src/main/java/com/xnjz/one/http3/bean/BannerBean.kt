package com.xnjz.myapplication.http3

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class BannerBean(
    val errorMsg: String?,
    val errorCode: Int,
    val data: BannerDataBean?,
)


//@JsonClass(generateAdapter = true)
data class BannerDataBean(
//    @Json(name = "imagePath")
    val imagePath: String? = null,
//    @Json(name = "imagePath")
    val desc: String? = null,
    val title: String? = null,
    val url: String? = null,
)