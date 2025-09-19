package com.example.hopesa.data.model

data class User(
    val _id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val role: String = "user"  // default role
)