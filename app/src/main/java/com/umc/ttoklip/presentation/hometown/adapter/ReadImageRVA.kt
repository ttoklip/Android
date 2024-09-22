package com.umc.ttoklip.presentation.hometown.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.data.model.town.ImageUrl
import com.umc.ttoklip.databinding.ItemImageReadBinding
import com.umc.ttoklip.util.setOnSingleClickListener

class ReadImageRVA(private val context: Context, private var listener: OnReadImageClickListener?) :
    ListAdapter<ImageUrl, ReadImageRVA.ImageViewHolder>(object : DiffUtil.ItemCallback<ImageUrl>() {
        override fun areItemsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class ImageViewHolder(private val binding: ItemImageReadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: ImageUrl) {
            Glide.with(binding.iv.context)
                .load(imageUrl.communityImageUrl)
                .into(binding.iv)

            binding.iv.setOnSingleClickListener {
                listener?.onClick(imageUrl.communityImageUrl, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageReadBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

interface OnReadImageClickListener {
    fun onClick(imageUrl: String, position: Int)
}