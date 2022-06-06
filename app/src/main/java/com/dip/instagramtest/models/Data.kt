package com.dip.instagramtest.models

import com.google.gson.annotations.SerializedName

data class Data(
    val id: String,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("media_url")
    var mediaUrl: String
)