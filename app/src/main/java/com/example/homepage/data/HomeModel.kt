package com.example.homepage.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeModel(
    var sliders: List<Slider>,
    var banners: List<Banner>
)

@JsonClass(generateAdapter = true)
data class Banner(
    var id: Int,
    var image: ImageModel
)

@JsonClass(generateAdapter = true)
data class ImageModel(
    var width: Int,
    var height: Int,
    var url: String
)

@JsonClass(generateAdapter = true)
data class Slider(
    var id: Int,
    var image: ImageModel
)