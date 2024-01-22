package com.umc.ttoklip.presentation.honeytip.write

import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentWriteHoneyTipBinding
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.presentation.honeytip.adapter.ImageRVA


class WriteHoneyTipFragment : Fragment() {
    private val imageLoadLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){

    }
    private lateinit var binding: FragmentWriteHoneyTipBinding
    private lateinit var imageAdapter: ImageRVA
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteHoneyTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        initImageRVA()
        addLink()
        addImage()
    }

    private fun initImageRVA(){
        imageAdapter = ImageRVA()
        binding.imageRv.adapter = imageAdapter
    }

    private fun initTabLayout(){
        val tabTitles = listOf("집안일", "요리", "안전한 생활", "사기", "복지 \u00b7 정책")
        for (i in tabTitles.indices) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabTitles[i]))
        }

        setTabItemMargin(binding.tabLayout, 40)
        setSelectedTabTextStyleBold()
    }

    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {
        for (i in 0 until 3) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = marginEnd
                lp.height = -2
                Log.d("size", lp.height.toString() + lp.width.toString())
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }

    private fun setSelectedTabTextStyleBold(){
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout =
                    (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendard_bold)
                tabTextView.setTypeface(typeface, Typeface.NORMAL)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout =
                    (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendard_medium)
                tabTextView.setTypeface(typeface, Typeface.NORMAL)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun addLink(){
        binding.addLinkBtn.setOnClickListener {
            binding.addLinkBtn.visibility = View.GONE
            binding.inputUrlBtn.visibility = View.VISIBLE
        }
    }

    private fun addImage(){
        binding.addImageBtn.setOnClickListener {
            Log.d("됨?", "됨?")
            binding.imageRv.visibility = View.VISIBLE
            checkPermission()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) ->
            {
                showPermissionInfoDialog()
            }
            else -> {
                imageLoadLauncher.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun loadImage() {
        imageLoadLauncher.launch("image/*")
    }

    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setMessage("이미지를 가져오기 위해서, 외부 저장소 읽기 권한이 필요합니다.")
            setPositiveButton("동의") { _, _ ->
                requestReadExternalStorage()
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun requestReadExternalStorage() {
        Log.d("됨?", "됨3?")
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    private fun updateImages(uriList: List<Uri>){
        val images = uriList.map { Image(it) }
        val updatedImages = imageAdapter.currentList.apply { addAll(images) }
        Log.d("ui", "$updatedImages")
        imageAdapter.submitList(updatedImages)
    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }
}