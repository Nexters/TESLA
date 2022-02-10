package com.ozcoin.cookiepang.fms

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MyFms : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d("onNewToken Created : $p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Timber.d("onMessageReceived(), message : ${p0.notification?.body}")
    }
}
