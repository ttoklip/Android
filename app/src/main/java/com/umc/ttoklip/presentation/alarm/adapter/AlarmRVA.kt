package com.umc.ttoklip.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemAlarmBinding
import com.umc.ttoklip.presentation.news.adapter.Dummy

class AlarmRVA(val onClick: () -> Unit) : ListAdapter<Dummy, AlarmRVA.ItemViewHolder>(differ) {

    inner class ItemViewHolder(
        private val binding: ItemAlarmBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Dummy) {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemAlarmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Dummy>() {
            override fun areItemsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
                return oldItem == newItem
            }
        }
    }
}