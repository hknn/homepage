package com.example.homepage.dj

import android.app.Application
import android.content.Context
import com.example.homepage.api.ApiService
import com.example.homepage.BuildConfig
import com.example.homepage.api.repository.Repository
import com.example.homepage.api.repository.RepositoryImp
import com.example.homepage.api.sp.PreferenceRepository
import com.example.homepage.api.usecase.DetailUseCase
import com.example.homepage.api.usecase.HomeUseCase
import com.example.homepage.api.usecase.InitUseCase
import com.example.homepage.api.interceptor.AuthInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient(get()) }

    single { MoshiConverterFactory.create() }

    single { Moshi.Builder().build() }

}

fun createOkHttpClient(sp: PreferenceRepository): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor {
            it.addHeader("token", sp.token.orEmpty())
        })
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun providesPreferenceRepository(application: Application): PreferenceRepository {
    return PreferenceRepository(
        application.getSharedPreferences(
            "default_preferences",
            Context.MODE_PRIVATE
        )
    )
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createRepository(apiService: ApiService): Repository {
    return RepositoryImp(apiService)
}

fun createGetHomeUseCase(repository: Repository): HomeUseCase {
    return HomeUseCase(repository)
}

fun createGetDetailUseCase(repository: Repository): DetailUseCase {
    return DetailUseCase(repository)
}

fun createPostInitUseCase(repository: Repository): InitUseCase {
    return InitUseCase(repository)
}
