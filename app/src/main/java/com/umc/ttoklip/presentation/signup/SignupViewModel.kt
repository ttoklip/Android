package com.umc.ttoklip.presentation.signup

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.login.LoginLocalRequest
import com.umc.ttoklip.data.model.signup.VerifyRequest
import com.umc.ttoklip.data.repository.login.LoginRepositoryImpl
import com.umc.ttoklip.data.repository.naver.NaverRepositoryImpl
import com.umc.ttoklip.data.repository.signup.SignupRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val loginRepository: LoginRepositoryImpl,
    private val signupRepository: SignupRepositoryImpl,
    private val naverRepository: NaverRepositoryImpl,
    application: Application
) : AndroidViewModel(application) {

    val signupType=MutableStateFlow<String>("")
    val fail_message=MutableStateFlow<String>("")

    ///////////////////////////////fragment 1
    val name=MutableStateFlow<String>("")
    val birth=MutableStateFlow<String>("")
    val email=MutableStateFlow<String>("")
    val verifycheckok=MutableStateFlow<Boolean>(false)
    val verifySuccess=MutableStateFlow<Boolean>(false)

    fun verifyCheckOk(){
        viewModelScope.launch {
            verifycheckok.emit(true)
        }
    }
    fun verifySend(email:String){
        viewModelScope.launch {
            signupRepository.verifySend(VerifyRequest(email, ""))
        }
    }

    fun verifyCheck(email:String,code:String){
        viewModelScope.launch {
            signupRepository.verifyCheck(VerifyRequest(email,code))
                . onSuccess {
                    verifySuccess.emit(true)
                }
        }
    }

    ///////////////////////////////fragment 2
    val pw=MutableStateFlow<String>("")
    val repw=MutableStateFlow<String>("")
    val iddupcheckbtn=MutableStateFlow<Boolean>(false)
    val idok=MutableStateFlow<Boolean>(false)
    val pwSizeOk=MutableStateFlow<Boolean>(false)
    val pwok=MutableStateFlow<Boolean>(false)

    fun idDupCheckOk(){
        viewModelScope.launch {
            iddupcheckbtn.emit(true)
        }
    }
    fun checkId(id:String){
        viewModelScope.launch {
            signupRepository.checkId(id)
                .onSuccess {
                    idok.emit(true)
                }
        }
    }

    ///////////////////////////////fragment 4
    val nickcheckbtn=MutableStateFlow<Boolean>(false)
    val nickok = MutableStateFlow<Boolean>(false)
    var independentCareerok = MutableStateFlow<Boolean>(false)
    var interestok = MutableStateFlow<Boolean>(false)

    fun nickCheck(nick: String) {
        viewModelScope.launch {
            if(signupType.value=="local"){
                signupRepository.checkNicknameLocal(nick)
                    .onSuccess {
                        nickok.emit(true)
                    }.onFail {message->
                        nickok.emit(false)
                        fail_message.value=message
                    }
            }else{
                signupRepository.checkNickname(nick)
                    .onSuccess {
                        nickok.emit(true)
                    }.onFail {
                        nickok.emit(false)
                    }
            }
        }
    }
    fun nickcheckclick(){
       viewModelScope.launch {
           nickcheckbtn.emit(true)
       }
    }
    fun independentCheck(indendentok: Boolean) {
        viewModelScope.launch {
            independentCareerok.emit(indendentok)
        }
    }
    fun interestCheck(interestcheck: Boolean) {
        viewModelScope.launch {
            interestok.emit(interestcheck)
        }
    }

    private var _nickname = MutableStateFlow<String>("")
    val nickname: StateFlow<String>
        get() = _nickname
    private var _categories = MutableStateFlow<ArrayList<String>>(ArrayList<String>())
    val categories: StateFlow<ArrayList<String>>
        get() = _categories
    private var _profileImage = MutableStateFlow<String>("")
    val profileImage: StateFlow<String>
        get() = _profileImage
    private var _independenctYear = MutableStateFlow<Int>(0)
    val independenctYear: StateFlow<Int>
        get() = _independenctYear
    private var _independenctMonth = MutableStateFlow<Int>(0)
    val independenctMonth: StateFlow<Int>
        get() = _independenctMonth
    val agreeTermsOfService = MutableStateFlow<Boolean>(false)
    val agreePrivacyPolicy=MutableStateFlow<Boolean>(false)
    val agreeLocationService=MutableStateFlow<Boolean>(false)

    fun saveUserInfoAt4(
        uemail:String, upw:String, uoriginName:String,
//        ubirth:String,
        termsOfService:Boolean, privacyPolicy:Boolean, locationService:Boolean,
        unick: String,
        ucategories: ArrayList<String>,
        uprofileImage: String,
        uindependentYear: Int,
        uindependentMonth: Int
    ){
        email.value=uemail
        pw.value=upw
        name.value=uoriginName
//        birth.value=ubirth
        agreeTermsOfService.value=termsOfService
        agreePrivacyPolicy.value=privacyPolicy
        agreeLocationService.value=locationService
        _nickname.value = unick
        _categories.value = ucategories
        _profileImage.value = uprofileImage
        _independenctYear.value = uindependentYear
        _independenctMonth.value = uindependentMonth
        TtoklipApplication.prefs.setString("nickname", unick)
    }

    ///////////////////////////////fragment 5
    var street=MutableStateFlow<String>("")
    val saveok=MutableStateFlow<Boolean>(false)

    fun getAdmcode(coord: com.naver.maps.geometry.LatLng){
        viewModelScope.launch {
            naverRepository.getAdmcode("${coord.longitude}, ${coord.latitude}")
                .onSuccess {
                    val address=it.results.first()
                    street.value = address.region.area1.name + " " +
                            (address.region.area2?.name?.let { it + " " } ?: "") +
                            (address.region.area3?.name?.let { it + " " } ?: "") +
                            (address.region.area4?.name ?: "")
                }
        }
    }

    fun savePrivacy(type:String) {
        viewModelScope.launch {
            //이미지
            var fileToUpload:MultipartBody.Part
            if(profileImage.value.isEmpty()){
                fileToUpload=MultipartBody.Part.createFormData("profileImage","defaultImage",
                    RequestBody.create(
                        "image/*".toMediaTypeOrNull(),
                        getApplication<TtoklipApplication>().resources.openRawResource(
                            R.raw.profile_image_default).readBytes()))
            }else{
                val file = File(profileImage.value)
                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                fileToUpload = MultipartBody.Part.createFormData("profileImage", file.name, requestBody)
            }

            //카테고리
            val cate=ArrayList<MultipartBody.Part>()
            for (c in categories.value) {
                cate.add(MultipartBody.Part.createFormData("categories",c))
            }

            //기타 정보
            val requestMap= hashMapOf<String, RequestBody>()
            if(type=="local"){
                requestMap["email"]=email.value.toRequestBody("text/plain".toMediaTypeOrNull())
                requestMap["password"]=pw.value.toRequestBody("text/plain".toMediaTypeOrNull())
                requestMap["originName"]=name.value.toRequestBody("text/plain".toMediaTypeOrNull())
                //생일 추가되면 추가
                requestMap["agreeTermsOfService"]=agreeTermsOfService.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                requestMap["agreePrivacyPolicy"]=agreePrivacyPolicy.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                requestMap["agreeLocationService"]=agreeLocationService.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            }
            requestMap["street"] = street.value.toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["nickname"] = nickname.value.toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["independentYear"] = independenctYear.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["independentMonth"] = independenctMonth.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            if(type=="local"){
                signupRepository.savePrivacyLocal(fileToUpload, requestMap, cate)
                    .onSuccess {
                        Log.i("USERSAVE", "성공")
                        saveok.emit(true)
                    }.onFail {
                        Log.i("USERSAVE", "실패")
                    }.onError {
                        Log.d("USERSAVE ERROR", it.toString())
                    }
            }else{
                signupRepository.savePrivacy(fileToUpload, requestMap, cate)
                    .onSuccess {
                        Log.i("USERSAVE", "성공")
                        saveok.emit(true)
                    }.onFail {
                        Log.d("USERSAVE", "실패")
                    }.onError {
                        Log.d("USERSAVE ERROR", it.toString())
                    }
            }
        }
    }

    fun postLocalLogin(request: LoginLocalRequest, context: Context){
        viewModelScope.launch {
            loginRepository.postLoginLocal(request)
                .onSuccess {
                    TtoklipApplication.prefs.setString("jwt",it.jwtToken)
                    TtoklipApplication.prefs.setBoolean("isFirstLogin",it.ifFirstLogin)
                }.onFail {
                    Toast.makeText(context,"회원가입 실패", Toast.LENGTH_LONG).show()
                    Log.d("LOGIN-API", "local login 실패")
                }
        }
    }
}

