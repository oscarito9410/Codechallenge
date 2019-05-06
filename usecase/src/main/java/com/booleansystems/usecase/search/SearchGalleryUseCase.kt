package com.booleansystems.usecase.search

import com.booleansystems.data.common.IBaseResultListener
import com.booleansystems.data.search.SearchGalleryRepository
import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.domain.response.GalleryImage

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
open class SearchGalleryUseCase(val searchGalleryRepository: SearchGalleryRepository) {
    operator fun invoke(page: Int, query: String, result: IBaseResultListener<BaseResponse<GalleryImage>>) =
        searchGalleryRepository.executeSearch(page, query, result)
}