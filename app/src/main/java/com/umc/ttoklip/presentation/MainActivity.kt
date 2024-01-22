package com.umc.ttoklip.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityMainBinding
import com.umc.ttoklip.presentation.honeytip.HoneyTipFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        // add를 통해 container에 Fragment 추가
        fragmentTransaction.add(R.id.container, HoneyTipFragment())
        fragmentTransaction.setReorderingAllowed(true)
        // commit을 통해 transaction 등록
        fragmentTransaction.commit()
    }
}