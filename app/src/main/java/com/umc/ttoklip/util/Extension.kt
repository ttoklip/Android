package com.umc.ttoklip.util

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
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

fun EditText.showKeyboard(){
    this.requestFocus()
    val inputMethodManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.showToast(text: String){
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
fun String.tabTextToCategory(): String {
    return when (this) {
        "집안일" -> WriteHoneyTipActivity.Category.HOUSEWORK.toString()
        "레시피" -> WriteHoneyTipActivity.Category.RECIPE.toString()
        "안전한생활" -> WriteHoneyTipActivity.Category.SAFE_LIVING.toString()
        else -> WriteHoneyTipActivity.Category.WELFARE_POLICY.toString()
    }
}

fun String.isValidUri(): Boolean {
    return try {
        val uri = Uri.parse(this)
        // 기본적으로 scheme이나 path가 비어있지 않은지를 체크
        uri.scheme != null && uri.scheme!!.isNotEmpty() && uri.path != null
    } catch (e: Exception) {
        false
    }
}