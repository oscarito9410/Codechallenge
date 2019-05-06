package com.booleansystems.data.common

import com.booleansystems.domain.common.BaseResponse

/**
Created by oscar on 03/05/19
Notify is response is success or not */
interface IBaseResultListener<T : BaseResponse<*>> {
    open fun onSuccess(response: T);
    open fun onError(error: Throwable)
}