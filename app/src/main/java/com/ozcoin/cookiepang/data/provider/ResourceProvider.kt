package com.ozcoin.cookiepang.data.provider

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Context
) {
    private val resources = context.resources

    fun getString(id: Int): String = resources.getString(id)
}
