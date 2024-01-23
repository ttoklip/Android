package com.umc.ttoklip.presentation.honeytip.read

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityReadBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipFragment

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {
    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailHoneyTipFragment())
            .commit()
    }

    override fun initObserver() {

    }
}