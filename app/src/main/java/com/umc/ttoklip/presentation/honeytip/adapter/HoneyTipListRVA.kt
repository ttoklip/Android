package com.umc.ttoklip.presentation.honeytip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.databinding.ItemListHoneyTipBinding
import java.io.Serializable
import java.util.Date
import kotlin.String

class HoneyTipListRVA(private var listener: OnItemClickListener) :
    ListAdapter<HoneyTipResponse, HoneyTipListRVA.HoneyTipListViewHolder>(object :
        DiffUtil.ItemCallback<HoneyTipResponse>() {
        override fun areItemsTheSame(
            oldItem: HoneyTipResponse,
            newItem: HoneyTipResponse
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HoneyTipResponse,
            newItem: HoneyTipResponse
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    inner class HoneyTipListViewHolder(private val binding: ItemListHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTipResponse: HoneyTipResponse) {
            /*val currentDate = Date()
            val writtenDate = Date.parse(honeyTipResponse.writtenTime)
            val timeDiffInMillis = currentDate.time - writtenDate.time*/
            binding.titleTv.text = honeyTipResponse.title
            binding.writerTv.text = "똑똑이"//honeyTipResponse.writer
            binding.dateTv.text = "1일전"//honeyTipResponse.date
            binding.bodyTv.text = honeyTipResponse.content
            binding.commentCountTv.text = honeyTipResponse.commentCount.toString()

            binding.root.setOnClickListener {
                listener.onClick(honeyTipResponse)
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
    fun onClick(honeyTipResponse: HoneyTipResponse)
}

data class HoneyTips(
    var writer: String,
    var title: String,
    var body: String,
    var date: String,
    var chatCnt: Int = 0,
) : Serializable