package com.booleansystems.codechallenge.dependencies.rest

import com.booleansystems.domain.common.BaseResponse
import com.booleansystems.domain.response.GalleryImage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**

Created by oscar on 03/05/19
operez@na-at.com.mx
 */
interface ImgurRestEndpoints {
    @GET("gallery/search/{sort}/{page}")
    fun search(
        @Path("sort") sort: String, @Path("page")
        page: Int, @Query("q") query: String
    ): Observable<BaseResponse<GalleryImage>>
}
