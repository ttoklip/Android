package com.umc.ttoklip.module

import android.util.Log
import com.google.gson.Gson
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.ErrorResponse
import com.umc.ttoklip.di.NetworkModule
import retrofit2.Response

suspend fun <T : Any, R : Any> handleApi(
    execute: suspend () -> Response<T>,
    mapper: (T) -> R
): NetworkResult<R> {
    if (TtoklipApplication.isOnline().not()) {
        return NetworkResult.Error(Exception(NetworkModule.NETWORK_EXCEPTION_OFFLINE_CASE))
    }

    return try {
        val response = execute()
        val body = response.body()
        Log.d("body", body.toString())
        if (response.isSuccessful) {
            body?.let {
                NetworkResult.Success(mapper(it))
            } ?: run {
                throw NullPointerException(NetworkModule.NETWORK_EXCEPTION_BODY_IS_NULL)
            }
        } else {
            if (response.code() == 400) {
                getFailDataMessage(
                    Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    ).message
                )
            } else {
                getFailDataResult(body, response)
            }
        }
    } catch (e: Exception) {
        NetworkResult.Error(e)
    }
}


private fun <T : Any> getFailDataResult(body: T?, response: Response<T>) = body?.let {
    NetworkResult.Fail(statusCode = response.code(), message = it.toString())
} ?: run {
    NetworkResult.Fail(statusCode = response.code(), message = response.message())
}

private fun getFailDataMessage(message: String) = NetworkResult.Fail(400, message)