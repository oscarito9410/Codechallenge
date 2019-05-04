package com.booleansystems.domain.response

import com.google.gson.annotations.SerializedName

data class Images(

    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("datetime") val datetime: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("animated") val animated: Boolean?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("size") val size: Int?,
    @SerializedName("views") val views: Int?,
    @SerializedName("bandwidth") val bandwidth: String?,
    @SerializedName("vote") val vote: String?,
    @SerializedName("favorite") val favorite: Boolean?,
    @SerializedName("nsfw") val nsfw: String?,
    @SerializedName("section") val section: String?,
    @SerializedName("account_url") val account_url: String?,
    @SerializedName("account_id") val account_id: String?,
    @SerializedName("is_ad") val is_ad: Boolean?,
    @SerializedName("in_most_viral") val in_most_viral: Boolean?,
    @SerializedName("has_sound") val has_sound: Boolean?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("ad_type") val ad_type: Int?,
    @SerializedName("ad_url") val ad_url: String?,
    @SerializedName("edited") val edited: Int?,
    @SerializedName("in_gallery") val in_gallery: Boolean?,
    @SerializedName("link") val link: String?,
    @SerializedName("comment_count") val comment_count: String?,
    @SerializedName("favorite_count") val favorite_count: String?,
    @SerializedName("ups") val ups: String?,
    @SerializedName("downs") val downs: String?,
    @SerializedName("points") val points: String?,
    @SerializedName("score") val score: String?
)