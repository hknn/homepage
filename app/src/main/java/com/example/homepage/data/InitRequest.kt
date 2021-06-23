package com.example.homepage.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InitRequest(var appVersion: String)

@JsonClass(generateAdapter = true)
data class InitResponse(var token: String)