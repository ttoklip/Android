package com.umc.ttoklip.presentation.honeytip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ItemListHoneyTipBinding
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

class HoneyTipListRVA(private var listener: OnItemClickListener) :
    ListAdapter<HoneyTipMain, HoneyTipListRVA.HoneyTipListViewHolder>(object :
        DiffUtil.ItemCallback<HoneyTipMain>() {
        override fun areItemsTheSame(
            oldItem: HoneyTipMain,
            newItem: HoneyTipMain
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HoneyTipMain,
            newItem: HoneyTipMain
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    private fun calculateDate(writtenDate: String): String {
        val currentDate = Date(System.currentTimeMillis())

        // SimpleDateFormat을 사용하여 문자열을 Date로 변환
        val sdf = SimpleDateFormat("yyyy.MM.dd hh:mm")

        val date = sdf.parse("20$writtenDate")

        // 두 날짜의 차이 계산
        val difference = currentDate.time - date.time
        val daysDifference = difference / (24 * 60 * 60 * 1000)
        Log.d("날짜 계산", "$currentDate 와 $writtenDate 사이의 날짜 차이: $daysDifference 일")
        return if (daysDifference == 0.toLong()) "오늘" else "${daysDifference}일전"
    }

    inner class HoneyTipListViewHolder(private val binding: ItemListHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTip: HoneyTipMain) {
            binding.titleTv.text = honeyTip.title
            binding.writerTv.text = honeyTip.writer
            binding.dateTv.text = if (honeyTip.writtenTime.isNotBlank()) {
                calculateDate(honeyTip.writtenTime)
            } else {
                ""
            }
            binding.bodyTv.text = honeyTip.content
            binding.commentCountTv.text = honeyTip.commentCount.toString()
            binding.likeCountTv.text = honeyTip.likeCount.toString()
            binding.scrapCountTv.text = honeyTip.scrapCount.toString()
            binding.root.setOnClickListener {
                listener.onClick(honeyTip)
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
    fun onClick(honeyTip: HoneyTipMain)
}

data class HoneyTips(
    var writer: String,
    var title: String,
    var body: String,
    var date: String,
    var chatCnt: Int = 0,
) : Serializable