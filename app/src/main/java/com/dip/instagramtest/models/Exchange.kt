package com.dip.instagramtest.models

import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: String
)
