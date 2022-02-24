package com.ozcoin.cookiepang.data.user

import android.graphics.Bitmap
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import com.ozcoin.cookiepang.utils.BitmapRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun registrationUser(user: User): NetworkResult<UserEntity> {
        return safeApiCall { apiService.registrationUser(user.toData()) }
    }

    suspend fun getUser(userId: Int): NetworkResult<UserEntity> {
        return safeApiCall { apiService.getUser(userId) }
    }

    suspend fun isUserRegistration(walletAddress: String) = safeApiCall {
        apiService.login(LoginRequestParam(walletAddress))
    }

    suspend fun updateUserEntity(user: User) =
        safeApiCall {
            val profilePicture = user.updateThumbnailImg?.let {
                MultipartBody.Part.createFormData(
                    "image",
                    "profilePicture",
                    BitmapRequestBody(it)
                )
            } ?: kotlin.run {
                val content = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
                MultipartBody.Part.createFormData("profilePicture", "", content)
            }

            val backgroundPicture = user.updateProfileBackgroundImg?.let {
                MultipartBody.Part.createFormData(
                    "image",
                    "backgroundPicture",
                    BitmapRequestBody(it)
                )
            } ?: kotlin.run {
                val content = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
                MultipartBody.Part.createFormData("backgroundPicture", "", content)
            }

            apiService.updateUser(
                user.userId.toDataUserId(),
                user.introduction,
                profilePicture,
                backgroundPicture
            )
        }

    private fun convertBitmapToFile(name: String, bitmap: Bitmap?): File {
        val file: File

        if (bitmap != null) {
            file = File(name)

            kotlin.runCatching {
                file.createNewFile()

                val bitmapData = ByteArrayOutputStream().use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    it.toByteArray()
                }

                FileOutputStream(file).use {
                    it.write(bitmapData)
                    it.flush()
                    it.close()
                }
            }.onFailure {
                Timber.e(it)
            }
        } else {
            file = File("")
        }

        return file
    }
}
