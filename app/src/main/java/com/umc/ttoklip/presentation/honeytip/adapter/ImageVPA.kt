package com.umc.ttoklip.presentation.honeytip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.databinding.ItemImageVpBinding

class ImageVPA(private val context: Context): ListAdapter<String, ImageVPA.ImageViewHolder>(
    object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
) {

    inner class ImageViewHolder(private val binding: ItemImageVpBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imageUrl: String){
            Glide.with(context)
                .load(imageUrl)
                .into(binding.iv)
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
}