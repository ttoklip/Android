package com.umc.ttoklip.presentation.news.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemCommentBinding
import com.umc.ttoklip.databinding.ItemReplyBinding


//댓글 1 대댓글 2

class CommentRVA : ListAdapter<Comment, RecyclerView.ViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Comment) {
            binding.item = data
        }
    }

    inner class ItemReplyViewHolder(
        private val binding: ItemReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Comment) {
            binding.item = data
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                ItemViewHolder(
                    ItemCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ItemReplyViewHolder(
                    ItemReplyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            1 -> {
                (holder as ItemViewHolder).bind(currentList[position])
            }
            else ->{
                (holder as ItemReplyViewHolder).bind(currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].isReply
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }
        }
    }
}