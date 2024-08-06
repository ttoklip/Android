package com.umc.ttoklip.presentation.honeytip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ItemQuestionListBinding
import java.text.SimpleDateFormat
import java.util.Date

class QuestionListRVA(private val fragment: Fragment, private var listener: OnQuestionClickListener) :
    ListAdapter<HoneyTipMain, QuestionListRVA.QuestionListViewHolder>(object :
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

    inner class QuestionListViewHolder(private val binding: ItemQuestionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(honeyTip: HoneyTipMain) {
            Glide.with(fragment)
                .load(honeyTip.writerProfileImageUrl)
                .into(binding.writerIv)
            binding.titleTv.text = honeyTip.title
            binding.writerTv.text = honeyTip.writer
            binding.dateTv.text = calculateDate(honeyTip.writtenTime)
            binding.bodyTv.text = honeyTip.content
            binding.commentCountTv.text = honeyTip.commentCount.toString()

            binding.root.setOnClickListener {
                listener.onClick(honeyTip)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListViewHolder {
        return QuestionListViewHolder(
            ItemQuestionListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

interface OnQuestionClickListener {
    fun onClick(honeyTip: HoneyTipMain)
}