package com.umc.ttoklip.presentation.hometown.communication.write

import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody

interface WriteCommunicationViewModel {
    val title: StateFlow<String>
    val content: StateFlow<String>
    val closePage: StateFlow<Boolean>
    val doneButtonActivated: StateFlow<Boolean>
    val images: StateFlow<List<Image>>
    val postId: StateFlow<Long>

    fun addImages(images: List<Image>)
    fun checkDone()
    fun doneButtonClick()

    fun setTitle(title: String)

    fun setBody(body: String)

    fun patchCommunication(title: String,
                           content: String,
                           deleteImageIds: List<Int>,
                           addImages: List<MultipartBody.Part?>,
                           url: String)

    fun setPostId(postId: Long)

}