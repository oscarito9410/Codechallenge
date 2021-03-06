package com.booleansystems.domain.response

import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("name") val name: String?,
    @SerializedName("display_name") val display_name: String?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("total_items") val total_items: Int?,
    @SerializedName("following") val following: Boolean?,
    @SerializedName("background_hash") val background_hash: String?,
    @SerializedName("thumbnail_hash") val thumbnail_hash: String?,
    @SerializedName("accent") val accent: String?,
    @SerializedName("background_is_animated") val background_is_animated: Boolean?,
    @SerializedName("thumbnail_is_animated") val thumbnail_is_animated: Boolean?,
    @SerializedName("is_promoted") val is_promoted: Boolean?,
    @SerializedName("description") val description: String?,
    @SerializedName("logo_hash") val logo_hash: String?,
    @SerializedName("logo_destination_url") val logo_destination_url: String?
)