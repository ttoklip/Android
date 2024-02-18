package com.umc.ttoklip.presentation.search.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.db.HistoryEntity
import com.umc.ttoklip.databinding.ItemSerachHistoryBinding

class HistoryRVA(private val onClick : (String) -> Unit):
    ListAdapter<HistoryEntity, HistoryRVA.DetailViewHolder>(diff) {
    inner class DetailViewHolder(
        private val binding: ItemSerachHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HistoryEntity) {
            binding.historyT.text = data.history
            binding.root.setOnClickListener {
                onClick(data.history)
            }
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
        val diff = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(
                oldItem: HistoryEntity,
                newItem: HistoryEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryEntity,
                newItem: HistoryEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}