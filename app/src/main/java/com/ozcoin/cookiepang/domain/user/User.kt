package com.ozcoin.cookiepang.domain.user

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ozcoin.cookiepang.BR
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class User : BaseObservable() {

    @get:Bindable
    var userId: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    @get:Bindable
    var profileID: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.profileID)
        }

    @get:Bindable
    var location: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.location)
        }

    @get:Bindable
    var height: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.height)
        }

    @get:Bindable
    var job: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.job)
        }

    @get:Bindable
    var walletAddress: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.walletAddress)
        }

    @get: Bindable
    var interestCategoryList: List<UserCategory>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.interestCategoryList)
        }

    @get:Bindable
    var numOfKlaytn = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.numOfKlaytn)
        }

    @get:Bindable
    var numOfHammer = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.numOfHammer)
        }

    @get:Bindable
    var walletApproved = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.walletApproved)
        }

    @get:Bindable
    var introduction = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.introduction)
        }

    @get:Bindable
    var profileUrl: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.profileUrl)
        }

    @get:Bindable
    var backgroundUrl: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.backgroundUrl)
        }

    var finishOnboard: Boolean = false
    var updateThumbnailImg: Bitmap? = null
    var updateProfileBackgroundImg: Bitmap? = null
}

fun String.toDataUserId(): Int {
    return kotlin.runCatching { this.toInt() }.getOrDefault(-1)
}
