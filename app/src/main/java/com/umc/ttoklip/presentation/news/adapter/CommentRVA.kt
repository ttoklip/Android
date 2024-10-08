package com.umc.ttoklip.presentation.news.adapter


import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.databinding.ItemCommentBinding
import com.umc.ttoklip.databinding.ItemReplyBinding

class CommentRVA(
    val activity: Activity,
    val replyComment: (Int, String) -> Unit,
    val ReportOrDelete: (Int, Boolean) -> Unit,
    val strangerOnClick: (String) -> Unit
) :
    ListAdapter<NewsCommentResponse, RecyclerView.ViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCommentResponse) {
            Glide.with(activity)
                .load(data.writerProfileImageUrl)
                .placeholder(R.drawable.ic_defeault_logo)
                .into(binding.profileImg)
            binding.item = data
            binding.replyBtn.setOnClickListener {
                if (data.commentContent == "삭제된 댓글입니다."){
                    return@setOnClickListener
                }
                replyComment(data.commentId, data.writer ?: "")
            }
            binding.deleteBtn.setOnClickListener {
                if (data.commentContent == "삭제된 댓글입니다."){
                    return@setOnClickListener
                }
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
        private val activity: Activity,
        private val binding: ItemReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NewsCommentResponse) {
            Glide.with(activity)
                .load(data.writerProfileImageUrl)
                .placeholder(R.drawable.ic_defeault_logo)
                .into(binding.profileImg)
            binding.item = data
            Log.d("item", data.toString())
            Log.d("spf", TtoklipApplication.prefs.getString("nickname", ""))
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
                    activity,
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