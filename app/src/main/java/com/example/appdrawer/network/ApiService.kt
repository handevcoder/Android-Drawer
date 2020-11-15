package com.example.appdrawer.network

import com.example.appdrawer.model.Products
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun GetAllProducts(): Call<List<Products>>
}