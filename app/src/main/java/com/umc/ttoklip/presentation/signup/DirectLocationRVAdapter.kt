package com.umc.ttoklip.presentation.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.data.model.KakaoResponse
import com.umc.ttoklip.databinding.ItemAddressBinding

class DirectLocationRVAdapter(private val addressList:List<KakaoResponse.Place>):RecyclerView.Adapter<DirectLocationRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemAddressBinding=ItemAddressBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int=addressList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressList[position])
    }

    inner class ViewHolder(val binding:ItemAddressBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(place:KakaoResponse.Place){
            binding.itemAddressTitleTv.text=place.place_name
            binding.itemAddressDetailTv.text=place.address_name
        }
    }
}