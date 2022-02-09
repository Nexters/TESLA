package com.ozcoin.cookiepang.domain.klip

import androidx.annotation.StringDef

@StringDef(
    value = [
        KlipTokenType.Cookie,
        KlipTokenType.Hammer
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class KlipTokenType {
    companion object {
        const val Cookie = "Cookie_token"
        const val Hammer = "Hammer_token"
    }
}
