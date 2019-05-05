package com.booleansystems.codechallenge.utils.extentions

import android.content.Context
import android.net.ConnectivityManager

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }