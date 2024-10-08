package com.umc.ttoklip.presentation.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ItemHomeHoneyTipBinding
import com.umc.ttoklip.databinding.ItemListHoneyTipBinding
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.Date

class HomeTipRVA(private val fragment:Fragment, private var listener: OnItemClickListener) :
    ListAdapter<HoneyTipMain, HomeTipRVA.HoneyTipListViewHolder>(object :
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

    inner class HoneyTipListViewHolder(private val binding: ItemHomeHoneyTipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTip: HoneyTipMain) {
            Glide.with(fragment)
                .load(honeyTip.writerProfileImageUrl)
                .placeholder(R.drawable.ic_defeault_logo)
                .into(binding.writerIv)
            binding.titleTv.text = honeyTip.title
            binding.writerTv.text = honeyTip.writer
            binding.dateTv.text = calculateDate(honeyTip.writtenTime)
            binding.bodyTv.text = honeyTip.content
            binding.commentCountTv.text = honeyTip.commentCount.toString()
            binding.likeCountTv.text = honeyTip.likeCount.toString()
            binding.starCountTv.text = honeyTip.scrapCount.toString()
            binding.root.setOnClickListener {
                listener.onClick(honeyTip)
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

interface OnItemClickListener {
    fun onClick(honeyTip: HoneyTipMain)
}