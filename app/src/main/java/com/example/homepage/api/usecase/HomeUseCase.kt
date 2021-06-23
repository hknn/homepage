package com.example.homepage.api.usecase

import com.example.homepage.api.repository.Repository
import com.example.homepage.api.usecase.base.UseCase
import com.example.homepage.data.HomeModel

class HomeUseCase constructor(private val repository: Repository) :
    UseCase<HomeModel, Any?>() {

    override suspend fun run(params: Any?): HomeModel {
        return repository.getHome()
    }
}