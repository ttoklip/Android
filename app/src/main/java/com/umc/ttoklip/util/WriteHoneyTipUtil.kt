package com.umc.ttoklip.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class WriteHoneyTipUtil(private val context: Context) {
    fun convertUriToMultiBody(images: List<Uri>): Array<MultipartBody.Part> {
        val imageParts: MutableList<MultipartBody.Part> = mutableListOf()
        if (images.isNotEmpty()) {
            for (i in images.indices) {
                //Log.d("images", images[i].toString())
                val imagePath = images[i]
                val path = convertResizeImage(imagePath)
                Log.d("path", imagePath.lastPathSegment.toString())
                val imageFile = convertUriToJpegFile(context, imagePath, imagePath.lastPathSegment.toString())
                if (imageFile == null) {
                    null
                } else {
                    val imageRequestBody =
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData(
                        "images",
                        imageFile.name,
                        imageRequestBody
                    )
                    imageParts.add(imagePart)
                }
            }
        }
        return imageParts.toTypedArray()
    }

    private fun convertResizeImage(imageUri: Uri): Uri {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        val resizedBitmap =
            Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

        val tempFile = File.createTempFile("resized_image", ".jpg", context.cacheDir)
        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        return Uri.fromFile(tempFile)
    }

    private fun convertUriToJpegFile(context: Context, uri: Uri, targetFilename: kotlin.String): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputFile = File(context.cacheDir, "$targetFilename.jpeg")
        Log.d("outfile", outputFile.toString())

        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // 4KB buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }

        return if (outputFile.exists()) outputFile else null
    }

    //수정하기 관련
}