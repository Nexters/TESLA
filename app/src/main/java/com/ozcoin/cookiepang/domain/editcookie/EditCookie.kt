package com.ozcoin.cookiepang.domain.editcookie

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ozcoin.cookiepang.BR
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

class EditCookie constructor() : BaseObservable(), Parcelable {

    constructor(parcel: Parcel) : this() {
        parcel.run {
            isEditPricingInfo = readInt() == 1
            userId = readString() ?: ""
            question = readString() ?: ""
            answer = readString() ?: ""
            hammerCost = readString() ?: ""
            selectedCategory = readParcelable(EditCookie::class.java.classLoader)
            tx_hash = readString() ?: ""
        }
    }

    var isEditPricingInfo = false
    var userId: String = ""
    var tx_hash: String = ""

    @get:Bindable
    var question: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.question)
        }

    @get:Bindable
    var answer: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.answer)
        }

    var hammerCost: String = ""
    var selectedCategory: UserCategory? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.run {
            writeInt(
                if (isEditPricingInfo) 1 else 0
            )
            writeString(userId)
            writeString(tx_hash)
            writeString(question)
            writeString(answer)
            writeString(hammerCost)
            writeParcelable(selectedCategory, flags)
        }
    }

    companion object CREATOR : Parcelable.Creator<EditCookie> {
        override fun createFromParcel(parcel: Parcel): EditCookie {
            return EditCookie(parcel)
        }

        override fun newArray(size: Int): Array<EditCookie?> {
            return arrayOfNulls(size)
        }
    }
}
