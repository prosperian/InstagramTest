package com.dip.instagramtest.listeners

interface WebViewListener {

    fun onErrorHappened()
    fun onExchangeCode(code: String)
}