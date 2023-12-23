package com.example.userdetail.model

data class UserDetailsResponse(
    val limit: Int = 0,
    val skip: Int = 0,
    val total: Int = 0,
    val users: List<User> = listOf()
)