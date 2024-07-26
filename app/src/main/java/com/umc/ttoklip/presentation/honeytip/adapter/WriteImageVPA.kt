package com.umc.ttoklip.presentation.honeytip.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.databinding.ItemImageVpBinding

class WriteImageVPA(private val context: Context) :
    ListAdapter<String, WriteImageVPA.ImageViewHolder>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    ) {

    inner class ImageViewHolder(private val binding: ItemImageVpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(writeImage: String) {
            Log.d("writeImage", writeImage.toString())
            if (isValidUri(writeImage)) {
                Glide.with(context)
                    .load(Uri.parse(writeImage))
                    .into(binding.iv)
            } else {
                Glide.with(context)
                    .load(writeImage)
                    .into(binding.iv)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageVpBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private fun isValidUri(string: String): Boolean {
        return try {
            val uri = Uri.parse(string)
            // 기본적으로 scheme이나 path가 비어있지 않은지를 체크
            uri.scheme != null && uri.scheme!!.isNotEmpty() && uri.path != null
        } catch (e: Exception) {
            false
        }
    }

    data class WriteImage(
        val url: String,
        val uri: Uri
    )
}