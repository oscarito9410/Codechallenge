package com.booleansystems.codechallenge.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.booleansystems.codechallenge.R
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.booleansystems.codechallenge.ui.home.mapper.isVideoFormat
import com.booleansystems.codechallenge.ui.home.mapper.toPresentationModel
import com.booleansystems.codechallenge.utils.SingleLiveEvent
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

    var mCurrentPage: Int = 0

    val mRestartSearch = MutableLiveData<Boolean>()

    val mIsLoading = MutableLiveData<Boolean>()

    val mNotItemsFoundNewSearch = MutableLiveData<Boolean>()

    //Makes reference for send messages error defined in strings.xml
    val mErrorEvent = SingleLiveEvent<Int>()

    init {
        mRestartSearch.postValue(true)
        mNotItemsFoundNewSearch.postValue(true)
        mIsLoading.postValue(false)
    }

    fun startSearchGallery(restart: Boolean, query: String) {
        mIsLoading.postValue(true)
        if (restart) mCurrentPage = 0 else mCurrentPage++
        mRestartSearch.postValue(restart)
        searchGalleryUseCase.invoke(mCurrentPage, query, this)
    }

    override fun onSuccess(response: BaseResponse<DomainGalleryImage>) {
        if (!response.data.isEmpty()) {
            filterListItems(response.data)
            mIsLoading.postValue(false)
        } else {
            if (mCurrentPage > 0) mCurrentPage--
            mErrorEvent.postValue(R.string.error_no_data_found)
            mIsLoading.postValue(false)

            if (mRestartSearch.value!!)
                mNotItemsFoundNewSearch.postValue(true)

        }
    }

    private fun filterListItems(items: List<DomainGalleryImage>) {
        val searchResult = items.map { it.toPresentationModel() }
            .filter { item ->
                return@filter !item.images.isNullOrEmpty() && item.images.filter { imageItem ->
                    imageItem.isVideoFormat() && !imageItem.nsfw!!
                }.isNullOrEmpty()
            }.toMutableList()
        mSearchResultList.postValue(searchResult)
    }

    override fun onError(error: Throwable) {
        mErrorEvent.postValue(R.string.error_unhandler_message)
    }
}