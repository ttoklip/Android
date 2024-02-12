package com.umc.ttoklip.data.model.honeytip

import android.net.Uri
import java.io.Serializable

data class HoneyTip(
    var title: String,
    var content: String,
    val images: Array<String>?,
    val uri: String
): Serializable
