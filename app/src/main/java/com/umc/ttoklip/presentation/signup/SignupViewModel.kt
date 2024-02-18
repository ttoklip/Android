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

    private val _nickcheckbtn=MutableStateFlow<Boolean>(false)
    val nickcheckbtn:StateFlow<Boolean>
        get() = _nickcheckbtn
    private val _nickok = MutableStateFlow<Boolean>(false)
    val nickok: StateFlow<Boolean>
        get() = _nickok
    private var _independentCareerok = MutableStateFlow<Boolean>(false)
    val independentCareerok: StateFlow<Boolean>
        get() = _independentCareerok
    private var _interestok = MutableStateFlow<Boolean>(false)
    val interestok: StateFlow<Boolean>
        get() = _interestok

    fun nickCheck(nick: String) {
        viewModelScope.launch {
            signupRepository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check", "성공")
                    _nickok.value = true
                }.onFail {
                    Log.d("nick check", "실패")
                    _nickok.value = false
                }
        }
    }
    fun nickcheckclick(){
       viewModelScope.launch {
           _nickcheckbtn.value=true
       }
    }

    fun independentCheck(indendentok: Boolean) {
        viewModelScope.launch {
            _independentCareerok.value = indendentok
        }
    }

    fun interestCheck(interestok: Boolean) {
        viewModelScope.launch {
            _interestok.value = interestok
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
        unick: String,
        ucategories: ArrayList<String>,
        uprofileImage: String,
        uindependentYear: Int,
        uindependentMonth: Int
    ){
        _nickname.value = unick
        _categories.value = ucategories
        _profileImage.value = uprofileImage
        _independenctYear.value = uindependentYear
        _independenctMonth.value = uindependentMonth
        TtoklipApplication.prefs.setString("nickname", unick)
    }

    private var street: String = ""
    fun saveUserStreet(ustreet: String) {
        street = ustreet
    }

    fun savePrivacy() {
        viewModelScope.launch {
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

            val cate=ArrayList<MultipartBody.Part>()
            for (c in categories.value) {
                cate.add(MultipartBody.Part.createFormData("categories",c))
            }

            val requestMap= hashMapOf<String, RequestBody>()
            requestMap["street"] = street.toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["nickname"] = nickname.value.toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["independentYear"] = independenctYear.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            requestMap["independentMonth"] = independenctMonth.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            signupRepository.savePrivacy(fileToUpload, requestMap,cate)

//            signupRepository.savePrivacy(SignupRequest(
//                street,nickname.value,categories.value,profileImage.value,independenctYear.value,independenctMonth.value
//            ))
                .onSuccess {
                    Log.i("USERSAVE", "성공")
                }.onFail {
                    Log.d("USERSAVE", "실패")
                }.onError {
                    Log.d("USERSAVE ERROR", it.toString())
                }

        }
    }
}

