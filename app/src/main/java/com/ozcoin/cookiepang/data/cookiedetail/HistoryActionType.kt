package com.ozcoin.cookiepang.data.cookiedetail

import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistoryType

enum class HistoryActionType {
    MODIFY, BUY, CREATE
}

fun HistoryActionType.toDomain(): CookieHistoryType {
    return when (this) {
        HistoryActionType.BUY -> CookieHistoryType.PURCHASE
        HistoryActionType.MODIFY -> CookieHistoryType.MODIFY
        HistoryActionType.CREATE -> CookieHistoryType.CREATE
    }
}
