package com.example.homepage.api.repository

import com.example.homepage.data.DetailModel
import com.example.homepage.data.HomeModel
import com.example.homepage.data.InitRequest
import com.example.homepage.data.InitResponse

interface Repository {

    suspend fun getHome(): HomeModel

    suspend fun getDetail(id: Int): DetailModel

    suspend fun postInit(model: InitRequest): InitResponse

}