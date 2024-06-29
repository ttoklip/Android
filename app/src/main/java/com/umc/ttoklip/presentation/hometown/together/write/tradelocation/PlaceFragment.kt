package com.umc.ttoklip.presentation.hometown.together.write.tradelocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentPlaceBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.AddressDetailActivity
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModel
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherViewModelImpl
import com.umc.ttoklip.util.showKeyboard
import com.umc.ttoklip.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class PlaceFragment : BaseFragment<FragmentPlaceBinding>(R.layout.fragment_place),
    OnMapReadyCallback {
    private var type = ""
    private lateinit var naverMap: NaverMap
    private var address: String = ""
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private var locationok: Boolean = false
    private val navigator by lazy {
        findNavController()
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: WriteTogetherViewModel by activityViewModels<WriteTogetherViewModelImpl>()
    private var isInputComplete = false

    /*private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val addressIntent = it.data
            addressIntent?.let { aIntent ->
                val address = aIntent.getStringExtra("address")
                val addressDetail = aIntent.getStringExtra("addressDetail")
                if (address != null) {
                    val lIntent = Intent(this, TradeLocationActivity::class.java)
                    lIntent.putExtra("address", address)
                    lIntent.putExtra("addressDetail", addressDetail)
                    setResult(1, lIntent)
                    finish()
                }

            }
        }*/

    override fun initView() {
        binding.viewModel = viewModel
        /*intent.getStringExtra("place")?.let {
            type = it
            binding.locationTitleTv.text = getString(R.string.my_hometown_address_title)
        }*/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        /*if (!hasPermission()) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        }*/
        initMapView()

        binding.locationNextBtn.setOnClickListener {
            /*val intent = Intent(requireContext(), AddressDetailActivity::class.java)
            intent.putExtra("place", "town")
            intent.putExtra("address", address)
            activityResultLauncher.launch(intent)*/
            if(isInputComplete){
                showToast("먼저 위치확인을 해주세요")
            } else {
                navigator.navigate(R.id.addressDetailFragment)
            }
        }

        binding.locationBackIb.setOnClickListener {
            navigator.navigateUp()
        }

        binding.additionalAddressBtn.setOnClickListener {
            if(!isInputComplete) {
                binding.currentLocationTv.showKeyboard()
                binding.currentLocationTv.setSelection(binding.currentLocationTv.text.length)
            } else {
                viewModel.fetchGeocoding(binding.currentLocationTv.text.toString())
            }
            viewModel.setIsInputComplete()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.address.collect {
                    Log.d("address", it.toString())
                }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isInputComplete.collect{
                    isInputComplete = it
                }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.latLng.collect {latLng ->
                    Log.d("latLng", latLng.toString())
                    naverMap.locationOverlay.position = latLng
                    naverMap.moveCamera(CameraUpdate.scrollTo(latLng))
                }
            }
        }

    }

    private fun initMapView() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.location_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.location_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val addressList: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addressList != null && addressList.isNotEmpty()) {
                val address: Address = addressList[0]
                val spliteAddr = address.getAddressLine(0).split(" ")
                this.address = spliteAddr[1] + " " + spliteAddr[2] + " " + spliteAddr[3]
            }
        } else {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                val spliteAddr = addresses[0].getAddressLine(0).split(" ")
                this.address = spliteAddr[1] + " " + spliteAddr[2] + " " + spliteAddr[3]
            }
        }
        //binding.currentLocationTv.text = address
        viewModel.setAddress(address)
    }

    private fun hasPermission(): Boolean {
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0
        binding.locationNowLocation.map = naverMap

        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationOverlay.isVisible = true
        getLastKnownLocation()
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
            getLastKnownLocation()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    naverMap.locationOverlay.position = latLng
                    naverMap.moveCamera(CameraUpdate.scrollTo(latLng))
                    getAddress(location.latitude, location.longitude)
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.d("getLastKnownLocation", exception.message.toString())
            }
    }
}