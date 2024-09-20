package com.umc.ttoklip.presentation.mypage.adapter

import android.content.Context
import android.graphics.Paint
import android.icu.text.DecimalFormat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.databinding.ItemTransactionHistoryBinding
import com.umc.ttoklip.util.setOnSingleClickListener

class TransactionAdapter(
    private val context: Context,
    private val listener: OnTogetherItemClickListener
) :
    ListAdapter<Togethers, TransactionAdapter.TransactionViewHolder>(diff) {

    inner class TransactionViewHolder(
        private val binding: ItemTransactionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Togethers, pos: Int) {
            with(binding) {

                Glide.with(context)
                    .load(data.writerProfileImageUrl)
                    .into(binding.transactionOwnerIcon)

                root.setOnSingleClickListener {
                    listener.onClick(data)
                }
                if (pos == itemCount - 1) {
                    binding.itemSeparator.isGone = true
                }
                transactionTitleTv.text = data.title
                transactionDateTv.isGone = true
                transactionOwnerIdTv.text = data.writer
                transactionOwnerAddressTv.text = data.location

                val currentAmount = AMOUNT_FORMAT.format(data.currentPrice)
                val targetAmount = AMOUNT_FORMAT.format(data.totalPrice)
                val amount =
                    context.getString(R.string.amount_format, currentAmount, targetAmount)
                val spannableAmount = SpannableString(amount)

                if (data.tradeStatus != "COMPLETED"){
                    closureReasonChip.isGone = true
                }

                if (currentAmount == targetAmount) {
                    spannableAmount.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.blue)), AMOUNT_STRING_START,
                        currentAmount.toString().length,
                        SPANNABLE_FLAG_ZERO
                    )
                    transactionTitleTv.paintFlags =
                        transactionTitleTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    transactionTitleTv.setTextColor(context.getColor(R.color.gray60))
                } else {
                    spannableAmount.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.orange)), AMOUNT_STRING_START,
                        currentAmount.toString().length,
                        SPANNABLE_FLAG_ZERO
                    )
                }
                amountChip.text = spannableAmount

                val member =
                    context.getString(R.string.member_format, data.partyCnt, data.partyMax)
                val spannableMember = SpannableString(member)
                if (data.partyCnt == data.partyMax) {
                    spannableMember.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.blue)), AMOUNT_STRING_START,
                        data.partyCnt.toString().length,
                        SPANNABLE_FLAG_ZERO
                    )
                    transactionTitleTv.paintFlags =
                        transactionTitleTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    transactionTitleTv.setTextColor(context.getColor(R.color.gray60))
                } else {
                    spannableMember.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.orange)), AMOUNT_STRING_START,
                        data.partyMax.toString().length,
                        SPANNABLE_FLAG_ZERO
                    )
                }
                numberOfMembersChip.text = spannableMember

                if (data.commentCount < COMMENT_LENGTH_STANDARD) {
                    commentAmountTv.text =
                        context.getString(R.string.comment_format, data.commentCount)
                } else {
                    commentAmountTv.text = data.commentCount.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            ItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        private const val COMMENT_LENGTH_STANDARD = 10
        private val AMOUNT_FORMAT = DecimalFormat("#,###")
        private const val AMOUNT_STRING_START = 0
        private const val SPANNABLE_FLAG_ZERO = 0
        private const val AMOUNT_STRING_LENGTH = 1
        private val diff = object : DiffUtil.ItemCallback<Togethers>() {
            override fun areItemsTheSame(
                oldItem: Togethers,
                newItem: Togethers
            ): Boolean {
                return oldItem.writer == newItem.writer
            }

            override fun areContentsTheSame(
                oldItem: Togethers,
                newItem: Togethers
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnTogetherItemClickListener {
    fun onClick(together: Togethers)
}