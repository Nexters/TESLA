package com.ozcoin.cookiepang.ui.editprofile

sealed class EditProfileEvent {
    data class OpenCamera(val isThumbnail: Boolean) : EditProfileEvent()
    data class OpenGallery(val isThumbnail: Boolean) : EditProfileEvent()
}
