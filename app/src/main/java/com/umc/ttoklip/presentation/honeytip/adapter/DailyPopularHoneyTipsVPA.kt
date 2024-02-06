package com.umc.ttoklip.presentation.honeytip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemDailyPopularHoneyTipBinding
import kotlin.String

class DailyPopularHoneyTipsVPA: RecyclerView.Adapter<DailyPopularHoneyTipsVPA.DailyPopularHoneyTipsViewHolder>() {
    private var popularHoneyTipsList = listOf(
        PopularHoneyTips("똑리비", "삘래 후, 냄새가 난다면?", "빨래를 돌렸는데 냄새가 나는 것 같거나 옷이 오히려 더러워진 것 같으시면 세탁기 청소 상태 문제일 수도 있어요. 세탁기 내부에 있는 먼지 거름망을 확인..",
        0, 0, 0),
        PopularHoneyTips("똑리비", "삘래 후, 냄새가 난다면?", "빨래를 돌렸는데 냄새가 나는 것 같거나 옷이 오히려 더러워진 것 같으시면 세탁기 청소 상태 문제일 수도 있어요. 세탁기 내부에 있는 먼지 거름망을 확인..",
            0, 0, 0),
        PopularHoneyTips("똑리비", "삘래 후, 냄새가 난다면?", "빨래를 돌렸는데 냄새가 나는 것 같거나 옷이 오히려 더러워진 것 같으시면 세탁기 청소 상태 문제일 수도 있어요. 세탁기 내부에 있는 먼지 거름망을 확인..",
            0, 0, 0),
        PopularHoneyTips("똑리비", "삘래 후, 냄새가 난다면?", "빨래를 돌렸는데 냄새가 나는 것 같거나 옷이 오히려 더러워진 것 같으시면 세탁기 청소 상태 문제일 수도 있어요. 세탁기 내부에 있는 먼지 거름망을 확인..",
            0, 0, 0),
        PopularHoneyTips("똑리비", "삘래 후, 냄새가 난다면?", "빨래를 돌렸는데 냄새가 나는 것 같거나 옷이 오히려 더러워진 것 같으시면 세탁기 청소 상태 문제일 수도 있어요. 세탁기 내부에 있는 먼지 거름망을 확인..",
            0, 0, 0)
    )
    inner class DailyPopularHoneyTipsViewHolder(private val binding: ItemDailyPopularHoneyTipBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(popularHoneyTips: PopularHoneyTips){
            binding.writerTv.text = popularHoneyTips.writer
            binding.honeyTipTitleTv.text = popularHoneyTips.title
            binding.honeyTipBodyContentTv.text = popularHoneyTips.body
            binding.starCountTv.text = popularHoneyTips.starCount.toString()
            binding.likeCountTv.text = popularHoneyTips.likeCount.toString()
            binding.commentCountTv.text = popularHoneyTips.commentCount.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyPopularHoneyTipsViewHolder {
        return DailyPopularHoneyTipsViewHolder(
            ItemDailyPopularHoneyTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return popularHoneyTipsList.size
    }

    override fun onBindViewHolder(holder: DailyPopularHoneyTipsViewHolder, position: Int) {
        holder.bind(popularHoneyTipsList[position])
    }
}

data class PopularHoneyTips(
    val writer: String,
    val title: String,
    val body: String,
    val starCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
)