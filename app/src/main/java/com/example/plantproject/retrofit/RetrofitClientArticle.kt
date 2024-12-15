package com.example.plantproject.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientArticle {
    private const val BASE_URL = "https://ppbo-api.vercel.app/QXtDJ/"

    val instance: ArticleApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }
}
