package com.example.userdetail.model

data class Company(
    val address: Address = Address(),
    val department: String = "",
    val name: String = "",
    val title: String = ""
)