package com.bookstore.domain.model

data class Book(
    val id: Long = 0,
    val title: String,
    val author: String,
    val description: String,
    val price: Double,
    val originalPrice: Double = price,
    val coverImageUrl: String = "",
    val categoryId: Long,
    val categoryName: String = "",
    val brand: String,
    val rating: Float = 4.0f,
    val ratingCount: Int = 0,
    val deliveryDays: String = "3-5 business days",
    val stockCount: Int = 10,
    val isFeatured: Boolean = false,
    val isbn: String = ""
)
