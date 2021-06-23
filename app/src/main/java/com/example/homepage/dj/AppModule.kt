package com.example.homepage.dj

import com.example.homepage.ui.DetailViewModel
import com.example.homepage.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { HomeViewModel(get(), get(), get()) }

    viewModel { DetailViewModel(get()) }

    single { createGetHomeUseCase(get()) }

    single { createGetDetailUseCase(get()) }

    single { createPostInitUseCase(get()) }

    single { createRepository(get()) }

    single { providesPreferenceRepository(androidApplication()) }
}