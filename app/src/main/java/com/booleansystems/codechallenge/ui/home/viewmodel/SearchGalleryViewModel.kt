package com.booleansystems.codechallenge.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.booleansystems.codechallenge.ui.home.mapper.toPresentationModel
import com.booleansystems.data.common.IBaseResultListener
import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.usecase.SearchGalleryUseCase
import com.booleansystems.domain.response.GalleryImage as DomainGalleryImage


/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
class SearchGalleryViewModel(private val searchGalleryUseCase: SearchGalleryUseCase) : ViewModel(),
    IBaseResultListener<BaseResponse<DomainGalleryImage>> {

    val mSearchResultList = MutableLiveData<MutableList<GalleryImage>>()

    var mCurrentPage = Int

    val mRestartSearch = MutableLiveData<Boolean>()

    init {
        mRestartSearch.postValue(true)
    }

    fun startSearchGallery(restart: Boolean, query: String) {
        //  if (restart)
        //    mCurrentPage = 0
        //else
        //  mCurrentPage= mCurrentPage

        mRestartSearch.postValue(restart)
        searchGalleryUseCase.invoke(0, query, this)
    }

    override fun onSuccess(response: BaseResponse<DomainGalleryImage>) {
        if (!response.data.isEmpty()) {
            val searchResult = response.data.map { it.toPresentationModel() }.toMutableList()
            mSearchResultList.postValue(searchResult)
        }
    }

    override fun onError(error: Throwable) {
        if (error != null) {

        }
    }
}