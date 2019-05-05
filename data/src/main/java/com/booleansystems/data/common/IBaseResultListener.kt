package com.booleansystems.data.common

import com.booleansystems.domain.common.BaseResponse

/**
Created by oscar on 03/05/19
Notify is response is success or not */
interface IBaseResultListener<T : BaseResponse<*>> {
    fun onSuccess(response: T);
    fun onError(error: Throwable)
}