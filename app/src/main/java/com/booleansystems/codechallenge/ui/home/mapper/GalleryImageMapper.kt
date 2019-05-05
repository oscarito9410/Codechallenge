package com.booleansystems.codechallenge.ui.home.mapper

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */

import android.os.Parcel
import android.os.Parcelable
import com.booleansystems.domain.response.GalleryImage as DomainGalleryImage
import com.booleansystems.domain.response.Images as DomainImagen

data class SingleImage(
    val link: String?, val format: String?,
    val nsfw: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(link)
        parcel.writeString(format)
        parcel.writeValue(nsfw)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleImage> {
        override fun createFromParcel(parcel: Parcel): SingleImage {
            return SingleImage(parcel)
        }

        override fun newArray(size: Int): Array<SingleImage?> {
            return arrayOfNulls(size)
        }
    }
}

fun SingleImage.isVideoFormat(): Boolean = format.equals("video/mp4")

data class GalleryImage(
    val title: String?, val description: String?,
    val ups: String?, val downs: String?,
    val points: String?,
    val images: List<SingleImage>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(SingleImage)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(ups)
        parcel.writeString(downs)
        parcel.writeString(points)
        parcel.writeTypedList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryImage> {
        override fun createFromParcel(parcel: Parcel): GalleryImage {
            return GalleryImage(parcel)
        }

        override fun newArray(size: Int): Array<GalleryImage?> {
            return arrayOfNulls(size)
        }
    }
}


fun DomainImagen.toPresentationModel(): SingleImage = SingleImage(
    link, type, if (nsfw.isNullOrEmpty()) false else nsfw!!.toBoolean()
)

fun DomainGalleryImage.toPresentationModel(): GalleryImage =
    GalleryImage(
        "$title", "$description", "$ups",
        "$downs", "$points",
        if (images != null) images!!.map { it.toPresentationModel() }.toList() else emptyList()
    )

