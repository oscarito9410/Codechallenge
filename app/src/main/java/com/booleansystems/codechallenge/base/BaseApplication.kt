package com.booleansystems.codechallenge.base

import android.app.Application
import com.booleansystems.codechallenge.di.ApplicationModule
import com.booleansystems.codechallenge.di.NetModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

/**
 * Created by oscar on 03/05/19
 * operez@na-at.com.mx
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val moduleList =
            listOf(ApplicationModule, NetModule)
        startKoin(this, moduleList)
        Stetho.initializeWithDefaults(this)

    }

}