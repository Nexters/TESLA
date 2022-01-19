package com.ozcoin.cookiepang.repo.user

interface UserRegRepository {

    fun regUser()

    suspend fun getRegUser()

    fun isUserReg() : Boolean

}