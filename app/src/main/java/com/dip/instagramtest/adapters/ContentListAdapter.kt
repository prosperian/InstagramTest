package com.dip.instagramtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dip.instagramtest.databinding.ImageContentListItemBinding
import com.dip.instagramtest.listeners.OnItemClickedListener
import com.dip.instagramtest.models.Data
import com.dip.instagramtest.utils.Utils

class ContentListAdapter : RecyclerView.Adapter<ContentListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var onItemClickedListener: OnItemClickedListener
    private val list: MutableList<Data> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ImageContentListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            Glide.with(context).load(item.mediaUrl).into(binding.ivPost)
            binding.btnSave.setOnClickListener {
                onItemClickedListener.onDownloadStarted(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickedListener(listener: OnItemClickedListener) {
        onItemClickedListener = listener
    }

    fun addItem(data: Data) {
        list.add(data)
        notifyItemInserted(list.size - 1)
    }


    inner class ViewHolder(val binding: ImageContentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}