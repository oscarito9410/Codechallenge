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
import com.booleansystems.usecase.search.SearchGalleryUseCase
import com.booleansystems.domain.response.GalleryImage as DomainGalleryImage


/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
open class SearchGalleryViewModel(private val searchGalleryUseCase: SearchGalleryUseCase) : ViewModel(),
    IBaseResultListener<BaseResponse<DomainGalleryImage>> {

    val mSearchResultList = MutableLiveData<MutableList<GalleryImage>>()

    var mCurrentPage: Int = 0

    var mLastQuery: String = ""

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

    fun startSearchGallery(isConnectInternet: Boolean, restart: Boolean, query: String) {

        when (isConnectInternet) {
            true -> {
                //When query is not empty
                if (!query.isEmpty())
                    validateSearch(restart, query)
                //When user clean searchview text but last query keeps in local variable
                else if (query.isEmpty() && !mLastQuery.isEmpty() && !restart)
                    validateSearch(restart, mLastQuery)
                else
                    sendEventEmptyQuery()
            }
            else -> sendEventNotInternetAvailable()
        }
    }


    open fun validateSearch(restart: Boolean, query: String) {
        mIsLoading.postValue(true)
        increasePage(restart)
        mRestartSearch.postValue(restart)
        mLastQuery = query
        searchGalleryUseCase.invoke(mCurrentPage, query, this)
    }

    open fun increasePage(needsRestart: Boolean) {
        if (needsRestart) mCurrentPage = 0 else mCurrentPage++
    }

    open fun dicreasePage() {
        if (mCurrentPage > 0) mCurrentPage--
    }

    open fun sendEventEmptyQuery() {
        mErrorEvent.postValue(R.string.error_empty_query)
    }

    open fun sendEventNotInternetAvailable() {
        mErrorEvent.postValue(R.string.error_no_internet_connection_found)
    }

    override fun onSuccess(response: BaseResponse<DomainGalleryImage>) {
        if (!response.data.isEmpty()) {
            filterListItems(response.data)
            mIsLoading.postValue(false)
        } else {
            dicreasePage()
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