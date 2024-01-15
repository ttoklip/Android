package com.umc.ttoklip.presentation.news

import android.os.Bundle
import android.view.View
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}