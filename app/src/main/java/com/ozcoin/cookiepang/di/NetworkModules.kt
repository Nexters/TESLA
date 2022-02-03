package com.ozcoin.cookiepang.di

import com.ozcoin.cookiepang.request.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { okHttpClient }
    single { createApiService<ApiService>(get(), baseUrl) }
}

private const val baseUrl = ""

private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(15L, TimeUnit.SECONDS)
        .writeTimeout(15L, TimeUnit.SECONDS)
        .build()

inline fun <reified T> createApiService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
