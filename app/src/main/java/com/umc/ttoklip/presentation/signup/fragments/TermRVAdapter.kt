package com.umc.ttoklip.presentation.signup.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.databinding.ItemTermBinding

class TermRVAdapter(
    private val context: Context,
    private val termList: ArrayList<TermViewModel.Term>) :
    RecyclerView.Adapter<TermRVAdapter.ViewHolder>() {

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
//        holder.itemView.setOnClickListener {
//
//        }
        holder.binding.termAgreeServiceBtn.setOnClickListener {
//            if(holder.binding.termDetailSv.visibility.equals(View.VISIBLE)){
//                holder.binding.termDetailSv.visibility=View.GONE
//            }else{
//                holder.binding.termDetailSv.visibility=View.VISIBLE
//            }
//            checkTerm()
            mItemClickListener.onItemClick(termList[position].termId-1)

            //webview로 이동
//            val intent = Intent(context, WebviewActivity::class.java)
//            intent.putExtra("url",termList[position].content)
//            context.startActivity(intent)
        }
        holder.binding.termAgreeServiceOnIv.setOnClickListener {
            mItemClickListener.onCheckTermOff(termList[position].termId-1)
            checkTerm()
        }
        holder.binding.termAgreeServiceOffIv.setOnClickListener {
            mItemClickListener.onCheckTermOn(termList[position].termId-1)
            checkTerm()
        }
    }

    inner class ViewHolder(val binding: ItemTermBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(term: TermViewModel.Term) {
            binding.termAgreeServiceTv.text = term.title
            binding.termDetailTv.text=term.content
            if (term.check) {
                binding.termAgreeServiceOnIv.visibility = View.VISIBLE
                binding.termAgreeServiceOffIv.visibility = View.INVISIBLE
            } else {
                binding.termAgreeServiceOnIv.visibility = View.INVISIBLE
                binding.termAgreeServiceOffIv.visibility = View.VISIBLE
            }
        }
    }


}