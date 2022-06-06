package com.dip.instagramtest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dip.instagramtest.utils.MyWebViewClient
import com.dip.instagramtest.R
import com.dip.instagramtest.api.Resource
import com.dip.instagramtest.databinding.FragmentAuthenticationBinding
import com.dip.instagramtest.listeners.WebViewListener
import com.dip.instagramtest.viewmodels.AuthenticationViewModel


class AuthenticationFragment : Fragment(R.layout.fragment_authentication), WebViewListener {

    private val TAG = AuthenticationFragment::class.java.simpleName

    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var viewModel: AuthenticationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAuthenticationBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(AuthenticationViewModel::class.java)

        loadWebView()
        var message = ""
        viewModel.accessToken.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Exchanging code for access token")
                    message += getString(R.string.getting_user_token)
                    binding.txtInformation.text = message
                }
                is Resource.Error -> {
                    Log.d(TAG, it.message.toString())
                }
                is Resource.Success -> {
                    message += getString(R.string.getting_user_info)
                    viewModel.getUserInfo(it.data?.accessToken.toString())
                    binding.txtInformation.text = message
                }
            }
        }

        viewModel.userInfo.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading user info")
                }
                is Resource.Error -> {
                    Log.d(TAG, it.message.toString())
                }
                is Resource.Success -> {
                    message += getString(R.string.username_info) + it.data?.username
                    binding.txtInformation.text = message
                    findNavController().navigate(R.id.action_authenticationFragment_to_userProfileFragment)
                }
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        val webViewInsta: WebView = binding.webView
        webViewInsta.settings.javaScriptEnabled = true
        webViewInsta.webViewClient = MyWebViewClient(this)
        webViewInsta.loadUrl(viewModel.getAuthenticationUrl())
    }

    override fun onErrorHappened() {
        binding.webView.visibility = View.GONE
        binding.txtNotAuthorized.visibility = View.VISIBLE
        Toast.makeText(
            requireActivity().applicationContext,
            getString(R.string.ask_to_join),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onExchangeCode(code: String) {
        viewModel.sendExchangeRequest(code)
    }

}