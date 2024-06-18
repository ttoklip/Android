package com.umc.ttoklip.presentation.hometown.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.databinding.ItemTownCommentBinding
import com.umc.ttoklip.databinding.ItemTownCommentReplyBinding
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment

class TownCommentAdapter(
    val replyComment: (Long) -> Unit,
    val ReportOrDelete: (Long, ReportRequest?) -> Unit
) :
    ListAdapter<CommentResponse, RecyclerView.ViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemTownCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CommentResponse) {
            binding.item = data
            binding.replyBtn.setOnClickListener {
                replyComment(data.commentId)
            }
            binding.deleteBtn.setOnClickListener {
                val reportDialog = ReportDialogFragment()
                reportDialog.setDialogClickListener(object :
                    ReportDialogFragment.DialogClickListener {
                    override fun onClick(type: String, content: String) {
                        ReportOrDelete(
                            data.commentId,
                            ReportRequest(type, content)
                        )
                    }
                })
            }
        }
    }

    inner class ItemReplyViewHolder(
        private val binding: ItemTownCommentReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CommentResponse) {
            binding.item = data

            binding.deleteBtn.setOnClickListener {
                val reportDialog = ReportDialogFragment()
                reportDialog.setDialogClickListener(object :
                    ReportDialogFragment.DialogClickListener {
                    override fun onClick(type: String, content: String) {
                        ReportOrDelete(
                            data.commentId,
                            ReportRequest(type, content)
                        )
                    }
                })
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
                    ItemTownCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                ItemReplyViewHolder(
                    ItemTownCommentReplyBinding.inflate(
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
        return currentList[position].parentId?.toInt() ?: 0
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<CommentResponse>() {
            override fun areItemsTheSame(
                oldItem: CommentResponse,
                newItem: CommentResponse
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: CommentResponse,
                newItem: CommentResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}