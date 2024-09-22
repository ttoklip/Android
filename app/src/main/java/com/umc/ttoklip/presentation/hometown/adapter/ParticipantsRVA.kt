package com.umc.ttoklip.presentation.hometown.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Participants
import com.umc.ttoklip.databinding.ItemParticipantBinding

class ParticipantsRVA : ListAdapter<Participants, ParticipantsRVA.ParticipantViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        return ParticipantViewHolder(
            ItemParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(participants: Participants){
                with(binding){
                    model = participants
                    Glide.with(ivParticipant.context)
                        .load(participants.profileImgUrl)
                        .placeholder(R.drawable.ic_defeault_logo)
                        .into(ivParticipant)
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<Participants>() {
        override fun areItemsTheSame(oldItem: Participants, newItem: Participants) =
            oldItem.nickname == newItem.nickname

        override fun areContentsTheSame(oldItem: Participants, newItem: Participants) =
            oldItem == newItem
    }
}