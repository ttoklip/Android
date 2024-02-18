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
import com.umc.ttoklip.databinding.ItemImageBinding
import okhttp3.internal.assertThreadDoesntHoldLock

class ImageRVA(private var listener: OnImageClickListener?): ListAdapter<Image, ImageRVA.ImageViewHolder>(object : DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}) {
    inner class ImageViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Image){
            binding.iv.setImageURI(image.uri)

            binding.iv.setOnClickListener {
                listener?.onClick(image)
            }
            binding.deleteBtn.setOnClickListener {
                listener?.deleteImage(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

interface OnImageClickListener {
    fun onClick(image: Image)
    fun deleteImage(position: Int)
}

data class Image(
    val uri: Uri
)