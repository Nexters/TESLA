package com.ozcoin.cookiepang.data.provider

import android.content.Context

class ResourceProvider(
    private val context: Context
) {
    private val resources = context.resources

    fun getString(id: Int): String = resources.getString(id)
}
