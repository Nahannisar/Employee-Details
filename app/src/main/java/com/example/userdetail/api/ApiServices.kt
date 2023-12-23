package com.example.userdetail.api

import com.example.userdetail.model.User
import com.example.userdetail.model.UserDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserDetailsResponse

    @GET("users/{id}")
    suspend fun getUserDetails(@Path("id") id: Int):User
}