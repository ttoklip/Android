package com.umc.ttoklip.presentation.honeytip.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.question.QuestionCommentResponse
import com.umc.ttoklip.databinding.ItemCommentBinding
import com.umc.ttoklip.databinding.ItemQuestionCommentBinding
import com.umc.ttoklip.databinding.ItemQuestionReplyBinding
import com.umc.ttoklip.databinding.ItemReplyBinding
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipViewModel
import com.umc.ttoklip.util.setOnSingleClickListener

class QuestionCommentRVA (val context: Context, val replyComment: (Int, String) -> Unit, val ReportOrDelete: (Int, Boolean) -> Unit, val like: (Int, Boolean) -> Unit) :
    ListAdapter<QuestionCommentResponse, RecyclerView.ViewHolder>(differ) {
    inner class ItemViewHolder(
        private val binding: ItemQuestionCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: QuestionCommentResponse) {
            binding.item = data
            Glide.with(context)
                .load(data.writerProfileImageUrl)
                .placeholder(R.drawable.ic_defeault_logo)
                .into(binding.writerIv)

            binding.replyBtn.setOnSingleClickListener {
                replyComment(data.commentId, data.writer ?: "")
            }
            binding.deleteBtn.setOnSingleClickListener {
                ReportOrDelete(
                    data.commentId,
                    data.writer == TtoklipApplication.prefs.getString("nickname", "")
                )
            }
            binding.likeBtn.setOnSingleClickListener {
                like(data.commentId, data.likedByCurrentUser)
            }
        }
    }

    inner class ItemReplyViewHolder(
        private val binding: ItemQuestionReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: QuestionCommentResponse) {
            binding.item = data
            Glide.with(context)
                .load(data.writerProfileImageUrl)
                .placeholder(R.drawable.ic_defeault_logo)
                .into(binding.profileImg)

            binding.deleteBtn.setOnSingleClickListener {
                Log.d("닉네임","${data.writer == TtoklipApplication.prefs.getString("nickname", "")}")
                ReportOrDelete(
                    data.commentId,
                    data.writer == TtoklipApplication.prefs.getString("nickname", "")
                )
            }

            binding.likeBtn.setOnSingleClickListener {
                like(data.commentId, data.likedByCurrentUser)
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
                    ItemQuestionReplyBinding.inflate(
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
        val differ = object : DiffUtil.ItemCallback<QuestionCommentResponse>() {
            override fun areItemsTheSame(
                oldItem: QuestionCommentResponse,
                newItem: QuestionCommentResponse
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: QuestionCommentResponse,
                newItem: QuestionCommentResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}