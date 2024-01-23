package com.umc.ttoklip.presentation.mypage

import android.widget.SeekBar
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityMyHomtownAddressBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyHometownAddressActivity :
    BaseActivity<ActivityMyHomtownAddressBinding>(R.layout.activity_my_homtown_address) {
    private lateinit var range: String
    override fun initView() {
        range = getString(R.string.range_500m)
        binding.rangeSettingExplainTv.text =
            getString(R.string.range_setting_format, range)
        binding.myHometownAddressBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.range500mTv.setOnClickListener {
            setRange500m()
        }
        binding.range1kmTv.setOnClickListener {
            setRange1km()
        }
        binding.range15kmTv.setOnClickListener {
            setRange15km()
        }

        binding.rangeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress <= RANGE_500M_PROGRESS) {
                    setRange500m()
                } else if (progress <= RANGE_1KM_PROGRESS) {
                    setRange1km()
                } else {
                    setRange15km()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    if (it.progress <= RANGE_500M_PROGRESS) {
                        it.progress = RANGE_500M_PROGRESS
                        setRange500m()
                    } else if (it.progress <= RANGE_1KM_PROGRESS) {
                        it.progress = RANGE_1KM_PROGRESS
                        setRange1km()
                    } else {
                        it.progress = RANGE_15kM_PROGRESS
                        setRange15km()
                    }
                }
            }

        })
    }

    private fun setRange15km() {
        range = getString(R.string.range_1_5km)
        binding.rangeBar.progress = RANGE_15kM_PROGRESS
        binding.range500mTv.setTextColor(getColor(R.color.gray40))
        binding.range1kmTv.setTextColor(getColor(R.color.gray40))
        binding.range15kmTv.setTextColor(getColor(R.color.black))
        binding.rangeSettingExplainTv.text = getString(R.string.range_setting_format, range)
    }

    private fun setRange1km() {
        range = getString(R.string.range_1km)
        binding.rangeBar.progress = RANGE_1KM_PROGRESS
        binding.range500mTv.setTextColor(getColor(R.color.gray40))
        binding.range1kmTv.setTextColor(getColor(R.color.black))
        binding.range15kmTv.setTextColor(getColor(R.color.gray40))
        binding.rangeSettingExplainTv.text = getString(R.string.range_setting_format, range)
    }

    private fun setRange500m() {
        range = getString(R.string.range_500m)
        binding.rangeBar.progress = RANGE_500M_PROGRESS
        binding.range500mTv.setTextColor(getColor(R.color.black))
        binding.range1kmTv.setTextColor(getColor(R.color.gray40))
        binding.range15kmTv.setTextColor(getColor(R.color.gray40))
        binding.rangeSettingExplainTv.text = getString(R.string.range_setting_format, range)
    }

    override fun initObserver() = Unit

    companion object {
        private const val RANGE_500M_PROGRESS = 33
        private const val RANGE_1KM_PROGRESS = 67
        private const val RANGE_15kM_PROGRESS = 100
    }
}