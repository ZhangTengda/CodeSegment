package com.xnjz.one.http3.base

sealed class NetWorkResponse<out T : Any> {
    // 请求成功
    data class Success<T : Any>(val data: T) : NetWorkResponse<T>()

    // Api 错误
    data class ApiError(val data: Any, val errorCode: Int) : NetWorkResponse<Nothing>()

    // 网络错误
    data class NetWorkError(val errorMsg: String, val errorCode: Int) : NetWorkResponse<Nothing>()

    //未知错误
    data class UnknownError(val error: Throwable?) : NetWorkResponse<Nothing>()
}