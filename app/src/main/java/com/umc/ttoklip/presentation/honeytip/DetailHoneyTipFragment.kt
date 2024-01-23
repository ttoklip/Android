package com.umc.ttoklip.presentation.honeytip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.ttoklip.databinding.FragmentDetailHoneyTipBinding

class DetailHoneyTipFragment: Fragment() {
    private lateinit var binding: FragmentDetailHoneyTipBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHoneyTipBinding.inflate(inflater, container, false)
        return binding.root
    }
}