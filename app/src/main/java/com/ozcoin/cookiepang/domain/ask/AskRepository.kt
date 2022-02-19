package com.ozcoin.cookiepang.domain.ask

interface AskRepository {

    suspend fun askToUser(ask: Ask): Boolean
}
