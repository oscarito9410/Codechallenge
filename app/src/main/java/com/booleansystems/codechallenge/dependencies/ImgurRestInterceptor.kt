package com.booleansystems.codechallenge.dependencies

import com.booleansystems.codechallenge.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException

/**
 * Created by oscar on 03/05/19
 * operez@na-at.com.mx
 */
class ImgurRestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(getRequestBuilder(original).build())
    }

    private fun getRequestBuilder(original: Request): Request.Builder {
        val requestBuilder = original.newBuilder()
        requestBuilder.addHeader(
            Constants.HttpConfig.CLIENT_ID_HEADER_NAME,
            Constants.HttpConfig.CLIENT_ID_HEADER_VALUE
        )
        return requestBuilder
    }

}
