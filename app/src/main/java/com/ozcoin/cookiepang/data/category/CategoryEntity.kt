package com.ozcoin.cookiepang.data.category

import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryColorStyle
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    val id: Int,
    val name: String,
    val color: String
)

fun CategoryEntity.toDomain(): UserCategory {
    return UserCategory(
        categoryName = name,
        categoryColorStyle = kotlin.runCatching {
            UserCategoryColorStyle.valueOf(color)
        }.getOrDefault(UserCategoryColorStyle.NONE),
        categoryId = this.id,
        isSelected = false
    )
}

fun UserCategory.toData(): CategoryEntity {
    return CategoryEntity(
        id = categoryId,
        color = categoryColorStyle.name,
        name = categoryName
    )
}
