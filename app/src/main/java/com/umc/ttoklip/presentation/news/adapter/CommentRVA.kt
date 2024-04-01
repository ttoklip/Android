package com.umc.ttoklip.presentation.news.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.databinding.ItemCommentBinding
import com.umc.ttoklip.databinding.ItemReplyBinding

class CommentRVA(
    val replyComment: (Int) -> Unit,
    val ReportOrDelete: (Int, Boolean) -> Unit,
    val strangerOnClick: (String) -> Unit
) :
    ListAdapter<NewsCommentResponse, RecyclerView.ViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCommentResponse) {
            binding.item = data
            binding.replyBtn.setOnClickListener {
                replyComment(data.commentId)
            }
            binding.deleteBtn.setOnClickListener {
                ReportOrDelete(
                    data.commentId,
                    data.writer == TtoklipApplication.prefs.getString("nickname", "")
                )
            }
            binding.profileImg.setOnClickListener {
                strangerOnClick(data.writer ?: "")
            }
        }
    }

    inner class ItemReplyViewHolder(
        private val binding: ItemReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCommentResponse) {
            binding.item = data

            binding.deleteBtn.setOnClickListener {
                Log.d("닉네임", "${data.writer == TtoklipApplication.prefs.getString("nickname", "")}")
                ReportOrDelete(
                    data.commentId,
                    data.writer == TtoklipApplication.prefs.getString("nickname", "")
                )
            }
            binding.profileImg.setOnClickListener {
                strangerOnClick(data.writer ?: "")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
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
            0 -> {
                (holder as ItemViewHolder).bind(currentList[position])
            }

            else -> {
                (holder as ItemReplyViewHolder).bind(currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].parentId ?: 0
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<NewsCommentResponse>() {
            override fun areItemsTheSame(
                oldItem: NewsCommentResponse,
                newItem: NewsCommentResponse
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: NewsCommentResponse,
                newItem: NewsCommentResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}