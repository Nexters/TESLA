package com.ozcoin.cookiepang.ui.editcookie

sealed class EditCookieEvent {
    object QuestionInfoMissing : EditCookieEvent()
    object AnswerInfoMissing : EditCookieEvent()
    object HammerCostInfoMissing : EditCookieEvent()
    object CategoryInfoMissing : EditCookieEvent()
}
