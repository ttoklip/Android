package com.umc.ttoklip.presentation.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemBlockUserBinding

class BlockUserAdapter : ListAdapter<BlockUser, BlockUserAdapter.BlockUserViewHolder>(diff) {

    inner class BlockUserViewHolder(
        private val binding: ItemBlockUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BlockUser) {
            data.imgUrl?.let {

            }
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
        val diff = object : DiffUtil.ItemCallback<BlockUser>() {
            override fun areItemsTheSame(
                oldItem: BlockUser,
                newItem: BlockUser
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: BlockUser,
                newItem: BlockUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}