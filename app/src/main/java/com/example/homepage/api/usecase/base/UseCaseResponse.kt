package com.example.homepage.api.usecase.base

import com.example.homepage.api.exception.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

