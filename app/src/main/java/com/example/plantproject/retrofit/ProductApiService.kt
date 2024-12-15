package com.example.plantproject.retrofit

import com.example.plantproject.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("product")
    suspend fun getProducts(): List<Product>

    @POST("product")
    suspend fun createProduct(@Body product: Product): Product

    @GET("product/{id}")
    suspend fun getProductDetail(@Path("id") productId: String): Product

    @PUT("product/{id}")
    suspend fun updateProduct(@Path("id") productId: String, @Body product: Product): Product

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") productId: String)
}
