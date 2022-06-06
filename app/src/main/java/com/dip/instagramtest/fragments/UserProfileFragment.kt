package com.dip.instagramtest.fragments

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dip.instagramtest.R
import com.dip.instagramtest.adapters.ContentListAdapter
import com.dip.instagramtest.api.Resource
import com.dip.instagramtest.databinding.FragmentUserProfileBinding
import com.dip.instagramtest.listeners.OnItemClickedListener
import com.dip.instagramtest.models.Data
import com.dip.instagramtest.viewmodels.AuthenticationViewModel
import com.dip.instagramtest.viewmodels.UserProfileViewModel
import java.io.File


class UserProfileFragment : Fragment(R.layout.fragment_user_profile), OnItemClickedListener {

    private val TAG = UserProfileViewModel::class.java.simpleName

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var adapter: ContentListAdapter

    private var downloadID: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserProfileBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)
        authViewModel =
            ViewModelProvider(requireActivity()).get(AuthenticationViewModel::class.java)

        viewModel.userMediaList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Getting media list")
                }
                is Resource.Error -> {
                    Log.d(TAG, it.message.toString())
                }
                is Resource.Success -> {
                    viewModel.requestAllMediaData(authViewModel.accessToken.value?.data?.accessToken!!)
                }
            }
        }


        viewModel.mediaDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Getting media detail")
                }
                is Resource.Error -> {
                    Log.d(TAG, it.message.toString())
                }
                is Resource.Success -> {
                    adapter.addItem(it.data!!)
                }
            }
        }

        viewModel.getUserMediaList(
            authViewModel.accessToken.value?.data?.accessToken!!,
            authViewModel.userInfo.value?.data?.id!!
        )

        val rvHomeCharacters = binding.rvContentList

        rvHomeCharacters.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = ContentListAdapter()
        adapter.setOnItemClickedListener(this)
        rvHomeCharacters.adapter = adapter

    }

    override fun onDownloadStarted(data: Data) {
        Toast.makeText(
            requireActivity().applicationContext,
            getString(R.string.downloading),
            Toast.LENGTH_SHORT
        ).show()
        val path = File(data.mediaUrl)
        val fileName: String = path.name
        val request = DownloadManager.Request(Uri.parse(data.mediaUrl))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDescription(getString(R.string.downloading))
            .setTitle(fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)

        val downloadManager =
            requireActivity().applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        downloadID = downloadManager!!.enqueue(request)
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    getString(R.string.donwload_finished),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(onDownloadComplete)
    }

}