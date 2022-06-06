package com.dip.instagramtest.repositories

import androidx.annotation.WorkerThread
import com.dip.instagramtest.api.ApiService
import com.dip.instagramtest.models.*
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    @WorkerThread
    suspend fun exchangeCode(url: String, code: String): Response<Exchange> {
        return apiService.exchangeCode(url, code = code)
    }

    @WorkerThread
    suspend fun getUserInfo(accessToken: String): Response<User> {
        return apiService.getUserInfo(accessToken = accessToken)
    }

    @WorkerThread
    suspend fun getUserMediaList(accessToken: String, userId: String): Response<MediaItem> {
        return apiService.getUserMediaList(userId = userId, accessToken = accessToken)
    }

    @WorkerThread
    suspend fun getMediaDetail(accessToken: String, mediaId: String): Response<Data> {
        return apiService.getMediaDetail(mediaId = mediaId, accessToken = accessToken)
    }
}