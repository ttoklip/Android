package com.umc.ttoklip.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

fun Context.uriToFile(uri: Uri): File {
    val inputStream = contentResolver.openInputStream(uri) ?: return File("")
    val file = File(cacheDir, getFileName(uri))
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return file
}

fun Context.getFileName(uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "unknown"
}