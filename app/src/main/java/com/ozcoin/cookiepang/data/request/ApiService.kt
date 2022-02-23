package com.ozcoin.cookiepang.data.request

import com.ozcoin.cookiepang.data.ask.AskEntity
import com.ozcoin.cookiepang.data.ask.AskUpdateRequestBody
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.cookie.CookieEntity
import com.ozcoin.cookiepang.data.cookie.CookieResponse
import com.ozcoin.cookiepang.data.cookie.MakeACookieRequestParam
import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailEntity
import com.ozcoin.cookiepang.data.notification.NotificationEntity
import com.ozcoin.cookiepang.data.response.AddressResponse
import com.ozcoin.cookiepang.data.response.AnswerResponse
import com.ozcoin.cookiepang.data.response.BalanceResponse
import com.ozcoin.cookiepang.data.response.PageListResponse
import com.ozcoin.cookiepang.data.response.PriceResponse
import com.ozcoin.cookiepang.data.response.TokenAddressResponse
import com.ozcoin.cookiepang.data.response.UserIdResponse
import com.ozcoin.cookiepang.data.timeline.TimeLineEntity
import com.ozcoin.cookiepang.data.user.LoginRequestParam
import com.ozcoin.cookiepang.data.user.UserEntity
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/auth")
    suspend fun auth(): Response<Unit>

    /*
        user-controller
     */

    @POST("/users")
    suspend fun registrationUser(@Body userEntity: UserEntity): Response<UserEntity>

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserEntity>

    @Multipart
    @PUT("/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Int,
        @Query("introduction") introduction: String,
        @Part profilePicture: MultipartBody.Part,
        @Part backgroundPicture: MultipartBody.Part
    ): Response<UserEntity>

    /*
        cookie-controller
     */

    @POST("/cookies")
    suspend fun makeACookie(
        @Body makeACookieRequestParam: MakeACookieRequestParam
    ): Response<CookieResponse>

    @JvmSuppressWildcards
    @PUT("/cookies/{cookieId}")
    suspend fun updateCookieInfo(
        @Path("cookieId") cookieId: String,
        @QueryMap updateCookie: Map<String, Any>
    ): Response<CookieResponse>

    @DELETE("/cookies/{cookieId}")
    suspend fun deleteCookie(@Path("cookieId") cookieId: String): Response<Unit>

    @GET("/users/{userId}/cookies")
    suspend fun getUserCookieList(
        @Path("userId") userId: Int,
        @Query("target") target: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3
    ): Response<PageListResponse<CookieEntity>>

    /*
        ask-controller
     */

    @POST("/asks")
    suspend fun sendAsk(@Body askEntity: AskEntity): Response<AskEntity>

    @PUT("/asks/{askId}")
    suspend fun updateAsk(
        @Path("askId") askId: Int,
        @Body askUpdateRequestBody: AskUpdateRequestBody
    ): Response<AskEntity>

    /*
        category-controller
     */

    @POST("/users/{userId}/categories")
    suspend fun setInterestInCategoryList(
        @Path("userId") userId: Int,
        @Body categoryIdList: List<Int>
    ): Response<Unit>

    @GET("/categories")
    suspend fun getAllCategoryList(): Response<List<CategoryEntity>>

    /*
        login-controller
     */

    @POST("/login")
    suspend fun login(@Body loginRequestParam: LoginRequestParam): Response<UserIdResponse>

    /*
        notification-controller
     */

    @GET("/users/{userId}/notifications")
    suspend fun getAlarmList(
        @Path("userId") userId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3
    ): Response<List<NotificationEntity>>

    /*
        view-controller
     */

    @GET("/users/{userId}/cookies/{cookieId}/detail")
    suspend fun getCookieDetail(
        @Path("userId") userId: Int,
        @Path("cookieId") cookieId: String
    ): Response<CookieDetailEntity>

    @GET("/users/{userId}/categories/{categoryId}/cookies")
    suspend fun getCookieList(
        @Path("userId") userId: Int,
        @Path("categoryId") categoryId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3
    ): Response<PageListResponse<TimeLineEntity>>

    @GET("/users/{userId}/categories/all/cookies")
    suspend fun getAllCookieList(
        @Path("userId") userId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3
    ): Response<PageListResponse<TimeLineEntity>>

    @GET("/users/{userId}/asks")
    suspend fun getAskList(
        @Path("userId") userId: Int,
        @Query("target") target: String
    ): Response<PageListResponse<AskEntity>>

    /*
        klay-controller
     */

    @GET("/contract/klay/users/{userId}/balance")
    suspend fun getNumOfKlay(
        @Path("userId") userId: Int
    ): Response<BalanceResponse>

    /*
        contract-hammer-controller
     */

    @GET("/contract/hammers/users/{userId}/balance")
    suspend fun getNumOfHammer(
        @Path("userId") userId: Int
    ): Response<BalanceResponse>

    @GET("/contract/hammers/users/{userId}/approve")
    suspend fun getIsWalletApproved(
        @Path("userId") userId: Int
    ): Response<AnswerResponse>

    @GET("/contract/hammers/address")
    suspend fun getHammerContractAddress(): Response<AddressResponse>

    /*
        contract-cookie-controller
     */

    @GET("/contract/cookies/{nftTokenId}/sale")
    suspend fun isOnSaleCookie(
        @Path("nftTokenId") nftTokenId: Int
    ): Response<AnswerResponse>

    @GET("/contract/cookies/{nftTokenId}/price")
    suspend fun getCookiePrice(
        @Path("nftTokenId") nftTokenId: Int
    ): Response<PriceResponse>

    @GET("/contract/cookies/{nftTokenId}/hide")
    suspend fun isCookieHidden(
        @Path("nftTokenId") nftTokenId: Int
    ): Response<AnswerResponse>

    @GET("/contract/cookies/users/{userId}/nftTokenId")
    suspend fun getTokenAddress(
        @Path("userId") userId: String,
        @Query("index") index: String
    ): Response<TokenAddressResponse>

    @GET("/contract/cookies/prices/hammer")
    suspend fun getMakeCookieTaxPrice(): Response<PriceResponse>

    @GET("/contract/cookies/address")
    suspend fun getCookieContractAddress(): Response<AddressResponse>
}
