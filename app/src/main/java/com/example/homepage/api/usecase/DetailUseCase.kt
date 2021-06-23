package com.example.homepage.api.usecase

import com.example.homepage.api.repository.Repository
import com.example.homepage.api.usecase.base.UseCase
import com.example.homepage.data.DetailModel

class DetailUseCase constructor(private val repository: Repository) :
    UseCase<DetailModel, Any?>() {

    override suspend fun run(params: Any?): DetailModel {
        return repository.getDetail(params as Int)
    }
}