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
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ItemTransactionHistoryBinding

class TransactionAdapter(private val context: Context) :
    ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(diff) {

    inner class TransactionViewHolder(
        private val binding: ItemTransactionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Transaction) {
            with(binding) {
                transactionTitleTv.text = data.title
                transactionDateTv.text = data.date
                data.icon?.let {

                }
                transactionOwnerIdTv.text = data.ownerId
                transactionOwnerAddressTv.text = data.address
                if (data.closureReason != null) {
                    transactionTitleTv.paintFlags =
                        transactionTitleTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    transactionTitleTv.setTextColor(context.getColor(R.color.gray60))
                    closureReasonChip.text = data.closureReason
                } else {
                    closureReasonChip.isGone = true
                }

                val currentAmount = AMOUNT_FORMAT.format(data.currentAmount)
                val targetAmount = AMOUNT_FORMAT.format(data.targetAmount)
                val amount =
                    context.getString(R.string.amount_format, currentAmount, targetAmount)
                val spannableAmount = SpannableString(amount)
                if (currentAmount == targetAmount) {
                    spannableAmount.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.blue)), 0,
                        data.currentAmount.toString().length,
                        0
                    )
                } else {
                    spannableAmount.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.orange)), 0,
                        data.currentAmount.toString().length,
                        0
                    )
                }
                amountChip.text = spannableAmount

                val member =
                    context.getString(R.string.member_format, data.currentMember, data.targetMember)
                val spannableMember = SpannableString(member)
                if (data.currentMember == data.targetMember) {
                    spannableMember.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.blue)), 0,
                        data.currentMember.toString().length,
                        0
                    )
                } else {
                    spannableMember.setSpan(
                        ForegroundColorSpan(context.getColor(R.color.orange)), 0,
                        data.targetMember.toString().length,
                        0
                    )
                }
                numberOfMembersChip.text = spannableMember

                if (data.commentAmount < COMMENT_LENGTH_STANDARD) {
                    commentAmountTv.text =
                        context.getString(R.string.comment_format, data.commentAmount)
                } else {
                    commentAmountTv.text = data.commentAmount.toString()
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
        holder.bind(getItem(position))
    }

    companion object {
        private const val COMMENT_LENGTH_STANDARD = 10
        private val AMOUNT_FORMAT = DecimalFormat("#,###")
        private val diff = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem.ownerId == newItem.ownerId
            }

            override fun areContentsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}