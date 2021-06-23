package com.example.homepage.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homepage.api.exception.ApiError
import com.example.homepage.api.usecase.DetailUseCase
import com.example.homepage.api.usecase.base.UseCaseResponse
import com.example.homepage.data.DetailModel
import kotlinx.coroutines.cancel

class DetailViewModel constructor(private val detailUseCase: DetailUseCase) : ViewModel() {

    val detailData = MutableLiveData<DetailModel>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    fun getDetail(pageId: Int) {
        showProgressbar.value = true
        detailUseCase.invoke(viewModelScope, pageId, object : UseCaseResponse<DetailModel> {
            override fun onSuccess(result: DetailModel) {
                detailData.value = result
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