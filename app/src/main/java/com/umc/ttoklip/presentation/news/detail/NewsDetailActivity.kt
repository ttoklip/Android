package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityMainBinding
import com.umc.ttoklip.databinding.ActivityNewsDetailBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsDetailBinding
    var goArticle :Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        goArticle = intent.getBooleanExtra(NEWS_DETAIL_ACTIVITY, true)

    }

    companion object {
        const val NEWS_DETAIL_ACTIVITY = "new_detail"
        fun newIntent(context: Context, goArticle: Boolean) =
            Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(NEWS_DETAIL_ACTIVITY, goArticle)
            }
    }
}