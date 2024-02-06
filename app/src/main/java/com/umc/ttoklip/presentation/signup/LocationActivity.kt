package com.umc.ttoklip.presentation.signup

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityLocationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import java.util.Locale

class LocationActivity :
    BaseActivity<ActivityLocationBinding>(R.layout.activity_location),
    OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var uiSetting: UiSettings
    private lateinit var address: String
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var range: String


    override fun initView() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        }
        initMapView()

        range = getString(R.string.range_500m)
        binding.locationRangeDescTv.text =
            getString(R.string.range_setting_format, range)

        binding.locationRange1Tv.setOnClickListener { setRange500m() }
        binding.locationRange2Tv.setOnClickListener { setRange1km() }
        binding.locationRange3Tv.setOnClickListener { setRange15km() }

        binding.locationRangeBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
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

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0
        uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        binding.locationNowLocation.map = naverMap

        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.locationOverlay.subIcon =
            OverlayImage.fromResource(com.naver.maps.map.R.drawable.navermap_location_overlay_icon)

        naverMap.addOnLocationChangeListener {
            getAddress(
                it.latitude,
                it.longitude
            )
        }
    }

    private fun hasPermission(): Boolean {
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun initMapView() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.location_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.location_map, it).commit()
            }
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(applicationContext, Locale.KOREAN)
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
        binding.locationMytownDetailTv.text = address
    }

    override fun initObserver() {
    }

    private fun setRange15km() {
        range = getString(R.string.range_1_5km)
        binding.locationRangeBar.progress = RANGE_15kM_PROGRESS
        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
    }

    private fun setRange1km() {
        range = getString(R.string.range_1km)
        binding.locationRangeBar.progress = RANGE_1KM_PROGRESS
        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
    }

    private fun setRange500m() {
        range = getString(R.string.range_500m)
        binding.locationRangeBar.progress = RANGE_500M_PROGRESS
        binding.locationRange1Tv.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.locationRange2Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRange3Tv.setTextColor(ContextCompat.getColor(this, R.color.gray40))
        binding.locationRangeDescTv.text = getString(R.string.range_setting_format, range)
    }

    companion object {
        private const val RANGE_500M_PROGRESS = 33
        private const val RANGE_1KM_PROGRESS = 67
        private const val RANGE_15kM_PROGRESS = 100
    }
}