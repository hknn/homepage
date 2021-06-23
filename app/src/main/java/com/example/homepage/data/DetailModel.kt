package com.example.homepage.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailModel(
    var images: List<ImageModel>,
    var title: String,
    var subTitle: String,
    var description: String,
    var price: Double,
    var discountedPrice: Double,
    var discountRate: String,
    var active: Boolean,
    var soldCount: Int,
    var remainingCount: Int,
    var seller: SellerModel
)

@JsonClass(generateAdapter = true)
data class SellerModel(var nick: String)