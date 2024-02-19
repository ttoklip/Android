package com.umc.ttoklip.presentation.honeytip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.databinding.ItemCommentBinding
import com.umc.ttoklip.databinding.ItemQuestionCommentBinding
import com.umc.ttoklip.databinding.ItemReplyBinding
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipViewModel

class QuestionCommentRVA (val replyComment: (Int) -> Unit, val ReportOrDelete: (Int, Boolean) -> Unit, val like: (Int, Boolean) -> Unit) :
    ListAdapter<NewsCommentResponse, RecyclerView.ViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemQuestionCommentBinding
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
            binding.likeBtn.setOnClickListener {
                Log.d("commentId", data.commentId.toString())
                like(data.commentId, data.writer == TtoklipApplication.prefs.getString("nickname", ""))
            }
        }
    }

    inner class ItemReplyViewHolder(
        private val binding: ItemReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCommentResponse) {
            binding.item = data

            binding.deleteBtn.setOnClickListener {
                Log.d("닉네임","${data.writer == TtoklipApplication.prefs.getString("nickname", "")}")
                ReportOrDelete(
                    data.commentId,
                    data.writer == TtoklipApplication.prefs.getString("nickname", "")
                )
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
                    ItemQuestionCommentBinding.inflate(
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