package com.umc.ttoklip.presentation.hometown

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityPlaceBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.tradelocation.TradeLocationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class PlaceActivity : BaseActivity<ActivityPlaceBinding>(R.layout.activity_place),
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

    private val activityResultLauncher: ActivityResultLauncher<Intent> =
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
        }

    override fun initView() {
        intent.getStringExtra("place")?.let {
            type = it
            binding.locationTitleTv.text = getString(R.string.my_hometown_address_title)
        }
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        }
        initMapView()

        binding.locationNextBtn.setOnClickListener {
            val intent = Intent(this, AddressDetailActivity::class.java)
            intent.putExtra("place", "town")
            intent.putExtra("address", address)
            activityResultLauncher.launch(intent)
        }
    }

    override fun initObserver() {

    }

    private fun initMapView() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.location_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.location_map, it).commit()
            }
        mapFragment.getMapAsync(this)
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
        binding.currentLocationTv.text = address
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

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0
        binding.locationNowLocation.map = naverMap

//        locationSource =
        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
//        naverMap.locationOverlay.subIcon =
//            OverlayImage.fromResource(com.naver.maps.map.R.drawable.navermap_location_overlay_icon)

        naverMap.addOnLocationChangeListener {
            getAddress(
                it.latitude,
                it.longitude
            )
            locationok = true
        }
    }
}