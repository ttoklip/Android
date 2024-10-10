package com.umc.ttoklip.presentation.signup.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.login.LoginLocalRequest
import com.umc.ttoklip.databinding.ActivityLocationBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.login.LoginActivity
import com.umc.ttoklip.presentation.mypage.manageinfo.MyHomeTownAddressFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import com.umc.ttoklip.presentation.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationActivity :
    BaseActivity<ActivityLocationBinding>(R.layout.activity_location),
    OnMapReadyCallback {

    private val viewModel: SignupViewModel by viewModels()

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var uiSetting: UiSettings
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 5000
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var locationok: Boolean = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { (permission, isGranted) ->
            when {
                isGranted -> {
                    getLastKnownLocation()
                    locationok = true
                }
                else -> {
                    // 권한이 거부된 경우에 대한 처리
                    Log.d("PermissionDenied", "Permission $permission was denied")
                }
            }
        }
    }


    override fun initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val requestList = PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        if (requestList.isNotEmpty()) {
            requestPermissionLauncher.launch(requestList.toTypedArray())
        } else {
            getLastKnownLocation()
            locationok = true
        }
        initMapView()

        binding.locationBackIb.setOnClickListener {
            finish()
        }

        binding.locationNextBtn.setOnSingleClickListener {
            if (locationok) {
                val bundle = intent.getBundleExtra("userInfo")
                if (bundle != null) {
                    val type=bundle.getString("signupType")
                    viewModel.saveUserInfoAt4(
                        bundle.getString("email")?:"",
                        bundle.getString("password")?:"",
                        bundle.getString("originName")?:"",
//                        bundle.getString("birth")?:"",
                        bundle.getBoolean("agreeTermsOfService"),
                        bundle.getBoolean("agreePrivacyPolicy"),
                        bundle.getBoolean("agreeLocationService"),
                        bundle.getString("nickname")!!,
                        bundle.getStringArrayList("interest")!!,
                        bundle.getString("imageUri")!!,
                        bundle.getInt("independentCareerYear"),
                        bundle.getInt("independentCareerMonth"))
                    if(!viewModel.agreePrivacyPolicy.value||
                        !viewModel.agreeTermsOfService.value||
                        !viewModel.agreeLocationService.value){
                        Toast.makeText(this,"동의하지 않은 약관이 있습니다.",Toast.LENGTH_LONG).show()
                    }else{
                        viewModel.savePrivacy(type!!)
                    }
                }
            }
        }
    }

    // 클릭 리스너 디바운스
    inline fun View.setOnSingleClickListener(
        delay: Long = 1000L,
        crossinline block: (View) -> Unit,
    ) {
        var previousClickedTime = 0L
        setOnClickListener { view ->
            val clickedTime = System.currentTimeMillis()
            if (clickedTime - previousClickedTime >= delay) {
                block(view)
                previousClickedTime = clickedTime
            }
        }
    }

    override fun onMapReady(p0: NaverMap) {
        this.naverMap = p0
        uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        binding.locationNowLocation.map = naverMap

        locationSource =FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.getLegalcode(LatLng(location.latitude, location.longitude))
                        updateMapWithLocation(location.latitude,location.longitude)
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

    private fun updateMapWithLocation(latitude: Double,longitude: Double) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
        naverMap.moveCamera(cameraUpdate)
        naverMap.locationOverlay.isVisible = true
        naverMap.locationOverlay.position = LatLng(latitude,longitude)
    }

    private fun nextok() {
        if (locationok) {
            binding.locationNextBtn.isClickable = true
            binding.locationNextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
            binding.locationNextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
        } else {
            binding.locationNextBtn.isClickable = false
            binding.locationNextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.locationNextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
        }
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
        viewModel.getLegalcode(LatLng(latitude, longitude))
    }

    private fun startLogin(){
        val bundle = intent.getBundleExtra("userInfo")
        if(bundle!!.getString("signupType")=="local") {
            viewModel.postLocalLogin(
                LoginLocalRequest(viewModel.email.value, viewModel.pw.value),
                this
            )
        }else{
            startActivity()
        }
    }
    private fun startActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        TtoklipApplication.prefs.setBoolean("isFirstLogin", false)
        val loginActivity=LoginActivity.loginActivity
        loginActivity?.finish()
        val signupActivity= SignupActivity.signupActivity
        signupActivity?.finish()
        finish()
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveok.collect {
                    if (it) {
                        Toast.makeText(this@LocationActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG)
                            .show()
                        startLogin()
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginok.collect{
                    if(it){
                        startActivity()
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.street.collect{
                    binding.locationMytownDetailTv.text=it
                }
            }
        }
    }
}