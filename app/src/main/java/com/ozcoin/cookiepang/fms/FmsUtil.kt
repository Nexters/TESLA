package com.ozcoin.cookiepang.fms

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

object FmsUtil {
    suspend fun getToken() {
        suspendCancellableCoroutine<String> { continuation ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    continuation.resume("")
                    return@addOnCompleteListener
                }
                val token = task.result
                Timber.d("fcm token: $token")
                continuation.resume(token)
            }
        }
    }
}
