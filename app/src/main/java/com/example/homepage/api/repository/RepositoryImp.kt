package com.example.homepage.api.repository

import com.example.homepage.api.ApiService
import com.example.homepage.data.DetailModel
import com.example.homepage.data.HomeModel
import com.example.homepage.data.InitRequest
import com.example.homepage.data.InitResponse

class RepositoryImp(private val apiService: ApiService) : Repository {

    override suspend fun getHome(): HomeModel {
        return apiService.getHomeList()
    }

    override suspend fun getDetail(id: Int): DetailModel {
        return apiService.getDetail(id)
    }

    override suspend fun postInit(model: InitRequest): InitResponse {
        return apiService.postInitialize(model)
    }

}