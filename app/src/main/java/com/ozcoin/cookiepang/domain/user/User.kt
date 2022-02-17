package com.ozcoin.cookiepang.domain.user

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ozcoin.cookiepang.BR
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class User : BaseObservable() {

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

    var interestCategoryList: List<UserCategory>? = null
}
