package com.example.plantproject.model

data class AuthResponse(
    val status: String,
    val token: String? = null,
    val message: String? = null
)
