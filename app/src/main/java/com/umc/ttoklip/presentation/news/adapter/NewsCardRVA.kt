package com.umc.ttoklip.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.news.News
import com.umc.ttoklip.databinding.ItemNewsViewPagerBinding

class NewsCardRVA(val onClick: (News) -> Unit) : ListAdapter<NewsCard, NewsCardRVA.ItemViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemNewsViewPagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCard) {
            binding.item = data
            binding.news1Img.setOnClickListener {
                if (data.newsList.isNotEmpty()) onClick(data.newsList[0])
            }
            binding.newsTitle2T.setOnClickListener {
                if (data.newsList.size >=2) onClick(data.newsList[1])
            }
            binding.newsTitle3T.setOnClickListener {
                if (data.newsList.size >=3) onClick(data.newsList[2])
            }
            binding.newsTitle4T.setOnClickListener {
                if (data.newsList.size >=4) onClick(data.newsList[3])
            }
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