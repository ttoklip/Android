package com.umc.ttoklip.presentation.mypage.manageinfo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyHomeTownAddressBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.util.setOnSingleClickListener

class MyHomeTownAddressFragment :
    BaseFragment<FragmentMyHomeTownAddressBinding>(R.layout.fragment_my_home_town_address) {
    private val viewModel: ManageMyInfoViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { (permission, isGranted) ->
            when {
                isGranted -> {
                    //getLastKnownLocation()
                }
                else -> {
                    // 권한이 거부된 경우에 대한 처리
                    Log.d("PermissionDenied", "Permission $permission was denied")
                }
            }
        }
    }

    override fun initObserver() {
        // Observe ViewModel live data here
    }

    override fun initView() {
        binding.vm = viewModel
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // 권한 요청 로직을 수정하여, 권한이 부여되지 않은 경우에만 요청
        val requestList = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED
        }

        if (requestList.isNotEmpty()) {
            requestPermissionLauncher.launch(requestList.toTypedArray())
        } else {
            // 모든 권한이 이미 부여된 경우
            //getLastKnownLocation()
        }

        binding.gpsBtn.setOnSingleClickListener {
//            navigator.navigate(R.id.action_myHomeTownAddressFragment_to_myInfoLocationFragment)
        }

        binding.myHometownAddressBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }

        binding.finishAddressBtn.setOnSingleClickListener {
            val inputDirectText = binding.inputDirectAddressEt.text.toString()
            if(inputDirectText.isNotEmpty()){
                viewModel.setAddress(inputDirectText, true)
            }
            viewModel.setIsAddressEdit(true)
            navigator.popBackStack(R.id.manageMyInfoFragment, false)
        }
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        viewModel.fetchReverseGeocoding(latLng, "json")
                    } else {
                        Log.d("getLastKnownLocation", "Location is null")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("getLastKnownLocation", exception.message.toString())
                }
        } else {
            // 권한이 여전히 부여되지 않은 경우
            Log.d("getLastKnownLocation", "Location permission not granted")
        }
    }

    companion object {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}
