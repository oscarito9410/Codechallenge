package com.booleansystems.codechallenge.ui.home.datasource

import com.booleansystems.codechallenge.dependencies.rest.ImgurRestEndpoints
import com.booleansystems.codechallenge.utils.Constants
import com.booleansystems.data.common.IBaseResultListener
import com.booleansystems.data.search.SearchGalleryRepository
import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.domain.response.GalleryImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
class SearchGalleryDataSourceImpl(val endpoints: ImgurRestEndpoints) : SearchGalleryRepository.SearchGalleryDataSource {

    var mDisaposable: Disposable? = null

    override fun executeSearch(page: Int, query: String, result: IBaseResultListener<BaseResponse<GalleryImage>>) {
        mDisaposable = endpoints.search(Constants.HttpConfig.DEFAULT_SORT_TYPE, page, query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe({
                result.onSuccess(it)
                mDisaposable!!.dispose()

            }, {
                result.onError(it)
                mDisaposable!!.dispose()
            })

    }


}