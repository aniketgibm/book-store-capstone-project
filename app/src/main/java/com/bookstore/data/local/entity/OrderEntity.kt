package com.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val totalAmount: Double,
    val discountAmount: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val status: String = "CONFIRMED",
    val placedAt: Long = System.currentTimeMillis(),
    val deliveryAddress: String = ""
)
