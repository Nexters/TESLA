package com.ozcoin.cookiepang.data.request

import com.ozcoin.cookiepang.data.ask.AskEntity
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.cookie.CookieEntity
import com.ozcoin.cookiepang.data.cookie.MakeACookieRequestParam
import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailEntity
import com.ozcoin.cookiepang.data.timeline.TimeLineEntity
import com.ozcoin.cookiepang.data.user.UserEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/auth")
    suspend fun auth(): Response<Unit>

    @POST("/users")
    suspend fun registrationUser(@Body userEntity: UserEntity): Response<UserEntity>

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserEntity>

    @PUT("/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Int,
        @Body introduction: String
    ): Response<UserEntity>

    @POST("/users/{userId}/categories")
    suspend fun setInterestInCategoryList(
        @Path("userId") userId: Int,
        @Body categoryIdList: List<Int>
    ): Response<Unit>

    @GET("/categories")
    suspend fun getAllCategoryList(): Response<List<CategoryEntity>>

    @POST("/asks")
    suspend fun sendAsk(@Body askEntity: AskEntity): Response<AskEntity>

    @POST("/cookies")
    suspend fun makeACookie(
        @Body makeACookieRequestParam: MakeACookieRequestParam
    ): Response<CookieEntity>

    @PUT("/cookies/{cookieId}")
    suspend fun updateCookieInfo(
        @Path("cookieId") cookieId: String,
        @Body price: Int,
        @Body status: String,
        @Body purchaserUserId: Int
    ): Response<CookieEntity>

    @DELETE("/cookies/{cookieId}")
    suspend fun deleteCookie(@Path("cookieId") cookieId: String): Response<Unit>

    @POST("/cookies")
    suspend fun editCookie(
        @Body question: String,
        @Body answer: String,
        @Body price: Int,
        @Body authorUserId: Int,
        @Body ownedUserId: Int,
        @Body txHash: String,
        @Body categoryId: Int
    ): Response<CookieEntity>

    @GET("/users/{userId}/categories/all/cookies")
    suspend fun getAllCookieList(
        @Path("userId") userId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): Response<List<TimeLineEntity>>

    @GET("/users/{userId}/categories/{categoryId}/cookies")
    suspend fun getCookieList(
        @Path("userId") userId: String,
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): Response<List<TimeLineEntity>>

    @GET("/users/{userId}/cookies/{cookieId}/detail")
    suspend fun getCookieDetail(
        @Path("userId") userId: String,
        @Path("cookieId") cookieId: String
    ): Response<CookieDetailEntity>
}
