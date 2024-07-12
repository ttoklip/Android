package com.umc.ttoklip.presentation.mypage.manageinfo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyHomeTownAddressBinding
import com.umc.ttoklip.presentation.base.BaseFragment

class MyHomeTownAddressFragment : BaseFragment<FragmentMyHomeTownAddressBinding>(R.layout.fragment_my_home_town_address) {
    private val viewModel: ManageMyInfoViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun initObserver() {

    }

    override fun initView() {
        binding.vm = viewModel
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastKnownLocation()
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    viewModel.fetchReverseGeocoding(latLng, "json")
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.d("getLastKnownLocation", exception.message.toString())
            }
    }
}