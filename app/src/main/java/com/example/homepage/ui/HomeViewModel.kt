package com.example.homepage.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homepage.api.usecase.HomeUseCase
import com.example.homepage.data.HomeModel
import com.example.homepage.api.exception.ApiError
import com.example.homepage.api.sp.PreferenceRepository
import com.example.homepage.api.usecase.InitUseCase
import com.example.homepage.api.usecase.base.UseCaseResponse
import com.example.homepage.data.InitRequest
import com.example.homepage.data.InitResponse
import kotlinx.coroutines.cancel

class HomeViewModel constructor(
    private val homeUseCase: HomeUseCase,
    private val initUseCase: InitUseCase,
    private var sp: PreferenceRepository
) : ViewModel() {

    val homeData = MutableLiveData<HomeModel>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    fun postInit(version: String) {
        initUseCase.invoke(viewModelScope,
            InitRequest(version),
            object : UseCaseResponse<InitResponse> {
                override fun onSuccess(result: InitResponse) {
                    sp.token = result.token
                }

                override fun onError(apiError: ApiError?) {}

            })
    }

    fun getHome() {
        showProgressbar.value = true
        homeUseCase.invoke(viewModelScope, null, object : UseCaseResponse<HomeModel> {
            override fun onSuccess(result: HomeModel) {
                homeData.value = result
                showProgressbar.value = false
            }

            override fun onError(apiError: ApiError?) {
                messageData.value = apiError?.getErrorMessage()
                showProgressbar.value = false
            }
        })
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}