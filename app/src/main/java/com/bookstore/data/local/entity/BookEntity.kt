package com.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String,
    val description: String,
    val price: Double,
    val originalPrice: Double,
    val coverImageUrl: String = "",
    val categoryId: Long,
    val brand: String,
    val rating: Float = 4.0f,
    val ratingCount: Int = 0,
    val deliveryDays: String = "3-5 business days",
    val stockCount: Int = 10,
    val isFeatured: Boolean = false,
    val isbn: String = ""
)
