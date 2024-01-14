package com.umc.ttoklip.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding>(@LayoutRes val layoutResource: Int) :
    AppCompatActivity() {

    private var _binding: V? = null
    abstract fun initView()
    protected abstract fun initObserver()
    protected val binding: V get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResource)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initView()
        initObserver()
    }

}