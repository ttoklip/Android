package com.umc.ttoklip.presentation.honeytip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.databinding.ItemListHoneyTipBinding
import java.io.Serializable
import java.text.SimpleDateFormat
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
        fun bind(honeyTipResponse: HoneyTipResponse) {
            binding.titleTv.text = honeyTipResponse.title
            binding.writerTv.text = honeyTipResponse.writer
            binding.dateTv.text = calculateDate(honeyTipResponse.writtenTime)
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