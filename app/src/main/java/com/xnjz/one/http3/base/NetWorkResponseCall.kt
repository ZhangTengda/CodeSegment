package com.xnjz.one.http3.base

import com.xnjz.one.http3.bean.NewsChannelsBean
import com.xnjz.one.http3.util.MoshiHelper
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetWorkResponseCall<S : Any>(private val call: Call<S>) : Call<NetWorkResponse<S>> {
    override fun clone(): Call<NetWorkResponse<S>> {
        return NetWorkResponseCall(call.clone())
    }

    override fun execute(): Response<NetWorkResponse<S>> {
        throw UnsupportedOperationException("NetWorkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<NetWorkResponse<S>>) {
        return call.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val errorBody = response.errorBody()


                if (response.isSuccessful) {
                    // [200..300]
                    if (body != null) {
                        callback.onResponse(
                            this@NetWorkResponseCall,
                            Response.success(NetWorkResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetWorkResponseCall,
                            Response.success(NetWorkResponse.UnknownError(null))
                        )
                    }
                } else {
                    // 500 Exception
                    if (errorBody != null && errorBody.contentLength() > 0) {
                        val errorResponse =
                            MoshiHelper.fromJson<NewsChannelsBean>(errorBody.string())


                        callback.onResponse(
                            this@NetWorkResponseCall,
                            Response.success(
                                NetWorkResponse.ApiError(
                                    errorResponse?.showApiResError ?: "",
                                    errorResponse?.showApiResCode ?: -1
                                )
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@NetWorkResponseCall,
                            Response.success(
                                (NetWorkResponse.NetWorkError(
                                    errorBody?.string() ?: "Message is empty.", code
                                ))
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val netWorkResponse = when (throwable) {
                    is IOException -> { // 网络错误
                        NetWorkResponse.NetWorkError(throwable.message.toString(), 400)
                    }
                    else -> NetWorkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@NetWorkResponseCall, Response.success(netWorkResponse))
            }
        }
        )
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        return call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}