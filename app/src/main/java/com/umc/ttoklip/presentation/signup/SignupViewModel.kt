package com.umc.ttoklip.presentation.signup

import android.app.Application
import android.content.res.AssetManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.VerifyRequest
import com.umc.ttoklip.data.repository.signup.SignupRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl, application: Application
) : AndroidViewModel(application) {

    val signupType=MutableStateFlow<String>("")

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
                        Log.i("nick check", "성공")
                        nickok.emit(true)
                    }.onFail {
                        Log.d("nick check", "실패")
                        nickok.emit(false)
                    }
            }else{
                signupRepository.checkNickname(nick)
                    .onSuccess {
                        Log.i("nick check", "성공")
                        nickok.emit(true)
                    }.onFail {
                        Log.d("nick check", "실패")
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

    fun saveUserInfoAt4(
        uemail:String,
        upw:String,
        uoriginName:String,
        ubirth:String,
        unick: String,
        ucategories: ArrayList<String>,
        uprofileImage: String,
        uindependentYear: Int,
        uindependentMonth: Int
    ){
        email.value=uemail
        pw.value=upw
        name.value=uoriginName
        birth.value=ubirth
        _nickname.value = unick
        _categories.value = ucategories
        _profileImage.value = uprofileImage
        _independenctYear.value = uindependentYear
        _independenctMonth.value = uindependentMonth
        TtoklipApplication.prefs.setString("nickname", unick)
    }

    ///////////////////////////////fragment 5
    private var street: String = ""
    val saveok=MutableStateFlow<Boolean>(false)
    fun saveUserStreet(ustreet: String) {
        street = ustreet
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
            }
            requestMap["street"] = street.toRequestBody("text/plain".toMediaTypeOrNull())
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
}

