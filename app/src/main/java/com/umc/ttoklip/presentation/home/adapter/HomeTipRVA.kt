package com.umc.ttoklip.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemHomeHoneyTipBinding
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener

class HomeTipRVA(private var onclick:()->Unit): ListAdapter<HoneyTips, HomeTipRVA.HoneyTipListViewHolder>(object: DiffUtil.ItemCallback<HoneyTips>(){
    override fun areItemsTheSame(oldItem: HoneyTips, newItem: HoneyTips): Boolean {
        return oldItem.writer == newItem.writer && oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: HoneyTips, newItem: HoneyTips): Boolean {
        return oldItem == newItem
    }

}){
    inner class HoneyTipListViewHolder(private val binding: ItemHomeHoneyTipBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(honeyTips: HoneyTips){
            binding.titleTv.text = honeyTips.title
            binding.writerTv.text = honeyTips.writer
            binding.dateTv.text = honeyTips.date
            binding.bodyTv.text = honeyTips.body
            binding.commentCountTv.text = honeyTips.chatCnt.toString()

            binding.root.setOnClickListener {
                onclick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoneyTipListViewHolder {
        return HoneyTipListViewHolder(
            ItemHomeHoneyTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HoneyTipListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
