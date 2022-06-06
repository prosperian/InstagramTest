package com.dip.instagramtest.api

import com.dip.instagramtest.models.*
import com.dip.instagramtest.utils.Utils
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST
    suspend fun exchangeCode(
        @Url exchangeUrl: String,
        @Field("client_id") clientId: String = Utils.CLIENT_ID,
        @Field("client_secret") clientSecret: String = Utils.SECRET_KEY,
        @Field("grant_type") grandType: String = Utils.GRANT_TYPE,
        @Field("redirect_uri") redirectUri: String = Utils.REDIRECT_URI,
        @Field("code", encoded = true) code: String
    ): Response<Exchange>

    @GET("me")
    suspend fun getUserInfo(
        @Query("fields") fields: String = Utils.USER_INFO_FIELDS,
        @Query("access_token", encoded = true) accessToken: String
    ): Response<User>

    @GET("{user_id}")
    suspend fun getUserMediaList(
        @Path("user_id", encoded = true) userId: String,
        @Query("fields") fields: String = Utils.USER_MEDIA_FIELDS,
        @Query("access_token", encoded = true) accessToken: String
    ): Response<MediaItem>

    @GET("{media_id}")
    suspend fun getMediaDetail(
        @Path("media_id", encoded = true) mediaId: String,
        @Query("fields") fields: String = Utils.MEDIA_DETAIL_FIELDS,
        @Query("access_token", encoded = true) accessToken: String
    ): Response<Data>
}