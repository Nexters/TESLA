package com.ozcoin.cookiepang.domain.klip

data class SendToken(
    @KlipTokenType val tokenType: String,
    val to: String,
    val from: String,
    val amount: String
)
