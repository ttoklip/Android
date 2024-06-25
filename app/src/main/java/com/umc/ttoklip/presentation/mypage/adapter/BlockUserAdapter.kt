package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.mypage.BlockedUser
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.databinding.ItemBlockUserBinding

class BlockUserAdapter : ListAdapter<BlockedUser, BlockUserAdapter.BlockUserViewHolder>(diff) {

    inner class BlockUserViewHolder(
        private val binding: ItemBlockUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BlockedUser) {
//            data.imgUrl?.let {
//
//            }
            binding.blockedUserId.text = data.userId
            binding.unblockBtn.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockUserViewHolder {
        return BlockUserViewHolder(
            ItemBlockUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<BlockedUser>() {
            override fun areItemsTheSame(
                oldItem: BlockedUser,
                newItem: BlockedUser
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: BlockedUser,
                newItem: BlockedUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}