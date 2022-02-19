package com.ozcoin.cookiepang.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import timber.log.Timber

object BitmapUtil {

    fun decodeBitmap(context: Context, uri: Uri): Bitmap? {
        val bitmap = kotlin.runCatching {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }.onFailure {
            Timber.e("decodeBitmap Failure")
            Timber.e(it)
        }.getOrNull()
        val degrees = getOrientation(context, uri).toFloat()

        return bitmap?.let { rotateImage(it, degrees) }
    }

    private fun getOrientation(context: Context, photoUri: Uri?): Int {
        val cursor: Cursor? = context.contentResolver.query(
            photoUri!!,
            arrayOf(MediaStore.Images.ImageColumns.ORIENTATION),
            null,
            null,
            null
        )
        if (cursor == null || cursor.count != 1) {
            return 90 // Assuming it was taken portrait
        }

        cursor.moveToFirst()
        return cursor.getInt(0).also {
            Timber.d("it($photoUri) degree $it")
            cursor.close()
        }
    }

    private fun rotateImage(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
