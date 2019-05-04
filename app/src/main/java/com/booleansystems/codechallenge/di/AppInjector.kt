package com.booleansystems.codechallenge.di

import com.booleansystems.codechallenge.dependencies.ImgurRestInterceptor
import com.booleansystems.codechallenge.dependencies.ImgurRestEndpoints
import com.booleansystems.codechallenge.ui.home.datasource.SearchGalleryDataSourceImpl
import com.booleansystems.codechallenge.ui.home.viewmodel.SearchGalleryViewModel
import com.booleansystems.codechallenge.utils.Constants
import com.booleansystems.data.SearchGalleryRepository
import com.booleansystems.usecase.SearchGalleryUseCase
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**

Created by oscar on 03/05/19
operez@na-at.com.mx
 */

val ApplicationModule = module(definition = {

    factory {
        val endpoints: ImgurRestEndpoints = get()
        return@factory SearchGalleryDataSourceImpl(endpoints)
    }

    factory {
        val remoteDataSource: SearchGalleryDataSourceImpl = get()
        return@factory SearchGalleryRepository(remoteDataSource)
    }
    factory {
        val searchGalleryUseCase = SearchGalleryUseCase(get())
        return@factory searchGalleryUseCase
    }

    viewModel { SearchGalleryViewModel(get()) }

})


val NetModule = module {


    factory {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return@factory loggingInterceptor
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.HttpConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        ImgurRestInterceptor()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ImgurRestInterceptor>())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    single { get<Retrofit>().create(ImgurRestEndpoints::class.java) }
}
