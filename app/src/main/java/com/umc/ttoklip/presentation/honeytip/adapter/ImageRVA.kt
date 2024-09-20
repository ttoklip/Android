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
import com.umc.ttoklip.util.isValidUri
import com.umc.ttoklip.util.setOnSingleClickListener
import okhttp3.internal.assertThreadDoesntHoldLock

class ImageRVA(private val context: Context, private var listener: OnImageClickListener?): ListAdapter<Image, ImageRVA.ImageViewHolder>(object : DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}) {
    inner class ImageViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Image){
            if (image.src.isValidUri()){
                Glide.with(context)
                    .load(Uri.parse(image.src))
                    .into(binding.iv)
            } else {
                Glide.with(context)
                    .load(image.src)
                    .into(binding.iv)
            }

            binding.iv.setOnSingleClickListener {
                listener?.onClick(image, bindingAdapterPosition)
            }
            binding.deleteBtn.setOnSingleClickListener {
                listener?.deleteImage(bindingAdapterPosition, image.id)
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
    fun onClick(image: Image, position: Int)
    fun deleteImage(position: Int, id: Int)
}

data class Image(
    val id: Int,
    val src: String
)