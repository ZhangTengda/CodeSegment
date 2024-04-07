package com.xnjz.one.http3.base

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>>> or Call<NetworkResponse<>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != NetWorkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as NetWorkResponse"
        }
        return object : CallAdapter<Any, Call<*>?> {
            override fun responseType(): Type {
                return responseType
            }

            override fun adapt(call: Call<Any>): Call<*> {
                return NetWorkResponseCall(call)
            }

        }
    }
}