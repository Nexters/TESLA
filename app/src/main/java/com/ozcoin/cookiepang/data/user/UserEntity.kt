package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.domain.user.User

data class UserEntity(val name: String)

fun UserEntity.toDomain(): User = User(this.name)

fun User.toData(): UserEntity = UserEntity(this.name)
