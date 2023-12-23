package com.example.userdetail.repository

import com.example.userdetail.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getUsersList(limit: Int,skip: Int) = apiServices.getUsers(limit,skip)
    suspend fun getUserDetails(id: Int) = apiServices.getUserDetails(id)
}