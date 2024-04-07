package com.xnjz.one.http3


data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)
