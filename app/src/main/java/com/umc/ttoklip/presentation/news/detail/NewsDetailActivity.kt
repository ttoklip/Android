package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityMainBinding
import com.umc.ttoklip.databinding.ActivityNewsDetailBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object{
        const val NEWS_DETAIL_ACTIVITY = "new_detail"
        fun newIntent(context: Context) = Intent(context, NewsDetailActivity::class.java)
    }
}