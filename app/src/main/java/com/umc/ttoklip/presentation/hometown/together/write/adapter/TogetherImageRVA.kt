package com.umc.ttoklip.presentation.hometown.together.write.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.databinding.ItemImageBinding
import com.umc.ttoklip.databinding.ItemTogetherImageBinding
import com.umc.ttoklip.util.isValidUri

class TogetherImageRVA(private val context: Context, private var listener: OnImageClickListener?): ListAdapter<com.umc.ttoklip.presentation.honeytip.adapter.Image, TogetherImageRVA.ImageViewHolder>(object : DiffUtil.ItemCallback<com.umc.ttoklip.presentation.honeytip.adapter.Image,>(){
    override fun areItemsTheSame(oldItem: com.umc.ttoklip.presentation.honeytip.adapter.Image, newItem: com.umc.ttoklip.presentation.honeytip.adapter.Image,): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: com.umc.ttoklip.presentation.honeytip.adapter.Image, newItem: com.umc.ttoklip.presentation.honeytip.adapter.Image,): Boolean {
        return oldItem == newItem
    }

}) {
    inner class ImageViewHolder(private val binding: ItemTogetherImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: com.umc.ttoklip.presentation.honeytip.adapter.Image,){
            if (image.src.isValidUri()){
                Glide.with(context)
                    .load(Uri.parse(image.src))
                    .into(binding.iv)
            } else {
                Glide.with(context)
                    .load(image.src)
                    .into(binding.iv)
            }

            binding.iv.setOnClickListener {
                listener?.onClick(image, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemTogetherImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

interface OnImageClickListener {
    fun onClick(image: com.umc.ttoklip.presentation.honeytip.adapter.Image, position: Int)
}

data class Image(
    val id: Int,
    val src: String
)