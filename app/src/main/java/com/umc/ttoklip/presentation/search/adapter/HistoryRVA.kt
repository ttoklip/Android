package com.umc.ttoklip.presentation.search.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemSerachHistoryBinding

class HistoryRVA :
    ListAdapter<HistoryModel, HistoryRVA.DetailViewHolder>(diff) {
    inner class DetailViewHolder(
        private val binding: ItemSerachHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HistoryModel) {

            binding.historyT.text = data.string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemSerachHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean {
                return oldItem.string == newItem.string
            }

            override fun areContentsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}