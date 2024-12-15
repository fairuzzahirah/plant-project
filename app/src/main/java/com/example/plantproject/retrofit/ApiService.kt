package com.example.plantproject.retrofit

import com.example.plantproject.model.AuthResponse
import com.example.plantproject.model.LoginRequest
import com.example.plantproject.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("user")
    fun register(@Body user: User): Call<AuthResponse>

}
