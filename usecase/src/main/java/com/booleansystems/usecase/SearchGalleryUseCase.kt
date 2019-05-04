package com.booleansystems.usecase

import com.booleansystems.data.SearchGalleryRepository
import com.booleansystems.data.common.IBaseResultListener
import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.domain.response.GalleryImage

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
class SearchGalleryUseCase(val searchGalleryRepository: SearchGalleryRepository) {
    operator fun invoke(page: Short, query: String, result: IBaseResultListener<BaseResponse<GalleryImage>>) =
        searchGalleryRepository.executeSearch(page, query, result)
}