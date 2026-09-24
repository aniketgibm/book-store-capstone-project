package com.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val bookId: Long,
    val quantity: Int,
    val unitPrice: Double
)
