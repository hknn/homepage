package com.example.homepage.api

import com.example.homepage.data.DetailModel
import com.example.homepage.data.HomeModel
import com.example.homepage.data.InitRequest
import com.example.homepage.data.InitResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/home-page")
    suspend fun getHomeList(): HomeModel

    @GET("/product/detail/{id}")
    suspend fun getDetail(@Path("id") id: Int): DetailModel

    @POST("/initialize")
    suspend fun postInitialize(@Body model: InitRequest): InitResponse
}