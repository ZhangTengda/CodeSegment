package com.xnjz.one.http3.adapter

/**
 * 用于配置全局的异常处理逻辑
 */
interface ErrorHandler {

    /**
     * 业务错误
     */
    fun bizError(errorCode: Int, errorMsg: String)

    /**
     * 其他错误
     */
    fun otherError(throwable: Throwable)
}