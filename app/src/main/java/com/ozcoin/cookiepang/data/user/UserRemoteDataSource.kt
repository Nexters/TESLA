package com.ozcoin.cookiepang.data.user

import android.content.Context
import android.graphics.Bitmap
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    @ApplicationContext private val appContext: Context,
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
            val introduction = MultipartBody.Part.createFormData("introduction", user.introduction)
            val profilePicture = getImageMultiPart(
                "profilePicture",
                convertBitmapToFile("profilePicture", user.updateThumbnailImg)
            )
            val backgroundPicture = getImageMultiPart(
                "backgroundPicture",
                convertBitmapToFile("backgroundPicture", user.updateProfileBackgroundImg)
            )

            apiService.updateUser(
                user.userId.toDataUserId(),
                introduction,
                profilePicture,
                backgroundPicture
            )
        }

    private fun getImageMultiPart(name: String, file: File): MultipartBody.Part {
        val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, file.name, reqFile)
    }

    private fun convertBitmapToFile(name: String, bitmap: Bitmap?): File {
        val file: File = File(appContext.cacheDir, name)

        kotlin.runCatching {
            if (!file.exists()) file.createNewFile()

            if (bitmap != null) {
                val bitmapData = ByteArrayOutputStream().use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                    it.toByteArray()
                }

                FileOutputStream(file).use {
                    it.write(bitmapData)
                    it.flush()
                }
            }
        }.onFailure {
            Timber.e(it)
        }

        return file
    }
}
