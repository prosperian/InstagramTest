package com.dip.instagramtest.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dip.instagramtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // get method
//    private val url = "https://api.instagram.com/oauth/authorize" +
//            "?client_id=" +
//            "&client_secret=" +
//            "&redirect_uri=" +
//            "&scope=user_profile,user_media" +
//            "&response_type=code"


//     convert code to access token using this
//    curl -X POST \
//  https://api.instagram.com/oauth/access_token \
//  -F client_id={app-id} \
//  -F client_secret={app-secret} \
//  -F grant_type=authorization_code \
//  -F redirect_uri={redirect-uri} \
//  -F code={code}

//     get userId with this
//    curl -X GET \
//  'https://graph.instagram.com/me?fields=id,username&access_token='


//     get media list
//    https://graph.instagram.com/v14.0/{user_id}?fields=media&access_token=

    //    get medial detail
//    https://graph.instagram.com/{media_id}?fields=id,media_type,media_url,username,timestamp&access_token=
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}