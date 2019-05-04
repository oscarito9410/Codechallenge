package com.booleansystems.domain.common

import com.google.gson.annotations.SerializedName

/**

Created by oscar on 03/05/19
operez@na-at.com.mx
 */
data class BaseResponse<T>(
    @SerializedName("data") var data: List<T>,
    @SerializedName("success") var success: Boolean,
    @SerializedName("status") var status: Int
)