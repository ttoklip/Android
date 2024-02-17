package com.umc.ttoklip.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemNewsViewPagerBinding

class NewsCardRVA : ListAdapter<NewsCard, NewsCardRVA.ItemViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemNewsViewPagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCard) {
            binding.item = data
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemNewsViewPagerBinding.inflate(
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
        val differ = object : DiffUtil.ItemCallback<NewsCard>() {
            override fun areItemsTheSame(oldItem: NewsCard, newItem: NewsCard): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsCard, newItem: NewsCard): Boolean {
                return oldItem == newItem
            }
        }
    }
}