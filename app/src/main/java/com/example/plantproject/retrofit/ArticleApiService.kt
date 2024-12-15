package com.example.plantproject.retrofit


import com.example.plantproject.model.Article
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ArticleApiService {
    @GET("article")
    suspend fun getArticle(): List<Article>

    @POST("article")
    suspend fun createArticle(@Body article: Article): Article

    @GET("article/{id}")
    suspend fun getArticleDetail(@Path("id") articleId: String): Article

    @PUT("article/{id}")
    suspend fun updateArticle(@Path("id") articleId: String, @Body product: Article): Article

    @DELETE("article/{id}")
    suspend fun deleteArticle(@Path("id") articleId: String)
}
