package com.ozcoin.cookiepang.utils

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

object FmsUtil {

    suspend fun getFcmToken(): String {
        var fcmToken = ""

        while (fcmToken.isEmpty()) {
            fcmToken = requestFcmToken()
        }

        return fcmToken.also { Timber.d("fcmToken : $fcmToken") }
    }

    private suspend fun requestFcmToken(): String = withContext(Dispatchers.IO) {
        Timber.d("requestFcmToken(${Thread.currentThread().name})")
        val res = CompletableDeferred<String>()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful)
                Timber.d("Fetching FCM registration token failed, $task.exception")

            res.complete(task.result)
        }

        res.await()
    }
}
