package com.dip.instagramtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dip.instagramtest.api.Resource
import com.dip.instagramtest.api.ResponseHandler
import com.dip.instagramtest.models.Data
import com.dip.instagramtest.models.Media
import com.dip.instagramtest.models.MediaItem
import com.dip.instagramtest.repositories.NetworkRepository
import com.dip.instagramtest.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _userMediaList: SingleLiveEvent<Resource<MediaItem>> = SingleLiveEvent()
    val userMediaList get() = _userMediaList

    private val _mediaDetail: SingleLiveEvent<Resource<Data>> = SingleLiveEvent()
    val mediaDetail get() = _mediaDetail

    fun getUserMediaList(accessToken: String, userId: String) = viewModelScope.launch {
        _userMediaList.postValue(Resource.Loading())
        val response = networkRepository.getUserMediaList(accessToken, userId)
        _userMediaList.postValue(ResponseHandler.handleResponse(response))
    }

    fun requestAllMediaData(accessToken: String) {
        _userMediaList.value?.data?.media?.data?.forEach {
            getMediaDetail(accessToken, it.id)
        }
    }

    private fun getMediaDetail(accessToken: String, mediaId: String) = viewModelScope.launch {
        _mediaDetail.postValue(Resource.Loading())
        val response = networkRepository.getMediaDetail(accessToken, mediaId)
        _mediaDetail.postValue(ResponseHandler.handleResponse(response))
    }


}