package com.dip.instagramtest.utils

object Utils {

    const val AUTHENTICATION_URL = "https://api.instagram.com/oauth/"
    const val CODE_SUB_URL = "authorize"
    const val EXCHANGE_SUB_URL = "access_token"
    const val GRANT_TYPE = "authorization_code"
    const val BASE_URL = "https://graph.instagram.com/v14.0/"
    const val CLIENT_ID = "813826512916559"
    const val SECRET_KEY = "8cc8491dc1c73ce1d6641c97ae44da81"
    const val REDIRECT_URI = "https://prosperian.github.io/"
    const val AUTHENTICATION_SCOPE = "user_profile,user_media"
    const val RESPONSE_TYPE = "code"

    const val USER_INFO_FIELDS = "id,username"
    const val USER_MEDIA_FIELDS = "media"
    const val MEDIA_DETAIL_FIELDS = "id,media_type,media_url"

    const val IMAGE = "IMAGE"
}