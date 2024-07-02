package com.umc.ttoklip.presentation.login

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.databinding.ActivityLogin2Binding
import com.umc.ttoklip.databinding.ActivityLoginBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.search.dialog.BottomDialogSearchFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLogin2Binding>(R.layout.activity_login2) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        loginActivity=this
        binding.loginNaverBtn.setOnClickListener {
            naverLogin()
        }
        binding.loginKakaoBtn.setOnClickListener {
            kakaoLogin()
        }
        binding.loginLocalSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.putExtra("loginWay","local")
            startActivity(intent)
        }
    }

    companion object{
        var loginActivity:LoginActivity?=null
    }

    private fun naverLogin(){
        viewModel.setIsSocialLogin(true)
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("NAVER-LOGIN", "errorCode:$errorCode, errorDesc:$errorDescription")
            }

            override fun onSuccess() {
                Log.i("NAVER-LOGIN", "로그인 성공")
                val loginRequest = LoginRequest("${NaverIdLoginSDK.getAccessToken()}", "naver")
                viewModel.postLogin(loginRequest)
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
        //네이버 토큰 임시확인용
        Log.i("NAVER-LOGIN","${NaverIdLoginSDK.getAccessToken()}")
    }
    private fun kakaoLogin(){
        viewModel.setIsSocialLogin(true)
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("카카오로그인", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("카카오로그인", "카카오계정으로 로그인 성공")
                val loginRequest = LoginRequest("${token.accessToken}", "kakao")
                viewModel.postLogin(loginRequest)
            }
        }

        //카톡 연결이 없으면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("카카오로그인", "카카오톡으로 로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        //디바이스 권한 요청 화면에서 로그인 취소할 경우
                        //의도적 로그인 취소로 확인 > 로그인 시도 없이 back 처리
                        return@loginWithKakaoTalk
                    }
                    //카톡 연결이 없으면 카카오계정으로 로그인
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i("카카오로그인", "카카오톡으로 로그인 성공")
                    val loginRequest = LoginRequest("${token.accessToken}", "kakao")
                    //임시 토큰확인용
                    Log.i("KAKAO-LOGIN","${token.accessToken}")
                    viewModel.postLogin(loginRequest)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun startactivity() {
//            회원가입 만들기용 임시
//            val intent = Intent(this, SignupActivity::class.java)
//            intent.putExtra("loginWay","SNS")
//            startActivity(intent)
//            Log.i("JWT",TtoklipApplication.prefs.getString("jwt",""))

            //이쪽이 진짜
        if (viewModel.isFirstLogin.value) {
            val intent = Intent(this, SignupActivity::class.java)
            if(viewModel.isSocialLogin.value){
                //3단계부터 회원가입
                intent.putExtra("loginWay","SNS")
            }else{
                //1단계부터 회원가입
                intent.putExtra("loginWay","local")
            }
            startActivity(intent)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun cancelLogin(){
        viewModel.initIsLogin()
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLogin.collect {
                        if (it) startactivity()
                    }
                }
            }
        }
    }

}