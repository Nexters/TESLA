package com.ozcoin.cookiepang.domain.user

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
    var dateOfBirth: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfBirth)
        }

    @get:Bindable
    var mbti: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mbti)
        }

    @get:Bindable
    var hobby: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hobby)
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
    var numOfKlaytn = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.numOfKlaytn)
        }

    @get:Bindable
    var numOfHammer = 0
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
}
