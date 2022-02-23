package com.ozcoin.cookiepang.data.user

import android.R.attr.bitmap
import android.graphics.Bitmap
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import com.ozcoin.cookiepang.utils.FormDataUtil
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun registrationUser(userEntity: UserEntity): NetworkResult<UserEntity> {
        return safeApiCall { apiService.registrationUser(userEntity) }
    }

    suspend fun getUser(userId: Int): NetworkResult<UserEntity> {
        return safeApiCall { apiService.getUser(userId) }
    }

    suspend fun isUserRegistration(walletAddress: String) = safeApiCall {
        apiService.login(LoginRequestParam(walletAddress))
    }

    suspend fun updateUserEntity(user: User) =
        safeApiCall {
            apiService.updateUser(
                user.userId.toDataUserId(),
                user.introduction,
                FormDataUtil.getImageBody(
                    "profilePicture",
                    convertBitmapToFile("profilePicture", user.updateThumbnailImg)
                ),
                FormDataUtil.getImageBody(
                    "backgroundPicture",
                    convertBitmapToFile("backgroundPicture", user.updateProfileBackgroundImg)
                )
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
