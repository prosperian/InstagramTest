package com.dip.instagramtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dip.instagramtest.models.Exchange
import com.dip.instagramtest.api.Resource
import com.dip.instagramtest.api.ResponseHandler
import com.dip.instagramtest.models.User
import com.dip.instagramtest.repositories.NetworkRepository
import com.dip.instagramtest.utils.SingleLiveEvent
import com.dip.instagramtest.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {


    private val _accessToken: SingleLiveEvent<Resource<Exchange>> = SingleLiveEvent()
    val accessToken get() = _accessToken

    private val _userInfo: SingleLiveEvent<Resource<User>> = SingleLiveEvent()
    val userInfo get() = _userInfo

    fun getUserInfo(accessToken: String) = viewModelScope.launch {
        userInfo.postValue(Resource.Loading())
        val response = networkRepository.getUserInfo(accessToken)
        userInfo.postValue(ResponseHandler.handleResponse(response))
    }


    fun sendExchangeRequest(code: String) = viewModelScope.launch {
        _accessToken.postValue(Resource.Loading())
        val response = networkRepository.exchangeCode(getExchangeUrl(), code)
        _accessToken.postValue(ResponseHandler.handleResponse(response))
    }


    fun getAuthenticationUrl(): String {
        return Utils.AUTHENTICATION_URL + Utils.CODE_SUB_URL + "?client_id=" + Utils.CLIENT_ID +
                "&client_secret=" + Utils.SECRET_KEY + "&redirect_uri=" + Utils.REDIRECT_URI +
                "&scope=" + Utils.AUTHENTICATION_SCOPE + "&response_type=" + Utils.RESPONSE_TYPE
    }

    private fun getExchangeUrl(): String {
        return Utils.AUTHENTICATION_URL + Utils.EXCHANGE_SUB_URL

    }
}