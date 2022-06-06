package com.dip.instagramtest.utils

import android.net.Uri
import android.webkit.*
import com.dip.instagramtest.listeners.WebViewListener

class MyWebViewClient(private val webViewListener: WebViewListener) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host == "prosperian.github.io") {
            if (url?.contains("code=")!!) {
                val code = url.subSequence(url.indexOf("=") + 1, url.lastIndexOf('#'))
                webViewListener.onExchangeCode(code.toString())
            }
            return true
        }
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        if (Uri.parse(request?.url.toString()).getQueryParameter(Utils.CLIENT_ID) != null) {
            if (errorResponse?.statusCode != 200) {
                webViewListener.onErrorHappened()
            }
        }
        super.onReceivedHttpError(view, request, errorResponse)

    }


}