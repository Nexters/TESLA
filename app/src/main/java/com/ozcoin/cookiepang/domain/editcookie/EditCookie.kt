package com.ozcoin.cookiepang.domain.editcookie

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.ozcoin.cookiepang.BR
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

@Keep
class EditCookie constructor() : BaseObservable(), Parcelable {

    constructor(parcel: Parcel) : this() {
        parcel.run {
            isEditPricingInfo = readInt() == 1
            cookieId = readInt()
            question = readString() ?: ""
            answer = readString() ?: ""
            hammerCost = readString() ?: ""
            receivedQuestion = readParcelable(EditCookie::class.java.classLoader)
            selectedCategory = readParcelable(EditCookie::class.java.classLoader)
        }
    }
    var receivedQuestion: Question? = null
    var isEditPricingInfo = false
    var cookieId: Int = -1

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
            writeInt(cookieId)
            writeString(question)
            writeString(answer)
            writeString(hammerCost)
            writeParcelable(selectedCategory, flags)
            writeParcelable(receivedQuestion, flags)
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
