package com.umc.ttoklip.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.databinding.ItemSearchBinding

class SearchRVA(val onClick: (Int,Int) -> Unit) :
    ListAdapter<SearchModel, SearchRVA.ItemViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SearchModel) {
            binding.item = data
            binding.commentCountTv.text = data.commentCount.toString()
            binding.likeCountTv.text = data.likeCount.toString()
            binding.starCountTv.text = data.scrapCount.toString()
            binding.boardT.text = when (data.bigCategory) {
                1 -> "뉴스레터"
                2 -> "질문해요"
                3 -> "꿀팁 공유해요"
                4 -> "함께해요"
                5 -> "소통해요"
                else -> ""
            }
            binding.root.setOnClickListener {
                onClick(data.bigCategory, data.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemSearchBinding.inflate(
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
        val differ = object : DiffUtil.ItemCallback<SearchModel>() {
            override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}