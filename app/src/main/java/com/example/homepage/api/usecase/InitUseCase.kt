package com.example.homepage.api.usecase

import com.example.homepage.api.repository.Repository
import com.example.homepage.api.usecase.base.UseCase
import com.example.homepage.data.InitRequest
import com.example.homepage.data.InitResponse

class InitUseCase constructor(private val repository: Repository) :
    UseCase<InitResponse, Any?>() {

    override suspend fun run(params: Any?): InitResponse {
        return repository.postInit(params as InitRequest)
    }
}