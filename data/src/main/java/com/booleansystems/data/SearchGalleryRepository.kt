package com.booleansystems.data

import com.booleansystems.data.common.IBaseResultListener

import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.domain.response.GalleryImage

/**

Created by oscar on 27/04/19
operez@na-at.com.mx
 */
class SearchGalleryRepository(val searchGalleryDataSource: SearchGalleryDataSource) {

    fun executeSearch(page: Short, query: String, result: IBaseResultListener<BaseResponse<GalleryImage>>) {
        searchGalleryDataSource.executeSearch(page, query, result)
    }

    interface SearchGalleryDataSource {
        fun executeSearch(
            page: Short,
            query: String,
            result: IBaseResultListener<BaseResponse<GalleryImage>>
        )
    }

}