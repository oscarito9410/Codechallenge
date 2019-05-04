package com.booleansystems.codechallenge.ui.home.mapper

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */

import com.booleansystems.domain.response.GalleryImage as DomainGalleryImage
import com.booleansystems.domain.response.Images as DomainImagen

data class SingleImage(val link: String?, val format: String?)

data class GalleryImage(
    val title: String?, val description: String?,
    val ups: String?, val downs: String?,
    val points: String?,
    val images: List<SingleImage>?
)

fun DomainImagen.toPresentationModel(): SingleImage = SingleImage(link, type)

fun DomainGalleryImage.toPresentationModel(): GalleryImage =
    GalleryImage(
        "$title!!", "oaoaoa", "$ups!!",
        "$downs!!", "$points!!",
         if(images!=null) images!!.map { it.toPresentationModel() }.toList() else emptyList()
    )

