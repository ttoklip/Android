package com.umc.ttoklip.presentation.mypage.manageinfo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyInfoLocationBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.util.setOnSingleClickListener
import java.util.Locale

class MyInfoLocationFragment : BaseFragment<FragmentMyInfoLocationBinding>(R.layout.fragment_my_info_location) ,
    OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var uiSetting: UiSettings
    private var address: String=""
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
//    private var range = "500m"
//    private lateinit var circle: CircleOverlay
    private lateinit var marker: Marker
    private val navigator by lazy {
        findNavController()
    }

    private var locationok: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: ManageMyInfoViewModel by activityViewModels()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { (permission, isGranted) ->
            when {
                isGranted -> {
                    getLastKnownLocation()
                }
                else -> {
                    // 권한이 거부된 경우에 대한 처리
                    Log.d("PermissionDenied", "Permission $permission was denied")
                }
            }
        }
    }


    override fun initView() {
        binding.vm = viewModel
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // 권한 요청 로직을 수정하여, 권한이 부여되지 않은 경우에만 요청
        val requestList = MyHomeTownAddressFragment.permissions.filter { permission ->
            ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED
        }

        if (requestList.isNotEmpty()) {
            requestPermissionLauncher.launch(requestList.toTypedArray())
        } else {
            // 모든 권한이 이미 부여된 경우
            getLastKnownLocation()
        }
        initMapView()
//        circle = CircleOverlay()
        marker= Marker()

//        range = getString(R.string.range_500m)
//        binding.locationRangeDescTv.text =
//            getString(R.string.range_setting_format, range)
//
//        binding.locationRange1Tv.setOnSingleClickListener { setRange500m() }
//        binding.locationRange2Tv.setOnSingleClickListener { setRange1km() }
//        binding.locationRange3Tv.setOnSingleClickListener { setRange15km() }
//
//        binding.locationRangeBar.setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                if (progress <= RANGE_500M_PROGRESS) {
//                    setRange500m()
//                } else if (progress <= RANGE_1KM_PROGRESS) {
//                    setRange1km()
//                } else {
//                    setRange15km()
//                }
//                if (locationok) setcircle()
//            }
//            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                seekBar?.let {
//                    if (it.progress <= RANGE_500M_PROGRESS) {
//                        it.progress = RANGE_500M_PROGRESS
//                        setRange500m()
//                    } else if (it.progress <= RANGE_1KM_PROGRESS) {
//                        it.progress = RANGE_1KM_PROGRESS
//                        setRange1km()
//                    } else {
//                        it.progress = RANGE_15kM_PROGRESS
//                        setRange15km()
//                    }
//                }
//                if (locationok) setcircle()
//            }
//        })

        binding.locationBackIb.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }

    private var locationX:Int=0
    private var locationY:Int=0
    private fun sendResult() {
        val intent= Intent()
        intent.putExtra("location",address)
        intent.putExtra("locationX",locationX)
        intent.putExtra("locationY",locationY)

    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0
        uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        binding.locationNowLocation.map = naverMap

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
//        setcircle()
        locationok = true

        /*naverMap.setOnMapClickListener { pointF, latLng ->
            setlocation(latLng.latitude,latLng.longitude)
        }*/
    }

    private fun setlocation(latitude:Double,longitude:Double){
        this.address=""
        getAddress(latitude, longitude)
//        circle.center = LatLng(latitude, longitude)
        marker.position= LatLng(latitude,longitude)
        locationok = true
//        setcircle()
        marker.map=null
        marker.map=naverMap
    }

//    private fun setcircle() {
//        if (range == "500m") circle.radius = 500.0
//        else if (range == "1km") circle.radius = 1000.0
//        else circle.radius = 1500.0
//        circle.color = ContextCompat.getColor(requireContext(), R.color.yellow_trans30)
//        circle.map = naverMap
//    }

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
    private fun initMapView() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.location_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.location_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        viewModel.getAdmcode(LatLng(latitude, longitude))
//        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            val addressList: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
//            if (addressList != null && addressList.isNotEmpty()) {
//                val address: Address = addressList[0]
//                val spliteAddr = address.getAddressLine(0).split(" ")
//                for(i in 1.. spliteAddr.size-1){
//                    this.address=this.address+spliteAddr[i]+" "
//                }
//            }
//        } else {
//            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
//            if (addresses != null) {
//                val spliteAddr = addresses[0].getAddressLine(0).split(" ")
//                for(i in 1.. spliteAddr.size-1){
//                    this.address=this.address+spliteAddr[i]+" "
//                }
//            }
//        }
//        if (address.isNotEmpty()){
//            binding.locationMytownDetailTv.text = address
//        }
    }

    override fun initObserver() {
    }

//    private fun setRange15km() {
//        range = getString(R.string.range_1_5km)
//        binding.locationRangeBar.progress = RANGE_15kM_PROGRESS
//        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
//    }
//
//    private fun setRange1km() {
//        range = getString(R.string.range_1km)
//        binding.locationRangeBar.progress = RANGE_1KM_PROGRESS
//        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
//    }
//
//    private fun setRange500m() {
//        range = getString(R.string.range_500m)
//        binding.locationRangeBar.progress = RANGE_500M_PROGRESS
//        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray40))
//        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
//    }

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
                        viewModel.getAdmcode(LatLng(location.latitude, location.longitude))
//                        viewModel.fetchReverseGeocoding(latLng, "json")
//                        circle.center = latLng
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
        private const val RANGE_500M_PROGRESS = 33
        private const val RANGE_1KM_PROGRESS = 67
        private const val RANGE_15kM_PROGRESS = 100
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}