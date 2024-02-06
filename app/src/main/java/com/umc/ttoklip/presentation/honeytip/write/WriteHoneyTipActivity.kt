package com.umc.ttoklip.presentation.honeytip.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityHoneyTipBinding
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment

class WriteHoneyTipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHoneyTipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoneyTipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val transaction = supportFragmentManager.beginTransaction()
        when(intent.getStringExtra("caller")){
            "writeFab" -> {
                transaction
                    .replace(R.id.container, WriteHoneyTipFragment())
                    .commit()
            }
            else -> {
                transaction
                    .replace(R.id.container, DetailHoneyTipFragment())
                    .commit()
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}