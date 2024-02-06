package com.umc.ttoklip.presentation.honeytip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemListHoneyTipBinding
import java.io.Serializable

class HoneyTipListRVA(private var listener: OnItemClickListener): ListAdapter<HoneyTips, HoneyTipListRVA.HoneyTipListViewHolder>(object: DiffUtil.ItemCallback<HoneyTips>(){
    override fun areItemsTheSame(oldItem: HoneyTips, newItem: HoneyTips): Boolean {
        return oldItem.writer == newItem.writer && oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: HoneyTips, newItem: HoneyTips): Boolean {
        return oldItem == newItem
    }

}){
    inner class HoneyTipListViewHolder(private val binding: ItemListHoneyTipBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(honeyTips: HoneyTips){
            binding.titleTv.text = honeyTips.title
            binding.writerTv.text = honeyTips.writer
            binding.dateTv.text = honeyTips.date
            binding.bodyTv.text = honeyTips.body
            binding.commentCountTv.text = honeyTips.chatCnt.toString()

            binding.root.setOnClickListener {
                listener.onClick(honeyTips)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoneyTipListViewHolder {
        return HoneyTipListViewHolder(
            ItemListHoneyTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HoneyTipListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

interface OnItemClickListener {
    fun onClick(honeyTips: HoneyTips)
}

data class HoneyTips(
    var writer: String,
    var title: String,
    var body: String,
    var date: String,
    var chatCnt: Int = 0,
): Serializable