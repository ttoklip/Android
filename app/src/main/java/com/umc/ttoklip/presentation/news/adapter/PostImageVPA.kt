package com.umc.ttoklip.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.news.detail.ImageUrl
import com.umc.ttoklip.databinding.ItemImageVpBinding

class PostImageVPA : ListAdapter<ImageUrl, PostImageVPA.ItemViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemImageVpBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ImageUrl) {
            Glide.with(itemView)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_logo_big)
                .error(R.drawable.ic_logo_big)
                .apply(RequestOptions().fitCenter())
                .into(binding.iv)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemImageVpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<ImageUrl>() {
            override fun areItemsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: ImageUrl, newItem: ImageUrl): Boolean {
                return oldItem == newItem
            }
        }
    }
}