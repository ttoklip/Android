package com.umc.ttoklip.presentation.mypage.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemTermBinding
import com.umc.ttoklip.presentation.signup.fragments.TermViewModel
import com.umc.ttoklip.presentation.signup.fragments.WebviewActivity

class MyPageTermRVAdapter(
    private val context: Context,
    private val termList: ArrayList<TermViewModel.Term>) :
    RecyclerView.Adapter<MyPageTermRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(termId: Int)
        fun onCheckTermOn(termId: Int)
        fun onCheckTermOff(termId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun checkTerm(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTermBinding =
            ItemTermBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = termList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(termList[position])
        holder.binding.termAgreeServiceBtn.setOnClickListener {
            val intent = Intent(context, WebviewActivity::class.java)
            intent.putExtra("url",termList[position].content)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(val binding: ItemTermBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(term: TermViewModel.Term) {
            binding.termAgreeServiceTv.text = term.title
            binding.termDetailTv.text=term.content
            binding.termAgreeServiceOnIv.visibility = View.INVISIBLE
            binding.termAgreeServiceOffIv.visibility = View.INVISIBLE
        }
    }


}