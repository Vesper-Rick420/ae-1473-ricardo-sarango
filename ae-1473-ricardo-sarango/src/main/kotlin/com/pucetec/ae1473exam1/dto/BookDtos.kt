package com.pucetec.ae1473exam1.dto

data class BookRequest(
    val title: String,
    val author: String,
    val priceUsd: Double
)

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val slug: String,
    val priceUsd: Double,
    val finalPrice: Double
)
